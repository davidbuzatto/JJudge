/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge;

import br.com.davidbuzatto.jjudge.gui.MainWindow;
import br.com.davidbuzatto.jjudge.utils.Utils;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;

/**
 *
 * @author David
 */
public class JJudge {

    private static int secondsToTimeout = 5;
    
    public static void main( String args[] ) {
        
        if ( args.length == 2 ) {
            if ( args[0].equals( "-stt" ) ) {
                try {
                    secondsToTimeout = Integer.parseInt( args[1] );
                } catch ( NumberFormatException exc ) {
                }
            }
        }
        
        try {
            UIManager.setLookAndFeel( new FlatIntelliJLaf() );
        } catch ( Exception exc ) {
        }
        
        Utils.updateSplashScreen( 6000 );

        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                new MainWindow( secondsToTimeout ).setVisible( true );
            }
        });
        
    }
    
}
