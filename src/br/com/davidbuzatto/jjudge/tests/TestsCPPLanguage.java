/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.tests;

import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.util.List;

/**
 *
 * @author David
 */
public class TestsCPPLanguage {
    
    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) throws Exception {
        
        String baseDir = "C:\\Users\\David\\Documents\\Google Drive\\Projetos\\Corretor de Exercícios\\JJudge\\debugCode\\cpp";
        boolean outputStreams = false;
        int secondsToTimeout = 10;
        
        Student student = Utils.loadStudent( baseDir );
        List<TestSet> testSets = Utils.loadTestSets();
        
        TestSet testSet = null;
        for ( TestSet t : testSets ) {
            if ( t.getDescription().equals( "Debug Test Set CPP" ) ) {
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
