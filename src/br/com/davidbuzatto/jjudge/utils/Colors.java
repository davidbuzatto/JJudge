package br.com.davidbuzatto.jjudge.utils;

import java.awt.Color;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class Colors {
    
    public static final Color APPROVED = Color.GREEN.darker();
    public static final Color REPROVED = Color.RED;
    public static final Color PASSED = Color.GREEN.darker().darker();
    public static final Color NOT_PASSED = Color.RED.darker();
    public static final Color COMPILATION_ERROR = new Color( 255, 127, 39 );
    public static final Color RUNTIME_ERROR = new Color( 163, 73, 164 );
    public static final Color TIMEOUT_ERROR = new Color( 120, 0, 240 );
    public static final Color FILE_NOT_FOUND_ERROR = new Color( 120, 120, 120 );
    public static final Color DONT_CHECK = new Color( 0, 176, 240 );
    public static final Color ERROR = Color.ORANGE.darker();
    
    public static final Color VERIFICATION_ERROR_F = Color.RED;
    public static final Color VERIFICATION_ERROR_B = new Color( 50, 0, 0 );
    
    public static final Color RESULT_TEXT_LIGHT = Color.BLACK;
    public static final Color RESULT_TEXT_DARK = new Color( 169, 183, 198 );
    
    public static final Color PROCESSING_MESSAGE_LIGHT = Color.BLUE;
    public static final Color PROCESSING_MESSAGE_DARK = new Color( 63, 115, 187 );
    
    public static final Color REDO_COLOR = new Color( 197, 255, 186 );
    
}
