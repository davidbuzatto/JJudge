/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.processor;

/**
 *
 * @author David
 */
public enum ExecutionState {
    
    // test case
    PASSED,
    NOT_PASSED,
    
    // whole test
    APPROVED,
    REPROVED,
    COMPILATION_ERROR,
    RUNTIME_ERROR,
    TIMEOUT_ERROR,
    FILE_NOT_FOUND_ERROR
    
}
