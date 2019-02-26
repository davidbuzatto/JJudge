/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

import br.com.davidbuzatto.jjudge.processor.ExecutionState;

/**
 *
 * @author David
 */
public class TestCaseResult {
    
    private String input;
    private String output;
    private String testOutput;
    private ExecutionState executionState;

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

    public String getTestOutput() {
        return testOutput;
    }

    public void setTestOutput( String testOutput ) {
        this.testOutput = testOutput;
    }

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState( ExecutionState executionState ) {
        this.executionState = executionState;
    }

    
    
}
