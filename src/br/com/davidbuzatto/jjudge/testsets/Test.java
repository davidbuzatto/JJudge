/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class Test implements Cloneable {
    
    private String name;
    private List<TestCase> testCases;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases( List<TestCase> testCases ) {
        this.testCases = testCases;
    }

    @Override
    public String toString() {
        return String.format(  "%s - %02d test case(s)", name, testCases.size() );
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        Test clone = (Test) super.clone();
        
        clone.name = this.name;
        clone.testCases = new ArrayList<>();
        
        for ( TestCase tc : testCases ) {
            clone.testCases.add( (TestCase) tc.clone() );
        }
        
        return clone;
        
    }
    
}
