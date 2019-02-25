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
    public static final Color ERROR = Color.ORANGE.darker();
    
    public static final Color VERIFICATION_ERROR_F = Color.RED;
    public static final Color VERIFICATION_ERROR_B = new Color( 50, 0, 0 );
    
}
