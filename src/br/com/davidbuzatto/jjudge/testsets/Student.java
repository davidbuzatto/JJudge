package br.com.davidbuzatto.jjudge.testsets;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode( this.code );
        hash = 53 * hash + Objects.hashCode( this.name );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Student other = (Student) obj;
        if ( !Objects.equals( this.code, other.code ) ) {
            return false;
        }
        return Objects.equals( this.name, other.name );
    }
    
}
