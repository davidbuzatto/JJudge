package br.com.davidbuzatto.jjudge.stylechecker;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public record StyleCheckerToken( String text, int line, int column, StyleCheckerTokenType type ) {
}
