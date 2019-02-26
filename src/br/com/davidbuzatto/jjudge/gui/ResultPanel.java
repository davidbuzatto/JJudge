/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import br.com.davidbuzatto.jjudge.utils.Colors;
import br.com.davidbuzatto.jjudge.utils.Cursors;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class ResultPanel extends JPanel {

    private List<TestSetResult> testSetResultList;
    private List<TestResultRectangle> resRects;
    
    private final int RES_WIDTH = 25;
    private final int RES_HEIGHT = 25;
    
    private boolean mouseOverAllowed;
    
    public ResultPanel() {
        
        resRects = new ArrayList<>();
        
        addMouseListener( new MouseAdapter() {
            
            @Override
            public void mouseClicked( MouseEvent e ) {
                
                if ( mouseOverAllowed ) {
                    
                    int x = e.getX();
                    int y = e.getY();
                    TestResultRectangle selectedResRect = null;
                    
                    for ( TestResultRectangle r : resRects ) {
                        if ( r.intersects( x, y ) ) {
                            selectedResRect = r;
                            break;
                        }
                    }
                    
                    if ( selectedResRect != null ) {
                        ResultDialog rd = new ResultDialog( null, true, selectedResRect.testResult );
                        rd.setVisible( true );
                    }
                    
                }
                
            }
            
        });
        
        addMouseMotionListener( new MouseMotionAdapter() {
            
            @Override
            public void mouseMoved( MouseEvent e ) {
                
                boolean found = false;
                
                if ( mouseOverAllowed ) {
                    
                    int x = e.getX();
                    int y = e.getY();

                    for ( TestResultRectangle r : resRects ) {
                        if ( r.intersects( x, y ) ) {
                            found = true;
                        }
                    }
                    
                    if ( found ) {
                        setCursor( Cursors.HAND_CURSOR );
                    } else {
                        setCursor( Cursors.DEFAULT_CURSOR );
                    }
                    
                    repaint();
                    
                }
                
            }
            
        });
        
    }
    
    @Override
    protected void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint( 
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        draw( g2d );
        
        g2d.dispose();
        
    }
    
    private void draw( Graphics2D g2d ) {
        
        g2d.setColor( Color.WHITE );
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        
        FontMetrics fm = g2d.getFontMetrics();
        
        int maxStudentWidth = 0;
        int maxTestNameWidth = 0;
        
        if ( testSetResultList != null ) {
            
            for ( TestSetResult tsr : testSetResultList ) {
                int sw = fm.stringWidth( tsr.getStudent().toString() );
                if ( sw > maxStudentWidth ) {
                    maxStudentWidth = sw;
                }
                for ( TestResult tr : tsr.getTestResults() ) {
                    int tw = fm.stringWidth( tr.getName() );
                    if ( tw > maxTestNameWidth ) {
                        maxTestNameWidth = tw;
                    }
                }
            }
            
            int currTestSetResult = 1;
            boolean firstTestSetResult = true;
            
            for ( TestSetResult tsr : testSetResultList ) {
                
                int y = currTestSetResult * RES_HEIGHT + maxTestNameWidth;
                int studentWidth = fm.stringWidth( tsr.getStudent().toString() );
                
                g2d.setColor( Color.BLACK );
                g2d.drawString( tsr.getStudent().toString(), 
                        15 + maxStudentWidth - studentWidth, 
                        y + RES_HEIGHT - 7 );
                
                if ( firstTestSetResult ) {
                    
                    int currLabel = 0;
                    
                    for ( TestResult tr : tsr.getTestResults() ) {
                        
                        Graphics2D g2dt = (Graphics2D) g2d.create();
                        int x = maxStudentWidth + RES_WIDTH;
                        
                        g2dt.translate( x, y );
                        g2dt.rotate( Math.toRadians( -90 ) );
                        g2dt.drawString( tr.getName(), 10, 14 + RES_WIDTH * currLabel );
                        
                        currLabel++;
                        g2dt.dispose();
                        
                    }
                    
                    firstTestSetResult = false;
                    
                }
                
                currTestSetResult++;
                
            }
            
            for ( TestResultRectangle r : resRects ) {
                r.draw( g2d );
            }
            
        }
        
    }
    
    public void setTestSetResultList( List<TestSetResult> testSetResultList ) {
        this.testSetResultList = testSetResultList;
    }
    
    public void updateSize() {
        
        int maxX = 0;
        int maxY = 0;

        for ( TestResultRectangle r : resRects ) {

            if ( maxX < r.x + r.width ) {
                maxX = r.x + r.width;
            }

            if ( maxY < r.y + r.height ) {
                maxY = r.y + r.height;
            }
            
        }

        if ( maxX <= getParent().getWidth() ) {
            maxX = getParent().getWidth();
        }

        if ( maxY <= getParent().getHeight()) {
            maxY = getParent().getHeight();
        }

        maxX += 20;
        maxY += 20;
        
        Dimension d = new Dimension( maxX, maxY );
        setPreferredSize( d );
        
    }
    
    public void generateRects() {
        
        resRects.clear();
        
        Font f = new Font( "sansserif", Font.PLAIN, 12 );
        FontMetrics fm = this.getFontMetrics( f );
        
        int maxStudentWidth = 0;
        int maxTestNameWidth = 0;
        
        if ( testSetResultList != null ) {
            
            for ( TestSetResult tsr : testSetResultList ) {
                int sw = fm.stringWidth( tsr.getStudent().toString() );
                if ( sw > maxStudentWidth ) {
                    maxStudentWidth = sw;
                }
                for ( TestResult tr : tsr.getTestResults() ) {
                    int tw = fm.stringWidth( tr.getName() );
                    if ( tw > maxTestNameWidth ) {
                        maxTestNameWidth = tw;
                    }
                }
            }
            
            int currTestSetResult = 1;
            
            for ( TestSetResult tsr : testSetResultList ) {
                
                int currTestResult = 1;
                int y = currTestSetResult * RES_HEIGHT + maxTestNameWidth;
                
                for ( TestResult tr : tsr.getTestResults() ) {
                    
                    int x = maxStudentWidth + currTestResult * RES_WIDTH;
                    resRects.add( new TestResultRectangle( x, y, RES_WIDTH, RES_HEIGHT, tr ) );
                        
                    currTestResult++;
                    
                }
                
                currTestSetResult++;
                
            }
            
        }
        
    }

    public void setMouseOverAllowed( boolean mouseOverAllowed ) {
        this.mouseOverAllowed = mouseOverAllowed;
    }
    
    private class TestResultRectangle {
        
        int x;
        int y;
        int width;
        int height;
        Color color;
        TestResult testResult;
        
        boolean mouseOver;

        public TestResultRectangle( int x, int y, int width, int height, TestResult testResult ) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.testResult = testResult;
        }
        
        void draw( Graphics2D g2d ) {
            
            Color drawColor = Utils.retrieveStateColor( testResult.getExecutionState() );
            
            if ( mouseOver ) {
                drawColor = drawColor.darker();
            }
            
            g2d.setColor( drawColor );
            g2d.fillRect( x, y, width, height );
            
            g2d.setColor( Color.BLACK );
            g2d.drawRect( x, y, width, height );        
            
        }
        
        boolean intersects( int x, int y ) {
            
            if ( x > this.x && x < this.x + width && y > this.y && y < this.y + height ) {
                mouseOver = true;
                return true;
            }
            
            mouseOver = false;
            return false;
            
        }
        
    }
    
}
