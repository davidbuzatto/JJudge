package br.com.davidbuzatto.jjudge.processor;

/**
 *
 * @author Prof. Dr. David Buzatto
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
    FILE_NOT_FOUND_ERROR,
    DONT_CHECK
    
}
