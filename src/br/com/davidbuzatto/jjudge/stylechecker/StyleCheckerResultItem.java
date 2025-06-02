package br.com.davidbuzatto.jjudge.stylechecker;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerResultItem {
    
    private int lineNumber;
    private String line;
    private String error;

    public StyleCheckerResultItem( int lineNumber, String line, String error ) {
        this.lineNumber = lineNumber;
        this.line = line;
        this.error = error;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    
    public String getLine() {
        return line;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        if ( error != null ) {
            return String.format( "%04d: %s    // %s", lineNumber, line, error );
        }
        return String.format( "%04d: %s", lineNumber, line );
    }
    
}
