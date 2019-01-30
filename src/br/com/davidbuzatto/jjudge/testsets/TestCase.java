/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

import br.com.davidbuzatto.jjudge.utils.Utils;

/**
 *
 * @author David
 */
public class TestCase implements Cloneable {
    
    private String input;
    private String output;

    public String getInput() {
        return input;
    }

    public void setInput( String input ) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput( String output ) {
        this.output = output;
    }

    @Override
    public String toString() {
        
        return String.format( "<html>input: <strong><font color='#0000FF'>%s</font></strong> - output: <strong><font color='#00CC00'>%s</font></strong></html>", 
                input.isEmpty() ? "&lt;empty&gt;" : input.length() > 10 ? Utils.showScapeChars( input.substring( 0, 10 ) ) + "..." : Utils.showScapeChars( input ), 
                output.isEmpty() ? "&lt;empty&gt;" : output.length() > 10 ? Utils.showScapeChars( output.substring( 0, 10 ) ) + "..." : Utils.showScapeChars( output ) );
        
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        TestCase clone = (TestCase) super.clone();
        
        clone.input = this.input;
        clone.output = this.output;
        
        return clone;
        
    }
    
}
