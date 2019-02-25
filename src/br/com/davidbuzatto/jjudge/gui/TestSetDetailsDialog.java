/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.testsets.Test;
import br.com.davidbuzatto.jjudge.testsets.TestCase;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author David
 */
public class TestSetDetailsDialog extends javax.swing.JDialog {
    
    /**
     * Creates new form ResultDialog
     */
    public TestSetDetailsDialog( JFrame parent, boolean modal, TestSet testSet ) {
        super( parent, modal );
        initComponents();
        customInit( testSet );
    }
    
    private void customInit( TestSet testSet ) {
        
        setIconImage( new ImageIcon( getClass().getResource(
                "/br/com/davidbuzatto/jjudge/gui/icons/report.png" ) ).getImage() );
        
        processDetailsTestSet( testSet );
        
    }

    private void processDetailsTestSet( TestSet testSet ) {
        
        StringBuilder sb = new StringBuilder();
        
        Utils.addFormattedText( 
                textPaneDetails, 
                "Description: ",
                Color.BLACK, false );
        Utils.addFormattedText( 
                textPaneDetails, 
                testSet.getDescription(),
                Color.BLUE, false );
        
        Utils.addFormattedText( 
                textPaneDetails, 
                "\nProgramming Language: ",
                Color.BLACK, false );
        Utils.addFormattedText( 
                textPaneDetails, 
                testSet.getProgrammingLanguage().toString(),
                Color.BLUE, false );
        
        Utils.addFormattedText( 
                textPaneDetails, 
                "\nTests:\n",
                Color.BLACK, false );
        
        for ( Test t : testSet.getTests() ) {    
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    "|-- Name: ",
                    Color.BLACK, false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    t.getName(),
                    Color.BLUE, false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    "\n|   |-- file name: ",
                    Color.BLACK, false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    t.getName(),
                    Color.BLUE, false );
            
            String ext = "";
            
            switch ( testSet.getProgrammingLanguage() ) {
                case C:
                    ext = ".c";
                    break;
                case CPP:
                    ext = ".cpp";
                    break;
                case JAVA:
                    ext = ".java";
                    break;
                case PYTHON:
                    ext = ".py";
                    break;
                default:
                    ext = ".<undefined>";
                    break;
            }
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    ext,
                    Color.BLUE, false );
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    "\n|   |-- test cases:\n",
                    Color.BLACK, false );
            
            int i = 1;
            for ( TestCase tc : t.getTestCases() ) {
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        String.format( "|   |   |-- test case %02d:\n", i++ ),
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |   |   |-- input:\n",
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        Utils.identText( tc.getInput().isEmpty() ? "<empty>" : tc.getInput(), 5 ),
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "\n|   |   |   |\n",
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |   |   |-- output:\n",
                        Color.BLACK, false );
                
                if ( tc.getOutput().isEmpty() ) {
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            Utils.identText( "<empty>", 5 ),
                            Color.BLACK, false );
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            "\n",
                            Color.BLACK, false );
                } else {
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            Utils.identText( tc.getOutput(), 5 ),
                            Color.BLACK, true );
                }
                
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |   |   |\n",
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |   |\n",
                        Color.BLACK, false );
                
            }
            
            Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |\n",
                        Color.BLACK, false );
            
            Utils.addFormattedText( 
                        textPaneDetails, 
                        "|\n",
                        Color.BLACK, false );
            
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneDetails = new javax.swing.JScrollPane();
        textPaneDetails = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Test Set Details");

        textPaneDetails.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scrollPaneDetails.setViewportView(textPaneDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPaneDetails;
    private javax.swing.JTextPane textPaneDetails;
    // End of variables declaration//GEN-END:variables
}
