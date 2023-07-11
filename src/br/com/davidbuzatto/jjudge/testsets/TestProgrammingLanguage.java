package br.com.davidbuzatto.jjudge.testsets;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public enum TestProgrammingLanguage {
    
    C( "c" ),
    CPP( "cpp" ),
    JAVA( "java" ),
    PYTHON( "py" );
    
    public final String extension;
    
    private TestProgrammingLanguage( String extension ) {
        this.extension = extension;
    }
    
}
