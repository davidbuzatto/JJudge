/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

import java.util.List;

/**
 *
 * @author David
 */
public class TestSetResult {
    
    private Student student;
    private double grade;
    private List<TestResult> testResults;
    private String error;

    public Student getStudent() {
        return student;
    }

    public void setStudent( Student student ) {
        this.student = student;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade( double grade ) {
        this.grade = grade;
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults( List<TestResult> testResults ) {
        this.testResults = testResults;
    }

    public String getError() {
        return error;
    }

    public void setError( String error ) {
        this.error = error;
    }
    
}
