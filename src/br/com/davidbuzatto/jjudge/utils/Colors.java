/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.utils;

import java.awt.Color;

/**
 *
 * @author David
 */
public class Colors {
    
    public static final Color APPROVED = Color.GREEN.darker();
    public static final Color REPROVED = Color.RED;
    public static final Color PASSED = Color.GREEN.darker().darker();
    public static final Color NOT_PASSED = Color.RED.darker();
    public static final Color COMPILATION_ERROR = new Color( 255, 127, 39 );
    public static final Color RUNTIME_ERROR = new Color( 163, 73, 164 );
    public static final Color TIMEOUT_ERROR = new Color( 91, 0, 183 );
    public static final Color FILE_NOT_FOUND_ERROR = new Color( 120, 120, 120 );
    public static final Color ERROR = Color.ORANGE.darker();
    
    public static final Color VERIFICATION_ERROR_F = Color.RED;
    public static final Color VERIFICATION_ERROR_B = new Color( 50, 0, 0 );
    
}
