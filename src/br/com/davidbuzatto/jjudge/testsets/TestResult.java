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
public class TestResult {
    
    private Test test;
    private ExecutionState executionState;

    public Test getTest() {
        return test;
    }

    public void setTest( Test test ) {
        this.test = test;
    }

    public ExecutionState getExecutionState() {
        return executionState;
    }

    public void setExecutionState( ExecutionState executionState ) {
        this.executionState = executionState;
    }

    public boolean isApproved() {
        return executionState == ExecutionState.APPROVED;
    }
    
    @Override
    public String toString() {
        return test.getName() + ": " + executionState;
    }
    
}
