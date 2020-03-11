/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

import br.com.davidbuzatto.jjudge.utils.Utils;
import java.util.ResourceBundle;

/**
 *
 * @author David
 */
public class TestCase implements Cloneable {
    
    private String input;
    private String output;
    
    private transient ResourceBundle bundle = Utils.bundle;

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
        
        return String.format( bundle.getString( "TestCase.toString.format" ), 
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
