package br.com.davidbuzatto.jjudge.testsets;

import java.io.File;
import java.util.List;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestSetResult {
    
    private Student student;
    private double grade;
    private List<TestResult> testResults;
    private String error;
    private File packageFile;

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

    public File getPackageFile() {
        return packageFile;
    }

    public void setPackageFile( File packageFile ) {
        this.packageFile = packageFile;
    }
    
}
