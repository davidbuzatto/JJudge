package br.com.davidbuzatto.jjudge.testsets;

import br.com.davidbuzatto.jjudge.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Test implements Cloneable {
    
    private String name;
    private List<TestCase> testCases;
    
    private transient ResourceBundle bundle = Utils.bundle;

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
        return String.format(  bundle.getString( "Test.toString.format" ), 
                name, testCases.size() );
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
