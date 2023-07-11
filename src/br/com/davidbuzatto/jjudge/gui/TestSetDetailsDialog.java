package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.testsets.Test;
import br.com.davidbuzatto.jjudge.testsets.TestCase;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestSetDetailsDialog extends javax.swing.JDialog {
    
    private ResourceBundle bundle = Utils.bundle;
    private Color backgroundColor = Color.WHITE;
    
    /**
     * Creates new form ResultDialog
     */
    public TestSetDetailsDialog( JFrame parent, boolean modal, TestSet testSet, Color backgroundColor ) {
        super( parent, modal );
        initComponents();
        this.backgroundColor = backgroundColor;
        textPaneDetails.setBackground( backgroundColor );
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
                bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.description" ),
                Color.BLACK, false );
        Utils.addFormattedText( 
                textPaneDetails, 
                testSet.getDescription(),
                Color.BLUE, false );
        
        Utils.addFormattedText( 
                textPaneDetails, 
                bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.programmingLanguage" ),
                Color.BLACK, false );
        Utils.addFormattedText( 
                textPaneDetails, 
                testSet.getProgrammingLanguage().toString(),
                Color.BLUE, false );
        
        Utils.addFormattedText( 
                textPaneDetails, 
                bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.tests" ),
                Color.BLACK, false );
        
        for ( Test t : testSet.getTests() ) {    
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.name" ),
                    Color.BLACK, false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    t.getName(),
                    Color.BLUE, false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.fileName" ),
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
                    ext = bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.undefined" );
                    break;
            }
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    ext,
                    Color.BLUE, false );
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.testCases" ),
                    Color.BLACK, false );
            
            int i = 1;
            for ( TestCase tc : t.getTestCases() ) {
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        String.format( bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.testCase" ), i++ ),
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.input" ),
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        Utils.identText( tc.getInput().isEmpty() ? bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.empty" ) : tc.getInput(), 5 ),
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "\n|   |   |   |\n",
                        Color.BLACK, false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.output" ),
                        Color.BLACK, false );
                
                if ( tc.getOutput().isEmpty() ) {
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            Utils.identText( bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.empty" ), 5 ),
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
        panelDetails = new javax.swing.JPanel();
        textPaneDetails = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle"); // NOI18N
        setTitle(bundle.getString("TestSetDetailsDialog.title")); // NOI18N

        panelDetails.setLayout(new java.awt.BorderLayout());

        textPaneDetails.setEditable(false);
        textPaneDetails.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        panelDetails.add(textPaneDetails, java.awt.BorderLayout.CENTER);

        scrollPaneDetails.setViewportView(panelDetails);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelDetails;
    private javax.swing.JScrollPane scrollPaneDetails;
    private javax.swing.JTextPane textPaneDetails;
    // End of variables declaration//GEN-END:variables
}
