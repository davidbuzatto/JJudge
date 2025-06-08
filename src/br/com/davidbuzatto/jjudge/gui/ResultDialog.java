package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.processor.ExecutionState;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestCaseResult;
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
public class ResultDialog extends javax.swing.JDialog {
    
    private ResourceBundle bundle = Utils.bundle;
    private Color backgroundColor = Color.WHITE;
    private boolean useLightTheme;
    
    /**
     * Creates new form ResultDialog
     */
    public ResultDialog( JFrame parent, boolean modal, TestResult testResult, Color backgroundColor, boolean useLightTheme ) {
        super( parent, modal );
        initComponents();
        this.backgroundColor = backgroundColor;
        this.useLightTheme = useLightTheme;
        textPaneResult.setBackground( backgroundColor );
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
                    String.format( bundle.getString( "ResultDialog.processResults.test" ), testResult.getPresentationName() ), 
                    useLightTheme ? Colors.PROCESSING_MESSAGE_LIGHT : Colors.PROCESSING_MESSAGE_DARK, false );
        
        int testCase = 1;
        
        for ( TestCaseResult tcr : testResult.getTestCasesResult() ) {
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    String.format( bundle.getString( "ResultDialog.processResults.testCase" ), testCase++ ), 
                    getResultTextColor(), false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.processTestInput" ), 
                    getResultTextColor(), false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( tcr.getInput().isEmpty() ? bundle.getString( "ResultDialog.processResults.empty" ) : tcr.getInput(), 3 ) + "\n", 
                    getResultTextColor(), false );
                    
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    getResultTextColor(), false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.processTestOutput" ), 
                    getResultTextColor(), false );
            
            if ( tcr.getOutput().isEmpty() ) {
                Utils.addFormattedText( 
                        textPaneResult, 
                        Utils.identText( bundle.getString( "ResultDialog.processResults.empty" ), 3 ) + "\n",
                        getResultTextColor(), false );
            } else {
                Utils.addFormattedText( 
                        textPaneResult, 
                        Utils.identText( tcr.getOutput(), 3 ),
                        getResultTextColor(), true );
            }
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    getResultTextColor(), false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.processOutput" ), 
                    getResultTextColor(), false );
            
            if ( tcr.getTestOutput().isEmpty() ) {
                Utils.addFormattedText( 
                        textPaneResult, 
                        Utils.identText( bundle.getString( "ResultDialog.processResults.empty" ), 3 ) + "\n",
                        getResultTextColor(), false );
            } else {
                if ( tcr.getExecutionState() == ExecutionState.RUNTIME_ERROR || 
                        tcr.getExecutionState() == ExecutionState.TIMEOUT_ERROR ) {
                    Utils.addFormattedText( 
                            textPaneResult, 
                            Utils.identText( tcr.getTestOutput(), 3 ) + "\n",
                            getResultTextColor(), false );
                } else {
                    Utils.addFormattedText( 
                            textPaneResult, 
                            Utils.identText( tcr.getTestOutput(), 3 ),
                            getResultTextColor(), true );
                }
            }
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "|   |\n", 
                    getResultTextColor(), false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.testCaseState" ), 
                    getResultTextColor(), false );
            
            Color color = Utils.retrieveStateColor( tcr.getExecutionState() );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.getExecutionStateIntString( tcr.getExecutionState() ), 
                    color, false );
            
            Utils.addFormattedText( 
                    textPaneResult, 
                    "\n|\n", 
                    getResultTextColor(), false );
            
        }
        
        if ( testResult.getExecutionState() == ExecutionState.COMPILATION_ERROR ) {

            Utils.addFormattedText( 
                    textPaneResult, 
                    "|-- ", 
                    getResultTextColor(), false );

            Utils.addFormattedText( 
                    textPaneResult, 
                    bundle.getString( "ResultDialog.processResults.compilationError" ), 
                    Colors.COMPILATION_ERROR.darker(), false );

            Utils.addFormattedText( 
                    textPaneResult, 
                    Utils.identText( testResult.getErrorMessage(), 2 ) + "\n", 
                    getResultTextColor(), false );

        }
        
        Utils.addFormattedText( 
                textPaneResult, 
                bundle.getString( "ResultDialog.processResults.testState" ), 
                getResultTextColor(), false );
        
        Color color = Utils.retrieveStateColor( testResult.getExecutionState() );
            
        Utils.addFormattedText( 
                textPaneResult, 
                Utils.getExecutionStateIntString( testResult.getExecutionState() ), 
                color, false );
        
    }

    private Color getResultTextColor() {
        if ( useLightTheme ) {
            return Colors.RESULT_TEXT_LIGHT;
        }
        return Colors.RESULT_TEXT_DARK;
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
