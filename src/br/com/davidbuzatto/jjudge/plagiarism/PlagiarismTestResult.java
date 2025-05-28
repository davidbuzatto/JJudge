package br.com.davidbuzatto.jjudge.plagiarism;

import br.com.davidbuzatto.jjudge.testsets.Student;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Models the results of a plagiarism test.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PlagiarismTestResult {
    
    private Student student1;
    private Student student2;
    private List<SimilarityResult> similarityResults;

    public PlagiarismTestResult( Student student1, Student student2 ) {
        this.student1 = student1;
        this.student2 = student2;
        this.similarityResults = new ArrayList<>();
    }
    
    public void addSimilarityResult( File file1, File file2, double similarity ) {
        similarityResults.add( new SimilarityResult( file1, file2, similarity ) );
    }

    public Student getStudent1() {
        return student1;
    }

    public Student getStudent2() {
        return student2;
    }

    public List<SimilarityResult> getSimilarityResults() {
        return similarityResults;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( String.format( "%s x %s:", student1.getCode(), student2.getCode() ) );
        for ( SimilarityResult r : similarityResults ) {
            sb.append( "\n    " ).append( r.toString() );
        }
        
        return sb.toString();
        
    }
    
}
