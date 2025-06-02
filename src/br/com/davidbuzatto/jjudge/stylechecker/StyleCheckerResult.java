package br.com.davidbuzatto.jjudge.stylechecker;

import br.com.davidbuzatto.jjudge.testsets.Student;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerResult {
    
    private File file;
    private Student student;
    private List<StyleCheckerResultItem> items;
    private boolean containsError;

    public StyleCheckerResult( File file, Student student ) {
        this.file = file;
        this.student = student;
        this.items = new ArrayList<>();
        this.containsError = false;
    }

    public void addItem( StyleCheckerResultItem result  ) {
        items.add( result );
        if ( result.getError() != null ) {
            containsError = true;
        }
    }
    
    public void addItem( int lineNumber, String line, String error ) {
        items.add( new StyleCheckerResultItem( lineNumber, line, error ) );
        if ( error != null ) {
            containsError = true;
        }
    }

    public List<StyleCheckerResultItem> getItems() {
        return items;
    }

    public File getFile() {
        return file;
    }

    public Student getStudent() {
        return student;
    }

    public boolean containsError() {
        return containsError;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "Test result for: " ).append( file.getName() ).append( "\n" ); 
        for ( int i = 0; i < items.size(); i++ ) {
            if ( i != 0 ) {
                sb.append( "\n" );
            }
            sb.append( items.get( i ) );
        }
        return sb.toString();
    }
    
}
