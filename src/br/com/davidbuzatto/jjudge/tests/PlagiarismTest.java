package br.com.davidbuzatto.jjudge.tests;

import br.com.davidbuzatto.jjudge.plagiarism.PlagiarismTestResult;
import br.com.davidbuzatto.jjudge.plagiarism.PlagiarismUtils;
import br.com.davidbuzatto.jjudge.plagiarism.SimilarityResult;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class PlagiarismTest {
    
    public static void main( String[] args ) throws IOException {
        
        /*File[] jjds = {
            new File( "testPlagiarism/student1.jjd" ),
            new File( "testPlagiarism/student2.jjd" ),
            new File( "testPlagiarism/student3.jjd" ),
            new File( "testPlagiarism/student4.jjd" )
        };
        List<PlagiarismTestResult> results = PlagiarismUtils.plagiarismTest( 
            Arrays.asList( jjds ) );*/
        
        List<File> jjds = new ArrayList<>();
        for ( int i = 0; i <= 44; i++ ) {
            File f = new File( String.format( "testPlagiarism/%d.jjd", i ) );
            if ( f.exists() ) {
                jjds.add( f );
            }
        }
        List<PlagiarismTestResult> results = PlagiarismUtils.runPlagiarismDetector( jjds );
        
        /*for ( PlagiarismTestResult r : results ) {
            System.out.println( r );
        }*/
        
        showFrame( results );
        
    }
    
    private static void showFrame( List<PlagiarismTestResult> results ) {
        
        JFrame frame = new JFrame( "Plagiarism Test ;)" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        DrawPanel drawPanel = new DrawPanel( results, 0 );
        frame.add( drawPanel, BorderLayout.CENTER );
        
        JPanel northContainer = new JPanel();
        frame.add( northContainer, BorderLayout.NORTH );
        
        JLabel lblThreshold = new JLabel( "Similarity threshold: 0.00%" );
        JSlider thresholdSlider = new JSlider( 0, 1000, 0 );
        thresholdSlider.addChangeListener( new ChangeListener(){
            @Override
            public void stateChanged( ChangeEvent e ) {
                JSlider slider = (JSlider) e.getSource();
                drawPanel.setThreshold( slider.getValue() / 1000.0 );
                lblThreshold.setText( String.format( "Similarity threshold: %.2f%%", slider.getValue() / 1000.0 * 100 ));
                drawPanel.repaint();
            }
        });
        
        northContainer.add( thresholdSlider );
        northContainer.add( lblThreshold );
        
        frame.setSize( 1000, 1000 );
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );
        
    }
    
    private static class DrawPanel extends JPanel {
        
        List<PlagiarismTestResult> results;
        double threshold;
        
        DrawPanel( List<PlagiarismTestResult> results, double threshold ) {
            this.results = results;
            this.threshold = threshold;
        }

        @Override
        protected void paintComponent( Graphics g ) {
            
            super.paintComponent( g );
            
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint( 
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            
            g2d.setColor( Color.WHITE );
            g2d.fillRect( 0, 0, getWidth(), getHeight() );
            
            g2d.setColor( Color.BLACK );
            g2d.setFont( new Font( Font.MONOSPACED, Font.BOLD, 20 ) );
            
            int xStart = 10;
            int yStart = 30;
            int resultWidth = 30;
                
            for ( PlagiarismTestResult r : results ) {
                
                if ( shouldShowPlagiarismTestResult( r ) ) {
                    
                    g2d.drawString( 
                        String.format( 
                            "%s x %s", 
                            /*r.getStudent1().getName(),
                            r.getStudent2().getName()*/
                            r.getStudent1().getCode(),
                            r.getStudent2().getCode()
                        ),
                        xStart,
                        yStart
                    );

                    List<SimilarityResult> srs = r.getSimilarityResults();
                    int maxFileNameWidth = 0;
                    int k = 0;

                    for ( int i = 0; i < srs.size(); i++ ) {

                        SimilarityResult sr = srs.get( i );

                        if ( sr.getSimilarity() >= threshold ) {

                            Color c = Utils.lerp( Color.GREEN, Color.RED, sr.getSimilarity() );
                            g2d.setColor( c );
                            g2d.fillRect( xStart + resultWidth * k, yStart + 5, resultWidth, resultWidth );
                            g2d.setColor( Color.BLACK );
                            g2d.drawRect( xStart + resultWidth * k, yStart + 5, resultWidth, resultWidth );

                            Graphics2D g2dt = (Graphics2D) g2d.create();
                            g2dt.translate( xStart, yStart + 5 );
                            g2dt.rotate( Math.toRadians( 90 ) );
                            g2dt.drawString( sr.getFile1().getName(), resultWidth + 5, -10 - ( resultWidth * k ) );
                            g2dt.dispose();

                            int fileNameWidth = g2d.getFontMetrics().stringWidth( sr.getFile1().getName() );
                            if ( maxFileNameWidth < fileNameWidth ) {
                                maxFileNameWidth = fileNameWidth;
                            }

                            k++;

                        }

                    }

                    yStart += resultWidth + 10 + maxFileNameWidth + 50;
                    
                }
                
            }
            
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
        
    }   
    
}
