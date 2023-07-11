package br.com.davidbuzatto.jjudge.tests;

import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.util.List;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestsCLanguage {
    
    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) throws Exception {
        
        String baseDir = "debugCode/c";
        boolean outputStreams = false;
        int secondsToTimeout = 10;
        
        Student student = Utils.loadStudent( baseDir );
        List<TestSet> testSets = Utils.loadTestSets();
        
        TestSet testSet = null;
        for ( TestSet t : testSets ) {
            if ( t.getDescription().equals( "Debug Test Set C" ) ) {
                testSet = t;
                break;
            }
        }
        
        if ( testSet != null ) {
            
            TestSetResult tResSet = Utils.verify( 
                    testSet, 
                    student, 
                    baseDir, 
                    secondsToTimeout, 
                    outputStreams,
                    null,
                    null );

            System.out.println( testSet.getDescription() );
            System.out.println( tResSet.getStudent() );
            for ( TestResult tRes : tResSet.getTestResults() ) {
                System.out.println( "    " + tRes );
            }
            System.out.println( "    grade: " + tResSet.getGrade() );
            
        }
        
    }
    
}
