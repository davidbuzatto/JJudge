package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.plagiarism.PlagiarismTestResult;
import br.com.davidbuzatto.jjudge.plagiarism.SimilarityResult;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class PlagiarismDetectorResultPanel extends JPanel {
    
    private static final int RES_WIDTH = 25;
    private static final Font DEFAULT_FONT = new Font( Font.SANS_SERIF, Font.BOLD, 12 );
    
    private List<PlagiarismTestResult> results;
    private double threshold;
    private boolean showStudentName;
    private JScrollPane spContainer;
    
    private Color backgroundColor = Color.WHITE;
    
    public PlagiarismDetectorResultPanel( List<PlagiarismTestResult> results, JScrollPane spContainer ) {
        this.results = results;
        this.threshold = 0;
        this.showStudentName = true;
        this.spContainer = spContainer;
    }

    @Override
    protected void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint( 
            RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2d.setColor( backgroundColor );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );

        g2d.setColor( Color.BLACK );
        g2d.setFont( DEFAULT_FONT );

        int xStart = 10;
        int yStart = 30;
        int maxWidth = 0;

        for ( PlagiarismTestResult r : results ) {
            
            if ( shouldShowPlagiarismTestResult( r ) ) {

                String studentLabel = String.format( 
                    "%s x %s", 
                    showStudentName ? r.getStudent1().getName() : r.getStudent1().getCode(),
                    showStudentName ? r.getStudent2().getName() : r.getStudent2().getCode()
                );
                
                g2d.drawString( studentLabel, xStart, yStart );
                
                int labelWidth = g2d.getFontMetrics().stringWidth( studentLabel );
                int resultsWidth = 0;
                int currentWidth = 0;

                List<SimilarityResult> srs = r.getSimilarityResults();
                int maxFileNameWidth = 0;
                int k = 0;

                for ( int i = 0; i < srs.size(); i++ ) {

                    SimilarityResult sr = srs.get( i );

                    if ( sr.getSimilarity() >= threshold ) {

                        Color c = Utils.lerp( Color.GREEN, Color.RED, sr.getSimilarity() );
                        g2d.setColor( c );
                        g2d.fillRect( xStart + RES_WIDTH * k, yStart + 5, RES_WIDTH, RES_WIDTH );
                        g2d.setColor( Color.BLACK );
                        g2d.drawRect( xStart + RES_WIDTH * k, yStart + 5, RES_WIDTH, RES_WIDTH );

                        Graphics2D g2dt = (Graphics2D) g2d.create();
                        g2dt.translate( xStart, yStart + 5 );
                        g2dt.rotate( Math.toRadians( 90 ) );
                        g2dt.drawString( sr.getFile1().getName(), RES_WIDTH + 5, -8 - ( RES_WIDTH * k ) );
                        g2dt.dispose();

                        int fileNameWidth = g2d.getFontMetrics().stringWidth( sr.getFile1().getName() );
                        if ( maxFileNameWidth < fileNameWidth ) {
                            maxFileNameWidth = fileNameWidth;
                        }
                        
                        k++;

                    }

                }

                resultsWidth = xStart + RES_WIDTH * k;
                currentWidth = Math.max( labelWidth, resultsWidth );
                if ( maxWidth < currentWidth ) {
                    maxWidth = currentWidth;
                }
                
                yStart += RES_WIDTH + 10 + maxFileNameWidth + 50;

            }

        }
        
        setPreferredSize( new Dimension( maxWidth + 10, yStart + 10 ) );
        spContainer.updateUI();
        
        //g2d.drawRect( 0, 0, maxWidth + 10, yStart );

        g2d.dispose();
            
    }
    
    private boolean shouldShowPlagiarismTestResult( PlagiarismTestResult r ) {
        for ( SimilarityResult sr : r.getSimilarityResults() ) {
            if ( sr.getSimilarity() >= threshold ) {
                return true;
            }
        }
        return false;
    }

    public void setThreshold( double threshold ) {
        this.threshold = threshold;
    }

    public void setShowStudentName( boolean showStudentName ) {
        this.showStudentName = showStudentName;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor( Color backgroundColor ) {
        this.backgroundColor = backgroundColor;
    }
    
}
