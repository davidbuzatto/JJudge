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
public class TestSet implements Cloneable {
    
    private String description;
    private TestProgrammingLanguage programmingLanguage;
    private List<Test> tests;

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public TestProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage( TestProgrammingLanguage programmingLanguage ) {
        this.programmingLanguage = programmingLanguage;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests( List<Test> tests ) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return String.format(  "%s (%s) - %02d test(s)", description, programmingLanguage, tests.size() );
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        
        TestSet clone = (TestSet) super.clone();
        
        clone.description = this.description;
        clone.programmingLanguage = this.programmingLanguage;
        clone.tests = new ArrayList<>();
        
        for ( Test t : tests ) {
            clone.tests.add( (Test) t.clone() );
        }
        
        return clone;
        
    }
    
}
