/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.processor;

import br.com.davidbuzatto.jjudge.testsets.TestCase;
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
public class ProcessorCPP {
    
    public static ExecutionState compileAndRun( 
            String fileName, 
            String baseDir, 
            int secondsToTimeout, 
            boolean outputStreams,
            List<TestCase> testCases,
            JTextPane textPane ) throws IOException, InterruptedException {
        
        ExecutionState state = null;
        Runtime rt = Runtime.getRuntime();
        File dir = new File( baseDir );
        int passedTestCases = 0;
        
        File[] filesToRemove = {
            new File( String.format( "%s/%s.o", baseDir, fileName ) ),
            new File( String.format( "%s/%s.exe", baseDir, fileName ) ),
            new File( String.format( "%s/output.txt", baseDir ) )
        };
        
        String cmdGPPComp = String.format( "g++ -c %s.cpp -o %s.o", fileName, fileName );
        String cmdGPPLink = String.format( "g++ -o %s.exe %s.o", fileName, fileName );
        String cmdExec = String.format( "%s/%s.exe", baseDir, fileName );
        
        
        if ( textPane == null ) {
            System.out.printf( "Processing file %s/%s.cpp\n", baseDir, fileName );
        } else {
            Utils.addFormattedText( 
                    textPane, 
                    String.format( "|-- Processing file %s\\%s.cpp\n", baseDir, fileName ), 
                    Color.BLACK );
        }

        if ( new File( String.format( "%s/%s.cpp", baseDir, fileName ) ).exists() ) {
            
            Process pGPPComp = rt.exec( cmdGPPComp, null, dir );
            StreamGobbler sgGPPComp = new StreamGobbler( 
                    pGPPComp.getInputStream(), 
                    pGPPComp.getErrorStream(), 
                    null, 
                    "    g++ (comp)", 
                    outputStreams );
            Thread tGPPComp = new Thread( sgGPPComp );
            tGPPComp.start();
            tGPPComp.join();

            // no error
            if ( !sgGPPComp.isProcessErrorStreamDataAvailable() ) {

                Process pGPPLink = rt.exec( cmdGPPLink, null, dir );
                StreamGobbler sgGPPLink = new StreamGobbler( 
                        pGPPLink.getInputStream(), 
                        pGPPLink.getErrorStream(), 
                        null, 
                        "    g++ (link)", 
                        outputStreams );
                Thread tGPPLink = new Thread( sgGPPLink );
                tGPPLink.start();
                tGPPLink.join();

                // no error
                if ( !sgGPPLink.isProcessErrorStreamDataAvailable() ) {

                    if ( textPane == null ) {
                        System.out.println( "|-- compiled!" );
                        System.out.println( "|-- executing..." );
                    } else {
                        Utils.addFormattedText( 
                                textPane, 
                                "|   |-- compiled!\n|   |-- executing...\n", 
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

                        //sExec.close();
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

                } else {
                    state = ExecutionState.COMPILATION_ERROR;
                }

            } else {
                state = ExecutionState.COMPILATION_ERROR;
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
