/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import br.com.davidbuzatto.jjudge.testsets.TestCaseResult;
import br.com.davidbuzatto.jjudge.utils.Colors;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class ResultDialog extends javax.swing.JDialog {
    
    /**
     * Creates new form ResultDialog
     */
    public ResultDialog( JFrame parent, boolean modal, TestResult testResult ) {
        super( parent, modal );
        initComponents();
        customInit( testResult );
    }
    
    private void customInit( TestResult testResult ) {
        
        setIconImage( new ImageIcon( getClass().getResource(
                "/br/com/davidbuzatto/jjudge/gui/icons/report.png" ) ).getImage() );
        
        processResults( testResult );
        
    }
    
    private void processResults( TestResult testResult ) {
        
        Utils.addFormattedText( 
                    textPaneResult, 
                    String.format( "test %s: \n", testResult.getName() ), 
                    Color.BLUE );
        
        int testCase = 1;
        
        for ( TestCaseResult tcr : testResult.getTestCasesResult() ) {
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    String.format( "|-- test case %02d:\n", testCase++ ), 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |-- process test input:\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( tcr.getInput(), 3 ) + "\n", 
                    Color.BLACK );
                    
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |-- process test output:\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( tcr.getOutput(), 3 ) + "\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |-- process output:\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( tcr.getTestOutput(), 3 ) + "\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    Color.BLACK );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |-- test case state: ", 
                    Color.BLACK );
            
            Color color = Color.BLACK;
            switch ( tcr.getExecutionState() ) {
                case PASSED:
                    color = Colors.PASSED;
                    break;
                case NOT_PASSED:
                    color = Colors.NOT_PASSED;
                    break;
                default:
                    color = Colors.ERROR;
                    break;
            }
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    tcr.getExecutionState().toString(), 
                    color );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "\n|\n", 
                    Color.BLACK );
            
        }
        
        Utils.addFormattedText( 
                textPaneResult, 
                "|-- test state: ", 
                Color.BLACK );
        
        Color color = Color.BLACK;
            
        switch ( testResult.getExecutionState() ) {
            case APPROVED:
                color = Colors.APPROVED;
                break;
            case REPROVED:
                color = Colors.REPROVED;
                break;
            default:
                color = Colors.ERROR;
                break;
        }
            
        Utils.addFormattedText( 
                textPaneResult, 
                testResult.getExecutionState().toString(), 
                color );
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollResult = new javax.swing.JScrollPane();
        textPaneResult = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Test Results");

        textPaneResult.setEditable(false);
        textPaneResult.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scrollResult.setViewportView(textPaneResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollResult, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollResult, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollResult;
    private javax.swing.JTextPane textPaneResult;
    // End of variables declaration//GEN-END:variables
}
