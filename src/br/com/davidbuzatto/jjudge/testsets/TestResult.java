/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

import br.com.davidbuzatto.jjudge.processor.ExecutionState;
import java.util.List;

/**
 *
 * @author David
 */
public class TestResult {
    
    private String name;
    private List<TestCaseResult> testCasesResult;
    private ExecutionState executionState;
    private String errorMessage;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<TestCaseResult> getTestCasesResult() {
        return testCasesResult;
    }

    public void setTestCasesResult( List<TestCaseResult> testCasesResult ) {
        this.testCasesResult = testCasesResult;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage( String errorMessage ) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString() {
        return String.format(  "%s - %02d test case(s) result: %s", name, testCasesResult.size(), executionState );
    }
    
}
