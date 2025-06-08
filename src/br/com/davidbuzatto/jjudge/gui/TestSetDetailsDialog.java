package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.testsets.Test;
import br.com.davidbuzatto.jjudge.testsets.TestCase;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.utils.Colors;
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
    
    private boolean useLightTheme;
    
    /**
     * Creates new form ResultDialog
     */
    public TestSetDetailsDialog( JFrame parent, boolean modal, TestSet testSet, Color backgroundColor, boolean useLightTheme ) {
        super( parent, modal );
        initComponents();
        this.backgroundColor = backgroundColor;
        this.useLightTheme = useLightTheme;
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
                getResultTextColor(), false );
        Utils.addFormattedText( 
                textPaneDetails, 
                testSet.getDescription(),
                getProcessingMessageColor(), false );
        
        Utils.addFormattedText( 
                textPaneDetails, 
                bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.programmingLanguage" ),
                getResultTextColor(), false );
        Utils.addFormattedText( 
                textPaneDetails, 
                testSet.getProgrammingLanguage().toString(),
                getProcessingMessageColor(), false );
        
        Utils.addFormattedText( 
                textPaneDetails, 
                bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.tests" ),
                getResultTextColor(), false );
        
        for ( Test t : testSet.getTests() ) {    
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.name" ),
                    getResultTextColor(), false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    t.getPresentationName(),
                    getProcessingMessageColor(), false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.filename" ),
                    getResultTextColor(), false );
            Utils.addFormattedText( 
                    textPaneDetails, 
                    processFilename( t, testSet ),
                    getProcessingMessageColor(), false );
            
            Utils.addFormattedText( 
                    textPaneDetails, 
                    bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.testCases" ),
                    getResultTextColor(), false );
            
            int i = 1;
            for ( TestCase tc : t.getTestCases() ) {
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        String.format( bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.testCase" ), i++ ),
                        getResultTextColor(), false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.input" ),
                        getResultTextColor(), false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        Utils.identText( tc.getInput().isEmpty() ? bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.empty" ) : tc.getInput(), 5 ),
                        getResultTextColor(), false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "\n|   |   |   |\n",
                        getResultTextColor(), false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.output" ),
                        getResultTextColor(), false );
                
                if ( tc.getOutput().isEmpty() ) {
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            Utils.identText( bundle.getString( "TestSetDetailsDialog.processDetailsTestSet.empty" ), 5 ),
                            getResultTextColor(), false );
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            "\n",
                            getResultTextColor(), false );
                } else {
                    Utils.addFormattedText( 
                            textPaneDetails, 
                            Utils.identText( tc.getOutput(), 5 ),
                            getResultTextColor(), true );
                }                
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |   |   |\n",
                        getResultTextColor(), false );
                
                Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |   |\n",
                        getResultTextColor(), false );
                
            }
            
            Utils.addFormattedText( 
                        textPaneDetails, 
                        "|   |\n",
                        getResultTextColor(), false );
            
            Utils.addFormattedText( 
                        textPaneDetails, 
                        "|\n",
                        getResultTextColor(), false );
            
        }
        
    }
    
    private String processFilename( Test test, TestSet testSet ) {
        
        String filename = test.getName();
        String ext = testSet.getProgrammingLanguage().extension;
        
        if ( filename.contains( "$OR$" ) ) {
            
            String f = "";
            boolean first = true;
            
            for ( String s : filename.split( "[$]OR[$]" ) ) {
                if ( !first ) {
                    f += " ou ";
                }
                f += s + "." + ext;
                first = false;
            }
            
            return f;
            
        } else {
            return filename + "." + ext;
        }
        
    }
    
    private Color getResultTextColor() {
        if ( useLightTheme ) {
            return Colors.RESULT_TEXT_LIGHT;
        }
        return Colors.RESULT_TEXT_DARK;
    }
    
    private Color getProcessingMessageColor() {
        if ( useLightTheme ) {
            return Colors.PROCESSING_MESSAGE_LIGHT;
        }
        return Colors.PROCESSING_MESSAGE_DARK;
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
