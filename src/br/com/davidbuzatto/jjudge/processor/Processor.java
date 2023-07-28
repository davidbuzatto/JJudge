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
    
    public static TestResult compileAndRun( 
            String presentationName,
            String fileName, 
            String baseDir, 
            int secondsToTimeout, 
            boolean outputStreams,
            List<TestCase> testCases,
            TestProgrammingLanguage pLang,
            JTextPane textPane,
            List<File> javaClasspathFiles ) throws IOException, InterruptedException {
        
        ResourceBundle bundle = Utils.bundle;
        
        TestResult testResult = new TestResult();
        testResult.setPresentationName( presentationName );
        testResult.setName( fileName );
        testResult.setTestCasesResult( new ArrayList<>() );
            
        ExecutionState state = null;
        Runtime rt = Runtime.getRuntime();
        File dir = new File( baseDir );
        int passedTestCases = 0;
        
        String sourceExt = pLang.name().toLowerCase();
        sourceExt = sourceExt.equals( "python" ) ? "py" : sourceExt;
        
        String[] cmdExec = new String[0];
        String[] filesToRemoveByExtension = { "o", "exe", "class", "out" };
        String[][] compilationCommands = {};
        String[] threadId = {};
        
        
        switch ( pLang ) {
            
            case C:
                
                cmdExec = new String[]{
                    String.format( "%s%s%s.exe", baseDir, File.separator, fileName )
                };

                compilationCommands = new String[][]{
                    String.format( "gcc -Werror -Wfatal-errors -c %s.c -o %s.o", fileName, fileName ).split( "\\s+" ),
                    String.format( "g++ -o %s.exe %s.o", fileName, fileName ).split( "\\s+" )
                };

                threadId = new String[]{
                    "    gcc (comp)",
                    "    g++ (link)"
                };
                
                break;
                
                
                
            case CPP:
                
                cmdExec = new String[]{
                    String.format( "%s%s%s.exe", baseDir, File.separator, fileName )
                };

                compilationCommands = new String[][]{
                    String.format( "g++ -Werror -Wfatal-errors -c %s.cpp -o %s.o", fileName, fileName ).split( "\\s+" ),
                    String.format( "g++ -o %s.exe %s.o", fileName, fileName ).split( "\\s+" )
                };

                threadId = new String[]{
                    "    g++ (comp)",
                    "    g++ (link)"
                };
                
                break;
                
                
                
            case JAVA:
                
                String classpathFiles = "";
                String classpathFilesWithCP = "";
                
                if ( javaClasspathFiles != null ) {
                    classpathFiles = Utils.buildDependenciesPath( javaClasspathFiles );
                    classpathFilesWithCP = String.format( "-cp \"%s\"", classpathFiles );
                }
                
                /*System.out.println( classpathFiles );
                System.out.println( classpathFilesWithCP );*/
                
                if ( fileName.contains( "/" ) ) {
                        
                    int ind = fileName.lastIndexOf( '/' );
                    String fileDir = fileName.substring( 0, ind );
                    String justName = fileName.substring( ind + 1 );

                    cmdExec = String.format( "java -Duser.language=en -Duser.country=US -cp \"%s%s%s%s%s\" %s", 
                            baseDir, 
                            File.separator,
                            fileDir, 
                            File.pathSeparator, 
                            classpathFiles, 
                            justName ).split( "\\s+" );

                    compilationCommands = new String[][]{
                        String.format( "javac -Xlint:unchecked %s.java -cp \"%s%s%s%s%s\"", 
                                fileName, 
                                baseDir, 
                                File.separator,
                                fileDir, 
                                File.pathSeparator, 
                                classpathFiles ).split( "\\s+" )
                    };
                    
                    /*System.out.println( String.format( "javac -Xlint:unchecked %s.java -cp \"%s%s%s%s%s\"", 
                            fileName, 
                            baseDir, 
                            File.separator,
                            fileDir, 
                            File.pathSeparator, 
                            classpathFiles ) );*/
                    
                } else {
                    
                    cmdExec = String.format( "java -Duser.language=en -Duser.country=US %s%s", classpathFilesWithCP + " ", fileName ).split( "\\s+" );

                    compilationCommands = new String[][]{
                        String.format( "javac -Xlint:unchecked %s.java%s", fileName, " " + classpathFilesWithCP ).split( "\\s+" )
                    };
                
                }

                threadId = new String[]{
                    "    javac"
                };
                
                break;
                
                
                
            case PYTHON:
                
                cmdExec = String.format( "python %s.py", fileName ).split( "\\s+" );
                break;
            
        }
        
        
        
        
        if ( textPane == null ) {
            System.out.printf( "Processing file %s/%s.%s\n", baseDir, fileName, sourceExt );
        } else {
            Utils.addFormattedText( 
                    textPane, 
                    String.format( bundle.getString( "Processor.compileAndRun.processing" ), baseDir, fileName, sourceExt ), 
                    Color.BLACK, false );
        }

        
        if ( new File( String.format( "%s/%s.%s", baseDir, fileName, sourceExt ) ).exists() ) {
            
            // compilation
            for ( int i = 0; i < compilationCommands.length; i++ ) {
                
                Process p = rt.exec( compilationCommands[i], null, dir );
                StreamGobbler sg;
                
                try ( FileOutputStream fosOutput = new FileOutputStream( 
                        new File( String.format(  "%s/error.out" , baseDir ) ) ) ) {
                    
                    sg = new StreamGobbler( 
                            p.getInputStream(),
                            p.getErrorStream(),
                            fosOutput,
                            threadId[i],
                            outputStreams );
                    Thread t = new Thread( sg );
                    t.start();
                    t.join();
                    
                }
                
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
                                Color.BLACK, false );
                        
                        Utils.addFormattedText( 
                                textPane, 
                                bundle.getString( "Processor.compileAndRun.compilationError" ), 
                                Colors.COMPILATION_ERROR.darker(), false );

                        Utils.addFormattedText( 
                                textPane, 
                                Utils.identText( sbOutput.toString(), 3 ) + "\n", 
                                Color.BLACK, false );

                    }
                        
                    state = ExecutionState.COMPILATION_ERROR;
                    break;
                    
                }
                
            }

            if ( state != ExecutionState.COMPILATION_ERROR ) {

                if ( textPane == null ) {
                    
                    if ( compilationCommands.length > 0 ) {
                        System.out.println( "|-- compiled!" );
                    }
                    
                    System.out.println( "|-- executing..." );
                    
                } else {
                    
                    if ( compilationCommands.length > 0 ) {
                        Utils.addFormattedText( 
                            textPane, 
                            bundle.getString( "Processor.compileAndRun.compiled" ), 
                            Color.BLACK, false );
                    }
                    
                    Utils.addFormattedText( 
                            textPane, 
                            bundle.getString( "Processor.compileAndRun.executing" ), 
                            Color.BLACK, false );
                    
                }

                int i = 1;
                for ( TestCase tc : testCases ) {

                    state = null;

                    String input = tc.getInput();
                    String test = tc.getOutput();

                    Process pExec = rt.exec( cmdExec, null, dir );
                    PrintWriter pwExec = new PrintWriter( pExec.getOutputStream() );
                    FileOutputStream fosOutput = new FileOutputStream( 
                            new File( String.format(  "%s/output.out" , baseDir ) ) );
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
                                Color.BLACK, false );
                        Utils.addFormattedText( 
                                textPane, 
                                bundle.getString( "Processor.compileAndRun.processTestInput" ), 
                                Color.BLACK, false );
                        if ( input.isEmpty() ) {
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( bundle.getString( "Processor.compileAndRun.empty" ), 4 ) + "\n", 
                                    Color.BLACK, false );
                        } else {
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( input, 4 ) + "\n", 
                                    Color.BLACK, false );
                        }
                        Utils.addFormattedText( 
                                textPane, 
                                "|   |   |\n", 
                                Color.BLACK, false );

                    }

                    for ( String s : input.split( "\n" ) ) {
                        if ( !s.isEmpty() ) {
                            pwExec.println( s );
                        }
                    }
                    pwExec.close();

                    // wait for long process time
                    if ( !pExec.waitFor( secondsToTimeout, TimeUnit.SECONDS ) ) {
                        state = ExecutionState.TIMEOUT_ERROR;
                        pExec.destroyForcibly();
                    }
                    tExec.join();

                    // abnormal termination
                    if ( state == null ) {
                        if ( pExec.exitValue() != 0 ) {
                            state = ExecutionState.RUNTIME_ERROR;
                        }
                    }

                    fosOutput.close();

                    
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
                                    Color.BLACK, false );

                            if ( test.isEmpty() ) {
                                Utils.addFormattedText( 
                                        textPane, 
                                        Utils.identText( bundle.getString( "Processor.compileAndRun.empty" ), 4 ) + "\n", 
                                        Color.BLACK, false );
                            } else {
                                Utils.addFormattedText( 
                                        textPane, 
                                        Utils.identText( test, 4 ) + "\n", 
                                        Color.BLACK, true );
                            }

                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    Color.BLACK, false );

                            Utils.addFormattedText( 
                                    textPane, 
                                    bundle.getString( "Processor.compileAndRun.processOutput" ), 
                                    Color.BLACK, false );

                            if ( cleanOutput.isEmpty() ) {
                                Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( bundle.getString( "Processor.compileAndRun.empty" ), 4 ) + "\n", 
                                    Color.BLACK, false );
                            } else {
                                if ( state == ExecutionState.RUNTIME_ERROR || 
                                        state == ExecutionState.TIMEOUT_ERROR ) {
                                    Utils.addFormattedText( 
                                            textPane, 
                                            Utils.identText( cleanOutput, 4 ) + "\n", 
                                            Color.BLACK, false );
                                } else {
                                    Utils.addFormattedText( 
                                            textPane, 
                                            Utils.identText( cleanOutput, 4 ) + "\n", 
                                            Color.BLACK, true );
                                }
                            }

                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    Color.BLACK, false );
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
                                    Color.BLACK, false );

                            Color color = Utils.retrieveStateColor( state );
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.getExecutionStateIntString( state ), 
                                    color, false );
                            Utils.addFormattedText( 
                                    textPane, 
                                    "\n|   |\n", 
                                    Color.BLACK, false );

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
                    Color.BLACK, false );
            
            Color color = Utils.retrieveStateColor( state );
            
            Utils.addFormattedText( 
                    textPane, 
                    Utils.getExecutionStateIntString( state ), 
                    color, false );
            Utils.addFormattedText( 
                    textPane, 
                    "\n|\n", 
                    Color.BLACK, false );
                    
        }
        
        // clean files
        File baseDirFile = new File( baseDir );
        for ( File cFile : baseDirFile.listFiles() ) {
            if ( cFile.exists() && !cFile.isDirectory() ) {
                for ( String ext : filesToRemoveByExtension ) {
                    if ( cFile.getName().endsWith( "." + ext ) ) {
                        cFile.delete();
                    }
                }
            }
        }
                
        testResult.setExecutionState( state );
        
        return testResult;
        
    }
    
}
