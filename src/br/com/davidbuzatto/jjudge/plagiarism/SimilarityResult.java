package br.com.davidbuzatto.jjudge.plagiarism;

import java.io.File;

/**
 * Models the results similarity test using diff.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SimilarityResult {
    
    private File file1;
    private File file2;
    private double similarity;

    public SimilarityResult( File file1, File file2, double similarity ) {
        this.file1 = file1;
        this.file2 = file2;
        this.similarity = similarity;
    }

    public File getFile1() {
        return file1;
    }

    public File getFile2() {
        return file2;
    }

    public double getSimilarity() {
        return similarity;
    }

    @Override
    public String toString() {
        return String.format( "%s x %s = %.2f", file1.getName(), file2.getName(), similarity );
    }
    
}
