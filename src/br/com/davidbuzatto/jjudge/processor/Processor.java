/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.processor;

import br.com.davidbuzatto.jjudge.testsets.TestCase;
import br.com.davidbuzatto.jjudge.testsets.TestProgrammingLanguage;
import br.com.davidbuzatto.jjudge.utils.StreamGobbler;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextPane;

/**
 *
 * @author David
 */
public class Processor {
    
    public static ExecutionState compileAndRun( 
            String fileName, 
            String baseDir, 
            int secondsToTimeout, 
            boolean outputStreams,
            List<TestCase> testCases,
            TestProgrammingLanguage pLang,
            JTextPane textPane ) throws IOException, InterruptedException {
        
        
        ExecutionState state = null;
        Runtime rt = Runtime.getRuntime();
        File dir = new File( baseDir );
        int passedTestCases = 0;
        
        String sourceExt = pLang.name().toLowerCase();
        sourceExt = sourceExt.equals( "python" ) ? "py" : sourceExt;
        
        String cmdExec = "";
        File[] filesToRemove = {};
        String[] compilationCommands = {};
        String[] threadId = {};
        
        
        switch ( pLang ) {
            
            case C:
                
                cmdExec = String.format( "%s/%s.exe", baseDir, fileName );
                
                filesToRemove = new File[]{
                    new File( String.format( "%s/%s.o", baseDir, fileName ) ),
                    new File( String.format( "%s/%s.exe", baseDir, fileName ) ),
                    new File( String.format( "%s/output.txt", baseDir ) )
                };

                compilationCommands = new String[]{
                    String.format( "gcc -c %s.c -o %s.o", fileName, fileName ),
                    String.format( "g++ -o %s.exe %s.o", fileName, fileName )
                };

                threadId = new String[]{
                    "    gcc (comp)",
                    "    g++ (link)"
                };
                
                break;
                
                
                
            case CPP:
                
                cmdExec = String.format( "%s/%s.exe", baseDir, fileName );
                
                filesToRemove = new File[]{
                    new File( String.format( "%s/%s.o", baseDir, fileName ) ),
                    new File( String.format( "%s/%s.exe", baseDir, fileName ) ),
                    new File( String.format( "%s/output.txt", baseDir ) )
                };

                compilationCommands = new String[]{
                    String.format( "g++ -c %s.cpp -o %s.o", fileName, fileName ),
                    String.format( "g++ -o %s.exe %s.o", fileName, fileName )
                };

                threadId = new String[]{
                    "    g++ (comp)",
                    "    g++ (link)"
                };
                
                break;
                
                
                
            case JAVA:
                
                cmdExec = String.format( "java -Duser.language=en -Duser.country=US %s", fileName );
                
                filesToRemove = new File[]{
                    new File( String.format( "%s/%s.class", baseDir, fileName ) ),
                    new File( String.format( "%s/output.txt", baseDir ) )
                };

                compilationCommands = new String[]{
                    String.format( "javac %s.java", fileName )
                };

                threadId = new String[]{
                    "    javac"
                };
                
                break;
                
                
                
            case PYTHON:
                
                cmdExec = String.format( "python %s.py", fileName );
                break;
            
        }
        
        
        
        
        if ( textPane == null ) {
            System.out.printf( "Processing file %s/%s.%s\n", baseDir, fileName, sourceExt );
        } else {
            Utils.addFormattedText( 
                    textPane, 
                    String.format( "|-- Processing file %s\\%s.%s\n", baseDir, fileName, sourceExt ), 
                    Color.BLACK );
        }

        
        if ( new File( String.format( "%s/%s.%s", baseDir, fileName, sourceExt ) ).exists() ) {
            
            // compilation
            for ( int i = 0; i < compilationCommands.length; i++ ) {
                
                Process p = rt.exec( compilationCommands[i], null, dir );
                StreamGobbler sg = new StreamGobbler( 
                        p.getInputStream(), 
                        p.getErrorStream(), 
                        null, 
                        threadId[i], 
                        outputStreams );
                Thread t = new Thread( sg );
                t.start();
                t.join();
                
                // if error...
                if ( sg.isProcessErrorStreamDataAvailable() ) {
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
                            "|   |-- compiled!\n", 
                            Color.BLACK );
                    }
                    
                    Utils.addFormattedText( 
                            textPane, 
                            "|   |-- executing...\n", 
                            Color.BLACK );
                    
                }

                int i = 1;
                for ( TestCase tc : testCases ) {

                    state = null;

                    String input = tc.getInput();
                    String test = tc.getOutput();

                    Process pExec = rt.exec( cmdExec, null, dir );
                    PrintWriter pwExec = new PrintWriter( pExec.getOutputStream() );
                    FileOutputStream fosOutput = new FileOutputStream( 
                            new File( String.format(  "%s/output.txt" , baseDir ) ) );
                    StreamGobbler sgExec = new StreamGobbler( 
                            pExec.getInputStream(), 
                            pExec.getErrorStream(), 
                            fosOutput, 
                            "    execution", 
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
                                String.format( "|   |-- test case %02d:\n", i++ ), 
                                Color.BLACK );
                        Utils.addFormattedText( 
                                textPane, 
                                "|   |   |-- process test input:\n", 
                                Color.BLACK );
                        if ( input.isEmpty() ) {
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( "<empty>", 4 ) + "\n", 
                                    Color.BLACK );
                        } else {
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( input, 4 ) + "\n", 
                                    Color.BLACK );
                        }
                        Utils.addFormattedText( 
                                textPane, 
                                "|   |   |\n", 
                                Color.BLACK );

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

