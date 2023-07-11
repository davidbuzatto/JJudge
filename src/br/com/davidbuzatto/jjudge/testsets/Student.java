package br.com.davidbuzatto.jjudge.testsets;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Student {
    
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + code;
    }
    
}
