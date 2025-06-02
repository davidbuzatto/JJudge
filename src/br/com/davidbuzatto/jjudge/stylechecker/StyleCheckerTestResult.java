package br.com.davidbuzatto.jjudge.stylechecker;

import br.com.davidbuzatto.jjudge.testsets.Student;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerTestResult {
    
    private List<StyleCheckerResult> standaloneResults;
    private Map<Student, List<StyleCheckerResult>> studentResults;
    
    public StyleCheckerTestResult() {
        standaloneResults = new ArrayList<>();
        studentResults = new LinkedHashMap<>();
    }
    
    public void addStandaloneResult( StyleCheckerResult result ) {
        standaloneResults.add( result );
    }
    
    public void addStudentResult( Student student, StyleCheckerResult result ) {
        List<StyleCheckerResult> results = studentResults.get( student );
        if ( results == null ) {
            results = new ArrayList<>();
            studentResults.put( student, results );
        }
        results.add( result );
    }

    public List<StyleCheckerResult> getStandaloneResults() {
        return standaloneResults;
    }

    public Map<Student, List<StyleCheckerResult>> getStudentResults() {
        return studentResults;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Standalone Results: \n" );
        for ( StyleCheckerResult r : standaloneResults ) {
            sb.append( r ).append( "\n" );
        }
        
        sb.append( "\nStudent Results: \n" );
        for ( Map.Entry<Student, List<StyleCheckerResult>> e : studentResults.entrySet() ) {
            sb.append( "Student: " ).append( e.getKey() ).append( "\n" );
            for ( StyleCheckerResult r : e.getValue() ) {
                sb.append( r ).append( "\n" );
            }
        }
        
        return sb.toString();
        
    }
    
}
