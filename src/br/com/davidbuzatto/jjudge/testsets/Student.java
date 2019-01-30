/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.testsets;

/**
 *
 * @author David
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
