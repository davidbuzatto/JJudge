package br.com.davidbuzatto.jjudge.processor;

import br.com.davidbuzatto.jjudge.testsets.TestCase;
import br.com.davidbuzatto.jjudge.testsets.TestCaseResult;
import br.com.davidbuzatto.jjudge.testsets.TestProgrammingLanguage;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.utils.Colors;
import br.com.davidbuzatto.jjudge.utils.StreamGobbler;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextPane;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Processor {

    private static volatile Process currentProcess;

    public static void killCurrentProcess() {
        Process p = currentProcess;
        if ( p != null ) {
            p.destroyForcibly();
        }
    }

    public static TestResult compileAndRun(
            String presentationName,
            String fileName,
            String baseDir,
            int secondsToTimeout,
            boolean outputStreams,
            List<TestCase> testCases,
            TestProgrammingLanguage pLang,
            JTextPane textPane,
            List<File> javaClasspathFiles,
            List<String> extraCompilerParams,
            boolean useLightTheme ) throws IOException, InterruptedException {
        
        ResourceBundle bundle = Utils.bundle;
        
        TestResult testResult = new TestResult();
        testResult.setPresentationName( presentationName );
        testResult.setName( fileName );
        testResult.setTestCasesResult( new ArrayList<>() );
            
        ExecutionState state = null;
        File dir = new File( baseDir );
        int passedTestCases = 0;

        String sourceExt = pLang.name().toLowerCase();
        sourceExt = sourceExt.equals( "python" ) ? "py" : sourceExt;

        List<String> cmdExec = new ArrayList<>();
        String[] filesToRemoveByExtension = { "o", "exe", "class", "out" };
        List<List<String>> compilationCommands = new ArrayList<>();
        List<String> threadId = new ArrayList<>();


        switch ( pLang ) {

            case C: {

                cmdExec = new ArrayList<>( Arrays.asList(
                    baseDir + File.separator + fileName + ".exe"
                ) );

                List<String> gccCmd = new ArrayList<>( Arrays.asList(
                    "gcc", fileName + ".c", "-o", fileName + ".exe",
                    "-Werror", "-Wfatal-errors", "-std=c99", "-lm", "-O0"
                ) );
                gccCmd.addAll( extraCompilerParams );
                compilationCommands.add( gccCmd );
                threadId.add( "    gcc" );
                break;

            }

            case CPP: {

                cmdExec = new ArrayList<>( Arrays.asList(
                    baseDir + File.separator + fileName + ".exe"
                ) );

                List<String> gppCmd = new ArrayList<>( Arrays.asList(
                    "g++", fileName + ".cpp", "-o", fileName + ".exe",
                    "-Werror", "-Wfatal-errors", "-std=c++20", "-lm", "-O0"
                ) );
                gppCmd.addAll( extraCompilerParams );
                compilationCommands.add( gppCmd );
                threadId.add( "    g++" );
                break;

            }

            case JAVA: {

                String classpathFiles = "";
                if ( javaClasspathFiles != null ) {
                    classpathFiles = Utils.buildDependenciesPath( javaClasspathFiles );
                }

                // multiple file compilation and execution
                if ( fileName.contains( "/" ) ) {

                    int ind = fileName.lastIndexOf( '/' );
                    String fileDir = fileName.substring( 0, ind );
                    String justName = fileName.substring( ind + 1 );
                    String cp = baseDir + File.separator + fileDir;
                    if ( !classpathFiles.isEmpty() ) {
                        cp += File.pathSeparator + classpathFiles;
                    }

                    cmdExec = new ArrayList<>( Arrays.asList(
                        "java", "-Duser.language=en", "-Duser.country=US",
                        "-cp", cp, justName
                    ) );

                    List<String> javacCmd = new ArrayList<>( Arrays.asList(
                        "javac", "-Xlint:unchecked", fileName + ".java", "-cp", cp
                    ) );
                    javacCmd.addAll( extraCompilerParams );
                    compilationCommands.add( javacCmd );

                } else {

                    cmdExec = new ArrayList<>( Arrays.asList(
                        "java", "-Duser.language=en", "-Duser.country=US"
                    ) );
                    if ( !classpathFiles.isEmpty() ) {
                        cmdExec.add( "-cp" );
                        cmdExec.add( classpathFiles );
                    }
                    cmdExec.add( fileName );

                    List<String> javacCmd = new ArrayList<>( Arrays.asList(
                        "javac", "-Xlint:unchecked", fileName + ".java"
                    ) );
                    if ( !classpathFiles.isEmpty() ) {
                        javacCmd.add( "-cp" );
                        javacCmd.add( classpathFiles );
                    }
                    javacCmd.addAll( extraCompilerParams );
                    compilationCommands.add( javacCmd );

                }

                threadId.add( "    javac" );
                break;

            }

            case PYTHON: {

                cmdExec = new ArrayList<>( Arrays.asList( "python", fileName + ".py" ) );
                break;

            }

        }
        
        if ( textPane == null ) {
            System.out.printf( "Processing file %s/%s.%s\n", baseDir, fileName, sourceExt );
        } else {
            Utils.addFormattedText( 
                    textPane, 
                    String.format( bundle.getString( "Processor.compileAndRun.processing" ), baseDir, fileName, sourceExt ), 
                    getResultTextColor( useLightTheme ), false );
        }

        
        if ( new File( String.format( "%s/%s.%s", baseDir, fileName, sourceExt ) ).exists() ) {
            
            // compilation
            for ( int i = 0; i < compilationCommands.size(); i++ ) {

                ProcessBuilder pbComp = new ProcessBuilder( compilationCommands.get( i ) );
                pbComp.directory( dir );
                Process p = pbComp.start();
                currentProcess = p;
                StreamGobbler sg;

                try ( FileOutputStream fosOutput = new FileOutputStream(
                        new File( String.format( "%s/error.out", baseDir ) ) ) ) {

                    sg = new StreamGobbler(
                            p.getInputStream(),
                            p.getErrorStream(),
                            fosOutput,
                            threadId.get( i ),
                            outputStreams );
                    Thread t = new Thread( sg );
                    t.start();
                    t.join();

                }

                currentProcess = null;

                // if error...
                if ( sg.isProcessErrorStreamDataAvailable() ) {
                    
                    // reading process output from file
                    StringBuilder sbOutput = new StringBuilder();
                    
                    try ( Scanner sOutput = new Scanner( 
                            new File( String.format( "%s/%s", baseDir, "error.out" ) ) ) ) {
                        
                        boolean first = true;
                        while ( sOutput.hasNextLine() ) {
                            if ( first ) {
                                first = false;
                            } else {
                                sbOutput.append( "\n" );
                            }
                            sbOutput.append( sOutput.nextLine() );
                        }
                        
                    }
                    
                    testResult.setErrorMessage( sbOutput.toString() );
                    
                    if ( textPane == null ) {
                        System.out.println( "|-- compilation error!" );
                    } else {

                        Utils.addFormattedText( 
                                textPane, 
                                "|   |-- ", 
                                getResultTextColor( useLightTheme ), false );
                        
                        Utils.addFormattedText( 
                                textPane, 
                                bundle.getString( "Processor.compileAndRun.compilationError" ), 
                                Colors.COMPILATION_ERROR.darker(), false );

                        Utils.addFormattedText( 
                                textPane, 
                                Utils.identText( sbOutput.toString(), 3 ) + "\n", 
                                getResultTextColor( useLightTheme ), false );

                    }
                        
                    state = ExecutionState.COMPILATION_ERROR;
                    break;
                    
                }
                
            }

            if ( state != ExecutionState.COMPILATION_ERROR ) {

                if ( textPane == null ) {
                    
                    if ( !compilationCommands.isEmpty() ) {
                        System.out.println( "|-- compiled!" );
                    }
                    
                    System.out.println( "|-- executing..." );
                    
                } else {
                    
                    if ( !compilationCommands.isEmpty() ) {
                        Utils.addFormattedText( 
                            textPane, 
                            bundle.getString( "Processor.compileAndRun.compiled" ), 
                            getResultTextColor( useLightTheme ), false );
                    }
                    
                    Utils.addFormattedText( 
                            textPane, 
                            bundle.getString( "Processor.compileAndRun.executing" ), 
                            getResultTextColor( useLightTheme ), false );
                    
                }

                int i = 1;
                for ( TestCase tc : testCases ) {

                    state = null;

                    String input = tc.getInput();
                    String test = tc.getOutput();

                    ProcessBuilder pbExec = new ProcessBuilder( cmdExec );
                    pbExec.directory( dir );
                    Process pExec = pbExec.start();
                    currentProcess = pExec;
                    PrintWriter pwExec = new PrintWriter( pExec.getOutputStream() );
                    
                    try ( FileOutputStream fosOutput = new FileOutputStream(
                            new File( String.format( "%s/output.out", baseDir ) ) ) ) {
                        
                        StreamGobbler sgExec = new StreamGobbler( 
                                pExec.getInputStream(), 
                                pExec.getErrorStream(), 
                                fosOutput, 
                                bundle.getString( "Processor.compileAndRun.executionStream" ), 
                                outputStreams );
                        Thread tExec = new Thread( sgExec );
                        tExec.start();

                        if ( textPane == null ) {
                            System.out.printf( "|-- test case %02d:\n", i++ );
                            System.out.println( "|   |-- process test input: " );
                            if ( input.isEmpty() ) {
                                System.out.println( Utils.identText( "<empty>", 3 ) );
                            } else {
                                System.out.println( Utils.identText( input, 3 ) );
                            }
                            System.out.println( "|   |" );
                        } else {
                            Utils.addFormattedText( 
                                    textPane, 
                                    String.format( bundle.getString( "Processor.compileAndRun.testCase" ), i++ ), 
                                    getResultTextColor( useLightTheme ), false );
                            Utils.addFormattedText( 
                                    textPane, 
                                    bundle.getString( "Processor.compileAndRun.processTestInput" ), 
                                    getResultTextColor( useLightTheme ), false );
                            if ( input.isEmpty() ) {
                                Utils.addFormattedText( 
                                        textPane, 
                                        Utils.identText( bundle.getString( "Processor.compileAndRun.empty" ), 4 ) + "\n", 
                                        getResultTextColor( useLightTheme ), false );
                            } else {
                                Utils.addFormattedText( 
                                        textPane, 
                                        Utils.identText( input, 4 ) + "\n", 
                                        getResultTextColor( useLightTheme ), false );
                            }
                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    getResultTextColor( useLightTheme ), false );

                        }

                        for ( String s : input.split( "\n" ) ) {
                            if ( !s.isEmpty() ) {
                                pwExec.println( s );
                            }
                        }
                        pwExec.close();

                        // wait for long process time
                        try {
                            if ( !pExec.waitFor( secondsToTimeout, TimeUnit.SECONDS ) ) {
                                state = ExecutionState.TIMEOUT_ERROR;
                                pExec.destroyForcibly();
                            }
                        } catch ( InterruptedException e ) {
                            state = ExecutionState.TIMEOUT_ERROR;
                            pExec.destroyForcibly();
                            Thread.currentThread().interrupt();
                        }

                        try {
                            tExec.join();
                        } catch ( InterruptedException e ) {
                            Thread.currentThread().interrupt();
                        }

                        // abnormal termination
                        if ( state == null ) {
                            if ( pExec.exitValue() != 0 ) {
                                state = ExecutionState.RUNTIME_ERROR;
                            }
                        }

                    } // fosOutput closed automatically by try-with-resources

                    currentProcess = null;

                    // reading process output from file
                    StringBuilder sbOutput = new StringBuilder();
                    File processOutputFile = new File( String.format( "%s/%s", baseDir, "output.out" ) );
                    
                    // file must be lesser tham 1MB
                    if ( processOutputFile.length() > 1024 * 1024 ) {
                        state = ExecutionState.RUNTIME_ERROR;
                    } else {
                        
                        Scanner sOutput = new Scanner( processOutputFile );

                        boolean first = true;
                        while ( sOutput.hasNextLine() ) {
                            if ( first ) {
                                first = false;
                            } else {
                                sbOutput.append( "\n" );
                            }
                            sbOutput.append( sOutput.nextLine() );
                        }

                        sOutput.close();

                        // change spaces to \u2334
                        /*test = test.replace( " ", "\u2334" );
                        cleanOutput = cleanOutput.replace( " ", "\u2334" );*/

                        // trim end
                        String cleanOutput = sbOutput.toString().replaceAll( "\\s+$", "" );

                        TestCaseResult tcr = new TestCaseResult();
                        tcr.setInput( tc.getInput() );
                        tcr.setOutput( tc.getOutput() );
                        tcr.setTestOutput( cleanOutput );
                        tcr.setExecutionState( state );
                        testResult.getTestCasesResult().add( tcr );

                        if ( textPane == null ) {
                            System.out.println( "|   |-- process test output: " );
                            System.out.println( Utils.identText( test, 3 ) );
                            System.out.println( "|   |" );
                            System.out.println( "|   |-- process output: " );
                            System.out.println( Utils.identText( cleanOutput, 3 ) );
                            System.out.println( "|   |" );
                        } else {

                            Utils.addFormattedText( 
                                    textPane, 
                                    bundle.getString( "Processor.compileAndRun.processTestOutput" ), 
                                    getResultTextColor( useLightTheme ), false );

                            if ( test.isEmpty() ) {
                                Utils.addFormattedText( 
                                        textPane, 
                                        Utils.identText( bundle.getString( "Processor.compileAndRun.empty" ), 4 ) + "\n", 
                                        getResultTextColor( useLightTheme ), false );
                            } else {
                                Utils.addFormattedText( 
                                        textPane, 
                                        Utils.identText( test, 4 ) + "\n", 
                                        getResultTextColor( useLightTheme ), true );
                            }

                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    getResultTextColor( useLightTheme ), false );

                            Utils.addFormattedText( 
                                    textPane, 
                                    bundle.getString( "Processor.compileAndRun.processOutput" ), 
                                    getResultTextColor( useLightTheme ), false );

                            if ( cleanOutput.isEmpty() ) {
                                Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( bundle.getString( "Processor.compileAndRun.empty" ), 4 ) + "\n", 
                                    getResultTextColor( useLightTheme ), false );
                            } else {
                                if ( state == ExecutionState.RUNTIME_ERROR || 
                                        state == ExecutionState.TIMEOUT_ERROR ) {
                                    Utils.addFormattedText( 
                                            textPane, 
                                            Utils.identText( cleanOutput, 4 ) + "\n", 
                                            getResultTextColor( useLightTheme ), false );
                                } else {
                                    Utils.addFormattedText( 
                                            textPane, 
                                            Utils.identText( cleanOutput, 4 ) + "\n", 
                                            getResultTextColor( useLightTheme ), true );
                                }
                            }

                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    getResultTextColor( useLightTheme ), false );
                        }

                        // verify output with expected data...
                        if ( state == null ) {

                            if ( Utils.verifyBackwards( 
                                    cleanOutput, test ) ) {
                                state = ExecutionState.PASSED;
                                passedTestCases++;
                            } else {
                                state = ExecutionState.NOT_PASSED;
                            }

                            tcr.setExecutionState( state );

                        }

                        if ( textPane == null ) {
                            System.out.println( "|   |-- test case state: " + state + "\n|" );
                        } else {
                            Utils.addFormattedText( 
                                    textPane, 
                                    bundle.getString( "Processor.compileAndRun.testCaseState" ), 
                                    getResultTextColor( useLightTheme ), false );

                            Color color = Utils.retrieveStateColor( state );
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.getExecutionStateIntString( state ), 
                                    color, false );
                            Utils.addFormattedText( 
                                    textPane, 
                                    "\n|   |\n", 
                                    getResultTextColor( useLightTheme ), false );

                        }
                    
                    }
                    
                    if ( state != ExecutionState.RUNTIME_ERROR ) {
                        if ( passedTestCases == testCases.size() ) {
                            state = ExecutionState.APPROVED;
                        } else {
                            state = ExecutionState.REPROVED;
                        }
                    }
                    
                }
            
            }
            
        } else {
            state = ExecutionState.FILE_NOT_FOUND_ERROR;
        }
        
        if ( textPane == null ) {
            System.out.println( "|-- test state: " + state + "\n" );
        } else {
            Utils.addFormattedText( 
                    textPane, 
                    bundle.getString( "Processor.compileAndRun.testState" ), 
                    getResultTextColor( useLightTheme ), false );
            
            Color color = Utils.retrieveStateColor( state );
            
            Utils.addFormattedText( 
                    textPane, 
                    Utils.getExecutionStateIntString( state ), 
                    color, false );
            Utils.addFormattedText( 
                    textPane, 
                    "\n|\n", 
                    getResultTextColor( useLightTheme ), false );
                    
        }
        
        // clean files
        File baseDirFile = new File( baseDir );
        File[] filesToClean = baseDirFile.listFiles();
        if ( filesToClean != null ) {
            for ( File cFile : filesToClean ) {
                if ( cFile.exists() && !cFile.isDirectory() ) {
                    for ( String ext : filesToRemoveByExtension ) {
                        if ( cFile.getName().endsWith( "." + ext ) ) {
                            cFile.delete();
                        }
                    }
                }
            }
        }
                
        testResult.setExecutionState( state );
        
        return testResult;
        
    }
    
    private static Color getResultTextColor( boolean useLightTheme ) {
        if ( useLightTheme ) {
            return Colors.RESULT_TEXT_LIGHT;
        }
        return Colors.RESULT_TEXT_DARK;
    }
    
}