                    // verify output with expected data...
                    if ( state == null ) {

                        // reading process output from file
                        StringBuilder sbOutput = new StringBuilder();

                        Scanner sOutput = new Scanner( 
                                new File( String.format( "%s/%s", baseDir, "output.txt" ) ) );

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

                        if ( textPane == null ) {
                            System.out.println( "|   |-- process output: " );
                            System.out.println( Utils.identText( sbOutput.toString(), 3 ) );
                            System.out.println( "|   |" );
                            System.out.println( "|   |-- process test output: " );
                            System.out.println( Utils.identText( test, 3 ) );
                            System.out.println( "|   |" );
                        } else {
                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |-- process output:\n", 
                                    Color.BLACK );
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( sbOutput.toString(), 4 ) + "\n", 
                                    Color.BLACK );
                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    Color.BLACK );
                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |-- process test output:\n", 
                                    Color.BLACK );
                            Utils.addFormattedText( 
                                    textPane, 
                                    Utils.identText( test, 4 ) + "\n", 
                                    Color.BLACK );
                            Utils.addFormattedText( 
                                    textPane, 
                                    "|   |   |\n", 
                                    Color.BLACK );
                        }

                        if ( Utils.verifyBackwards( 
                                sbOutput.toString(), test ) ) {
                            state = ExecutionState.PASSED;
                            passedTestCases++;
                        } else {
                            state = ExecutionState.NOT_PASSED;
                        }

                    }

                    if ( textPane == null ) {
                        System.out.println( "|   |-- test case state: " + state + "\n|" );
                    } else {
                        Utils.addFormattedText( 
                                textPane, 
                                "|   |   |-- test case state: ", 
                                Color.BLACK );

                        Color color = Color.BLACK;
                        switch ( state ) {
                            case PASSED:
                                color = Color.GREEN.darker().darker();
                                break;
                            case NOT_PASSED:
                                color = Color.RED.darker();
                                break;
                            default:
                                color = Color.ORANGE.darker();
                                break;
                        }
                        Utils.addFormattedText( 
                                textPane, 
                                state.toString(), 
                                color );
                        Utils.addFormattedText( 
                                textPane, 
                                "\n|   |\n", 
                                Color.BLACK );
                    }

                }

                if ( passedTestCases == testCases.size() ) {
                    state = ExecutionState.APPROVED;
                } else {
                    state = ExecutionState.REPROVED;
                }

                // clean files
                for ( File f : filesToRemove ) {
                    f.delete();
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
                    "|   |-- test state: ", 
                    Color.BLACK );
            
            Color color = Color.BLACK;
            
            switch ( state ) {
                case APPROVED:
                    color = Color.GREEN.darker();
                    break;
                case REPROVED:
                    color = Color.RED;
                    break;
                default:
                    color = Color.ORANGE.darker();
                    break;
            }
            
            Utils.addFormattedText( 
                    textPane, 
                    state.toString(), 
                    color );
            Utils.addFormattedText( 
                    textPane, 
                    "\n|\n", 
                    Color.BLACK );
                    
        }
        
        return state;
        
    }
    
}
