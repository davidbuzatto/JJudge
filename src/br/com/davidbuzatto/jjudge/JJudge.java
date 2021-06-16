/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge;

import com.formdev.flatlaf.FlatDarkLaf;
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
        
        /*try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch ( Exception exc ) {
        }*/
        
        try {
            for ( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if ( "Nimbus".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        } catch ( ClassNotFoundException ex ) {
            java.util.logging.Logger.getLogger( br.com.davidbuzatto.jjudge.gui.MainWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( InstantiationException ex ) {
            java.util.logging.Logger.getLogger( br.com.davidbuzatto.jjudge.gui.MainWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( IllegalAccessException ex ) {
            java.util.logging.Logger.getLogger( br.com.davidbuzatto.jjudge.gui.MainWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        } catch ( javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( br.com.davidbuzatto.jjudge.gui.MainWindow.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }

        java.awt.EventQueue.invokeLater( new Runnable() {
            public void run() {
                new br.com.davidbuzatto.jjudge.gui.MainWindow( secondsToTimeout ).setVisible( true );
            }
        });
        
    }
    
}
