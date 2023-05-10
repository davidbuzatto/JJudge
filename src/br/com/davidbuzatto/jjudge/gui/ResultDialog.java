/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.processor.ExecutionState;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestCaseResult;
import br.com.davidbuzatto.jjudge.utils.Colors;
import br.com.davidbuzatto.jjudge.utils.Utils;
import static br.com.davidbuzatto.jjudge.utils.Utils.PREF_THEME;
import static br.com.davidbuzatto.jjudge.utils.Utils.getPref;
import java.awt.Color;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class ResultDialog extends javax.swing.JDialog {
    
    private ResourceBundle bundle = Utils.bundle;
    
    private Color textColor = Color.BLACK;
    private Color blueTextColor = Color.BLUE;
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
        // textColor e blueTextColor
        switch (getPref(PREF_THEME)) {
            case "light":
                textColor = Color.BLACK;
                blueTextColor =  Color.BLUE;
                break;
            case "dark":
                textColor = Color.WHITE;
                blueTextColor = Color.cyan;
                break;
        }
        
        Utils.addFormattedText( 
                    textPaneResult, 
                    String.format( bundle.getString( "ResultDialog.processResults.test" ), testResult.getName() ), 
                    blueTextColor, false );
        
        int testCase = 1;
        
        for ( TestCaseResult tcr : testResult.getTestCasesResult() ) {
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    String.format( bundle.getString( "ResultDialog.processResults.testCase" ), testCase++ ), 
                    textColor, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.processTestInput" ), 
                    textColor, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( tcr.getInput().isEmpty() ? bundle.getString( "ResultDialog.processResults.empty" ) : tcr.getInput(), 3 ) + "\n", 
                    textColor, false );
                    
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    textColor, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.processTestOutput" ), 
                    textColor, false );
            
            if ( tcr.getOutput().isEmpty() ) {
                Utils.addFormattedText( 
                        textPaneResult, 
                        Utils.identText( bundle.getString( "ResultDialog.processResults.empty" ), 3 ) + "\n",
                        textColor, false );
            } else {
                Utils.addFormattedText( 
                        textPaneResult, 
                        Utils.identText( tcr.getOutput(), 3 ),
                        textColor, true );
            }
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    textColor, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.processOutput" ), 
                    textColor, false );
            
            if ( tcr.getTestOutput().isEmpty() ) {
                Utils.addFormattedText( 
                        textPaneResult, 
                        Utils.identText( bundle.getString( "ResultDialog.processResults.empty" ), 3 ) + "\n",
                        textColor, false );
            } else {
                if ( tcr.getExecutionState() == ExecutionState.RUNTIME_ERROR || 
                        tcr.getExecutionState() == ExecutionState.TIMEOUT_ERROR ) {
                    Utils.addFormattedText( 
                            textPaneResult, 
                            Utils.identText( tcr.getTestOutput(), 3 ) + "\n",
                            textColor, false );
                } else {
                    Utils.addFormattedText( 
                            textPaneResult, 
                            Utils.identText( tcr.getTestOutput(), 3 ),
                            textColor, true );
                }
            }
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    textColor, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.testCaseState" ), 
                    textColor, false );
            
            Color color = Utils.retrieveStateColor( tcr.getExecutionState() );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.getExecutionStateIntString( tcr.getExecutionState() ), 
                    color, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "\n|\n", 
                    textColor, false );
            
        }
        
        if ( testResult.getExecutionState() == ExecutionState.COMPILATION_ERROR ) {

            Utils.addFormattedText( 
                    textPaneResult, 
                    "|-- ", 
                    textColor, false );

            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.compilationError" ), 
                    Colors.COMPILATION_ERROR.darker(), false );

            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( testResult.getErrorMessage(), 2 ) + "\n", 
                    textColor, false );

        }
        
        Utils.addFormattedText( 
                textPaneResult, 
                bundle.getString( "ResultDialog.processResults.testState" ), 
                textColor, false );
        
        Color color = Utils.retrieveStateColor( testResult.getExecutionState() );
            
        Utils.addFormattedText( 
                textPaneResult, 
                Utils.getExecutionStateIntString( testResult.getExecutionState() ), 
                color, false );
        
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
        panelResult = new javax.swing.JPanel();
        textPaneResult = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle"); // NOI18N
        setTitle(bundle.getString("ResultDialog.title")); // NOI18N

        panelResult.setLayout(new java.awt.BorderLayout());

        textPaneResult.setEditable(false);
        textPaneResult.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        panelResult.add(textPaneResult, java.awt.BorderLayout.CENTER);

        scrollResult.setViewportView(panelResult);

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
    private javax.swing.JPanel panelResult;
    private javax.swing.JScrollPane scrollResult;
    private javax.swing.JTextPane textPaneResult;
    // End of variables declaration//GEN-END:variables
}
