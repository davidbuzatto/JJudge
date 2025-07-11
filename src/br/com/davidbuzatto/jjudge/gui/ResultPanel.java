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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class ResultPanel extends JPanel {

    private static BufferedImage refreshIcon;
    
    private MainWindow mainWindow;
    private List<TestSetResult> testSetResultList;
    private List<TestResultRectangle> resRects;
    
    private static final int RES_WIDTH = 25;
    private static final int RES_HEIGHT = 25;
    private static final Font DEFAULT_FONT = new Font( Font.SANS_SERIF, Font.BOLD, 12 );
    
    private boolean mouseOverAllowed;
    private Color backgroundColor = Color.WHITE;
    
    private boolean useLightTheme;
    
    public ResultPanel() {
        
        if ( refreshIcon == null ) {
            try {
                refreshIcon = ImageIO.read( getClass().getResource(
                        "/br/com/davidbuzatto/jjudge/gui/icons/arrow_refresh.png" ) );
            } catch ( IOException exc ) {
                Utils.showException( exc );
            }
        }
        
        this.resRects = new ArrayList<>();
        
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
                        if ( selectedResRect.testSetResult == null ) {
                            ResultDialog rd = new ResultDialog( null, true, selectedResRect.testResult, backgroundColor, useLightTheme );
                            rd.setVisible( true );
                        } else if ( !mainWindow.isRunning() ) {
                            mainWindow.runTest( selectedResRect.testSetResult );
                        }
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
                    boolean shouldShowOver = true;

                    for ( TestResultRectangle r : resRects ) {
                        if ( r.intersects( x, y ) ) {
                            found = true;
                            if ( r.testSetResult != null && mainWindow.isRunning() ) {
                                shouldShowOver = false;
                            }
                            break;
                        }
                    }
                    
                    if ( found && shouldShowOver ) {
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
        
        g2d.setFont( DEFAULT_FONT );
        g2d.setColor( backgroundColor );
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
                    int tw = fm.stringWidth( tr.getPresentationName() );
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
                
                if ( useLightTheme ) {
                    g2d.setColor( Colors.RESULT_TEXT_LIGHT );
                } else {
                    g2d.setColor( Colors.RESULT_TEXT_DARK );
                }
                
                if ( firstTestSetResult ) {
                    
                    int currLabel = 0;
                    
                    for ( TestResult tr : tsr.getTestResults() ) {
                        
                        Graphics2D g2dt = (Graphics2D) g2d.create();
                        int x = maxStudentWidth + RES_WIDTH;
                        
                        g2dt.translate( x, y );
                        g2dt.rotate( Math.toRadians( -90 ) );
                        g2dt.drawString( tr.getPresentationName(), 10, (int) ( RES_WIDTH / 1.5 ) + RES_WIDTH * currLabel );
                        
                        currLabel++;
                        g2dt.dispose();
                        
                    }
                    
                    firstTestSetResult = false;
                    
                }
                
                if ( tsr.getError() != null ) {
                    g2d.setColor( Color.RED );
                    g2d.drawString( tsr.getError(), 
                            maxStudentWidth + RES_WIDTH, 
                            y + (int) ( RES_HEIGHT / 1.3 ) );
                }
                
                g2d.drawString( tsr.getStudent().toString(), 
                        15 + maxStudentWidth - studentWidth, 
                        y + (int) ( RES_HEIGHT / 1.3 ) );
                
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
        
        FontMetrics fm = this.getFontMetrics( DEFAULT_FONT );
        
        int maxStudentWidth = 0;
        int maxTestNameWidth = 0;
        
        if ( testSetResultList != null ) {
            
            for ( TestSetResult tsr : testSetResultList ) {
                int sw = fm.stringWidth( tsr.getStudent().toString() );
                if ( sw > maxStudentWidth ) {
                    maxStudentWidth = sw;
                }
                for ( TestResult tr : tsr.getTestResults() ) {
                    int tw = fm.stringWidth( tr.getPresentationName() );
                    if ( tw > maxTestNameWidth ) {
                        maxTestNameWidth = tw;
                    }
                }
            }
            
            int currTestSetResult = 1;
            
            for ( TestSetResult tsr : testSetResultList ) {
                
                int currTestResult = 1;
                int y = currTestSetResult * RES_HEIGHT + maxTestNameWidth;
                int x;
                
                for ( TestResult tr : tsr.getTestResults() ) {
                            
                    x = maxStudentWidth + currTestResult * RES_WIDTH;
                    resRects.add( new TestResultRectangle( x, y, RES_WIDTH, RES_HEIGHT, tr ) );
                        
                    currTestResult++;
                    
                }
                
                if ( tsr.getPackageFile() != null ) {
                    x = maxStudentWidth + currTestResult * RES_WIDTH;
                    resRects.add( new TestResultRectangle( x, y, RES_WIDTH, RES_HEIGHT, tsr ) );
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
        TestSetResult testSetResult;
        TestResult testResult;
        
        boolean mouseOver;

        TestResultRectangle( int x, int y, int width, int height, TestResult testResult ) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.testResult = testResult;
        }
        
        TestResultRectangle( int x, int y, int width, int height, TestSetResult testSetResult ) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.testSetResult = testSetResult;
        }
        
        void draw( Graphics2D g2d ) {
            
            Color drawColor = Colors.REDO_COLOR;
            
            if ( testSetResult == null ) {
                drawColor = Utils.retrieveStateColor( testResult.getExecutionState() );
            }
            
            if ( mouseOver ) {
                drawColor = drawColor.darker();
            }
            
            g2d.setColor( drawColor );
            g2d.fillRect( x, y, width, height );
            
            g2d.setColor( Color.BLACK );
            g2d.drawRect( x, y, width, height );        
            
            if ( testSetResult != null ) {
                g2d.drawImage( 
                    refreshIcon, 
                    x + RES_WIDTH / 2 - refreshIcon.getWidth() / 2 + 1, 
                    y + RES_HEIGHT / 2 - refreshIcon.getHeight() / 2 + 1, 
                    null
                );
            }
            
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

    public void setBackgroundColor( Color backgroundColor ) {
        this.backgroundColor = backgroundColor;
    }

    public void setUseLightTheme( boolean useLightTheme ) {
        this.useLightTheme = useLightTheme;
    }

    public void setMainWindow( MainWindow mainWindow ) {
        this.mainWindow = mainWindow;
    }
    
}
