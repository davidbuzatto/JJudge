/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * A class to consume processed streams.
 * 
 * @author David Buzatto
 * Based on: When Runtime.exe() Won't
 * URL: http://www.javaworld.com/article/2071275/core-java/when-runtime-exec---won-t.html?page=1
 */
public class StreamGobbler extends Thread {

    private InputStream is;
    private InputStream es;
    private OutputStream os;
    private String description;
    private boolean outputStreams;
    
    private boolean processOutputStreamDataAvailable;
    private boolean processErrorStreamDataAvailable;

    public StreamGobbler( InputStream is, InputStream es, OutputStream os, String description, boolean outputStreams ) {
        this.is = is;
        this.es = es;
        this.os = os;
        this.description = description;
        this.outputStreams = outputStreams;
    }

    @Override
    public void run() {
        
        try {
            
            String line = null;
            PrintWriter pw = null;
            
            if ( os != null ) {
                pw = new PrintWriter( os );
            }

            InputStreamReader isr = new InputStreamReader( is );
            BufferedReader ibr = new BufferedReader( isr );
            
            while ( ( line = ibr.readLine() ) != null ) {
                if ( pw != null ) {
                    pw.println( line );
                }
                if ( outputStreams ) {
                    System.out.println( description + "> " + line );
                }
                processOutputStreamDataAvailable = true;
            }
            
            
            InputStreamReader esr = new InputStreamReader( es );
            BufferedReader ebr = new BufferedReader( esr );
            
            while ( ( line = ebr.readLine() ) != null ) {
                if ( pw != null ) {
                    pw.println( line );
                }
                if ( outputStreams ) {
                    System.out.println( description + "> " + line );
                }
                processErrorStreamDataAvailable = true;
            }
            
            if ( pw != null ) {
                pw.flush();
                pw.close();
            }
            
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
        
    }

    public boolean isProcessOutputStreamDataAvailable() {
        return processOutputStreamDataAvailable;
    }

    public boolean isProcessErrorStreamDataAvailable() {
        return processErrorStreamDataAvailable;
    }
    
}
