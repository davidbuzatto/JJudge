package br.com.davidbuzatto.jjudge.gui;

import br.com.davidbuzatto.jjudge.plagiarism.PlagiarismTestResult;
import br.com.davidbuzatto.jjudge.plagiarism.PlagiarismUtils;
import br.com.davidbuzatto.jjudge.stylechecker.StyleCheckerUtils;
import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import br.com.davidbuzatto.jjudge.utils.NoGuiModeWrapper;
import br.com.davidbuzatto.jjudge.utils.Utils;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class MainWindow extends javax.swing.JFrame {
    
    private DefaultListModel<File> listPackagesModel;
    private DefaultListModel<TestSet> listTestSetsModel;
    private List<TestSet> testSets;
    private int secondsToTimeout;
    private boolean outputStreams;
    
    private ResourceBundle bundle = Utils.bundle;
    
    private Color textPaneProcessOutputBackgroundColor;
    private Color resultPanelBackgroundColor;
    private Color plagiarismPanelBackgroundColor;
    private Color testSetDetailsDialogBackgroundColor;
    
    private boolean running;
    private final Icon RUN_ICON;
    private final Icon STOP_ICON;
    
    private List<File> javaClasspathFiles;
    
    public MainWindow() {
        this( 5 );
    }
    
    public MainWindow( int secondsToTimeout ) {
        
        this.secondsToTimeout = secondsToTimeout;
        RUN_ICON = new ImageIcon( getClass().getResource(
                "/br/com/davidbuzatto/jjudge/gui/icons/accept.png" ) );
        STOP_ICON = new ImageIcon( getClass().getResource(
                "/br/com/davidbuzatto/jjudge/gui/icons/stop.png" ) );
        
        initComponents();
        customInit();
        
    }

    private void customInit() {
        
        outputStreams = false;
        
        setIconImage( new ImageIcon( getClass().getResource(
                "/br/com/davidbuzatto/jjudge/gui/icons/user_gray.png" ) ).getImage() );
        
        listPackagesModel = new DefaultListModel<>();
        listTestSetsModel = new DefaultListModel<>();
        
        listPackages.setModel( listPackagesModel );
        listTestSets.setModel( listTestSetsModel );
        
        lblStatus.setText( "" );
        
        javaClasspathFiles = Utils.processStoredFilesPath( 
                Utils.getPref( Utils.PREF_JAVA_CLASSPATH_DEPENDENCIES_PATHS ) );
        
        DefaultCaret caret = (DefaultCaret) textPaneProcessOutput.getCaret();
        caret.setUpdatePolicy( DefaultCaret.ALWAYS_UPDATE );
        
        setExtendedState( JFrame.MAXIMIZED_BOTH );
        resultPanel.setMouseOverAllowed( true );
        
        //prepareForJavaDebug();
        //prepareForCDebug();
        //prepareForGeneralDebug();
        //prepareForPlagiarismDetectorDebug();
        prepareForStyleCheckerDebug();
        
    }
    
    private void buildTestSetsModel() {
        listTestSetsModel.clear();
        for ( TestSet ts : testSets ) {
            listTestSetsModel.addElement( ts );
        }
    }
    
    private void buildTestPackage() {
        
        String name = JOptionPane.showInputDialog( 
                bundle.getString( 
                        "MainWindow.buildTestPackage.studentFullName" ) );
        String code;
        String packageName;
        
        if ( name != null ) {
            
            name = name.trim();
            
            code = JOptionPane.showInputDialog( 
                    bundle.getString( 
                            "MainWindow.buildTestPackage.studentCode" ) );
            
            if ( code != null ) {
                
                code = code.trim();
                
                packageName = JOptionPane.showInputDialog( 
                        bundle.getString( 
                                "MainWindow.buildTestPackage.packageName" ) );
                
                if ( packageName != null ) {
                    
                    packageName = packageName.trim();
                    
                    JFileChooser jfc = new JFileChooser( new File( Utils.getPref( Utils.PREF_BUILD_TEST_PACKAGE_PATH ) ) );
                    jfc.setDialogTitle( bundle.getString( "MainWindow.buildTestPackage.filesToInsert" ) );
                    jfc.setMultiSelectionEnabled( true );
                    jfc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
                    jfc.removeChoosableFileFilter( jfc.getFileFilter() );
                    jfc.setFileFilter( new FileNameExtensionFilter( 
                            bundle.getString( "MainWindow.buildTestPackage.fileTypes" ), 
                            "c", "cpp", "java", "py", "txt" ) );

                    jfc.showOpenDialog( this );
                    File[] files = jfc.getSelectedFiles();

                    if ( files != null && files.length > 0 ) {
                        
                        String absolutePath = files[0].getParentFile().getAbsolutePath();
                        Utils.setPref( Utils.PREF_BUILD_TEST_PACKAGE_PATH, absolutePath );
                        
                        Student s = new Student();
                        s.setName( name );
                        s.setCode( code );

                        try {
                            
                            File studentFile = new File( absolutePath + "/student.json" );
                            
                            Utils.saveStudent( s, studentFile );
                            
                            List<File> filesList = new ArrayList<>( Arrays.asList( files ) );
                            filesList.add( studentFile );
                            files = filesList.toArray( files );
                            
                            Utils.newZipFiles( files, new File( absolutePath + File.separator + packageName + ".jjd" )  );
                            
                            studentFile.delete();
                            
                            boolean hasDir = false;
                            for ( File f : filesList ) {
                                if ( f.isDirectory() ) {
                                    hasDir = true;
                                }
                            }
                            
                            if ( hasDir ) {
                                JOptionPane.showMessageDialog( 
                                    this, 
                                    bundle.getString( "MainWindow.buildTestPackage.warning" ), 
                                    bundle.getString( "MainWindow.buildTestPackage.warningTitle" ), 
                                    JOptionPane.WARNING_MESSAGE );
                            }
                            
                            JOptionPane.showMessageDialog( 
                                this,
                                bundle.getString( "MainWindow.buildTestPackage.success" ),
                                bundle.getString( "MainWindow.buildTestPackage.successTitle" ),
                                JOptionPane.INFORMATION_MESSAGE );
                            
                        } catch ( IOException exc )  {
                            Utils.showException( exc );
                        }

                    }
                
                }
        
            }
            
        }
        
    }
    
    private void addPackage() {
        
        JFileChooser jfc = new JFileChooser( new File( Utils.getPref( Utils.PREF_ADD_PACKAGE_PATH ) ) );
        jfc.setDialogTitle( bundle.getString( "MainWindow.btnAddPackageActionPerformed.packageOrSource" ) );
        jfc.setMultiSelectionEnabled( true );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( 
                bundle.getString( "MainWindow.btnAddPackageActionPerformed.fileTypes" ), 
                "jjd", "c", "cpp", "java", "py" ) );
        
        
        if ( jfc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
            
            File[] files = jfc.getSelectedFiles();
        
            if ( files != null && files.length > 0 ) {
                
                Utils.renameFilesToValidName( files );
                
                Utils.setPref( Utils.PREF_ADD_PACKAGE_PATH, files[0].getParentFile().getAbsolutePath() );
                
                Arrays.sort( files, ( File f1, File f2 ) -> {

                    String n1 = f1.getName();
                    String n2 = f2.getName();
                    String strCmp = ".";
                    boolean cmp;
                    
                    if ( n1.contains( "$" ) || n2.contains( "$" ) ) {
                        cmp = ( n1.startsWith( "Exercicio" ) || n1.startsWith( "Desafio" ) || n1.startsWith( "Projeto" ) ) && 
                              ( n2.startsWith( "Exercicio" ) || n2.startsWith( "Desafio" ) || n2.startsWith( "Projeto" ) ) && 
                                n1.contains( "$" ) && n2.contains( "$" );
                        strCmp = "$";
                    } else {
                        cmp = ( n1.startsWith( "ex" ) || n1.startsWith( "de" ) || n1.startsWith( "pr" ) ) && 
                              ( n2.startsWith( "ex" ) || n2.startsWith( "de" ) || n2.startsWith( "pr" ) ) && 
                                n1.contains( "." ) && n2.contains( "." );
                    }
                    
                    if ( cmp ) {
                        
                        String t1 = n1.substring( 0, n1.indexOf( strCmp ) );
                        String t2 = n2.substring( 0, n2.indexOf( strCmp ) );

                        String v1;
                        String v2;

                        try {
                            v1 = n1.substring( n1.indexOf( strCmp ) + 1, n1.lastIndexOf( "." ) );
                            v2 = n2.substring( n2.indexOf( strCmp ) + 1, n2.lastIndexOf( "." ) );
                        } catch ( StringIndexOutOfBoundsException exc ) {
                            return n1.compareTo( n2 );
                        }

                        try {

                            int num1 = Integer.parseInt( v1 );
                            int num2 = Integer.parseInt( v2 );

                            int compT = t1.compareTo( t2 );

                            if ( compT == 0 ) {
                                return num1 - num2;
                            } else {
                                return compT;
                            }

                        } catch ( NumberFormatException exc ) {
                            return n1.compareTo( n2 );
                        }
                        
                    } else {
                        return n1.compareTo( n2 );
                    }

                });

                for ( File f : files ) {
                    listPackagesModel.addElement( f );
                }
                
            }
        
        }
        
    }
    
    private void removePackage() {
        
        if ( listPackages.getSelectedValue() != null ) {
            
            if ( JOptionPane.showConfirmDialog( 
                    this, bundle.getString( "MainWindow.btnRemovePackageActionPerformed.confirmMessage" ),
                    bundle.getString( "MainWindow.btnRemovePackageActionPerformed.confirmTitle" ),
                    JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
                
                int[] indices = listPackages.getSelectedIndices();
                
                for ( int i = indices.length-1; i >= 0; i-- ) {
                    listPackagesModel.remove( indices[i] );
                }
                
            }
            
        }
        
    }
    
    private void loadTestSet() {
        
        JFileChooser jfc = new JFileChooser( new File( Utils.getPref( Utils.PREF_LOAD_TEST_SETS_PATH ) ) );
        jfc.setDialogTitle( bundle.getString( "MainWindow.loadTestSet.testSetPackage" ) );
        jfc.setMultiSelectionEnabled( false );
        jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
        jfc.removeChoosableFileFilter( jfc.getFileFilter() );
        jfc.setFileFilter( new FileNameExtensionFilter( bundle.getString( "MainWindow.loadTestSet.fileTypes" ), "json" ) );
        
        if ( jfc.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
            
            File file = jfc.getSelectedFile();

            if ( file != null ) {
                Utils.setPref( Utils.PREF_LOAD_TEST_SETS_PATH, file.getParentFile().getAbsolutePath() );
                testSets = Utils.loadTestSets( file );
                if ( testSets != null ) {
                    buildTestSetsModel();
                } else {
                    JOptionPane.showMessageDialog( 
                            this, 
                            bundle.getString( "MainWindow.loadTestSet.invalidTestSetPackage" ), 
                            bundle.getString( "MainWindow.errorTitle" ), JOptionPane.ERROR_MESSAGE );
                }
            }
            
        }
        
    }
    
    private void runTest() {
        
        final TestSet tSet;
        
        if ( listTestSetsModel.getSize() == 1 ) {
            tSet = listTestSetsModel.get( 0 );
        } else {
            tSet = listTestSets.getSelectedValue();
        }
        List<TestSetResult> tSetResList = new ArrayList<>();
        
        resultPanel.setTestSetResultList( tSetResList );
        resultPanel.generateRects();
        resultPanel.repaint();
        resultPanel.updateSize();
        scrollResults.updateUI();
        
        if ( tSet != null && !listPackagesModel.isEmpty() ) {
            
            new Thread( new Runnable() {
                
                @Override
                public void run() {
                    
                    running = true;
                    
                    btnRunTest.setText( bundle.getString( "MainWindow.btnRunTestStop.text" ) );
                    btnRunTest.setIcon( STOP_ICON );
                    
                    listPackages.setEnabled( false );
                    listTestSets.setEnabled( false );
                    btnBuildTestPackage.setEnabled( false );
                    btnAddPackage.setEnabled( false );
                    btnRemovePackage.setEnabled( false );
                    btnLoadTestSet.setEnabled( false );
                    menuItemBuildTestPackage.setEnabled( false );
                    menuItemAddPackage.setEnabled( false );
                    menuItemLoadTestSets.setEnabled( false );
                    menuItemRunTest.setEnabled( false );
                    menuItemExit.setEnabled( false );
                    menuItemTestSets.setEnabled( false );
                    menuItemDependencies.setEnabled( false );
                    menuItemTheme.setEnabled( false );
                    menuItemPlagiarismDetector.setEnabled( false );
                    menuItemStyleChecker.setEnabled( false );
                    menuItemHowTo.setEnabled( false );
                    menuItemAbout.setEnabled( false );
                    lblStatus.setText( bundle.getString( "MainWindow.btnRunTestActionPerformed.pleaseWait" ) );
                    textPaneProcessOutput.setText( "" );
                    
                    try {
                        
                        for ( int i = 0; i < listPackagesModel.size() && running; i++ ) {
                            
                            File file = listPackagesModel.get( i );
                            File destDir = new File( file.getAbsolutePath().replace( ".jjd", "-jjd-temp" ).trim() );
                            
                            Utils.addFormattedText( 
                                    textPaneProcessOutput, 
                                    String.format( bundle.getString( "MainWindow.btnRunTestActionPerformed.processing" ), file ),
                                    Color.BLUE, false );
                            
                            tSetResList.add( Utils.runTest( 
                                    file, 
                                    tSet, 
                                    outputStreams, 
                                    secondsToTimeout, 
                                    textPaneProcessOutput,
                                    javaClasspathFiles ) );

                            resultPanel.generateRects();
                            resultPanel.updateSize();
                            resultPanel.repaint();
                            scrollResults.updateUI();
                            
                            if ( file.getName().endsWith( ".jjd" ) ) {

                                Utils.addFormattedText( 
                                        textPaneProcessOutput, 
                                        String.format( bundle.getString( "MainWindow.btnRunTestActionPerformed.cleaning" ), file ),
                                        Color.BLACK, false );

                                FileUtils.deleteDirectory( destDir );
                                
                            }
                            
                        }
                        
                        if ( checkGenerateResultsSpreadsheet.isSelected() ) {
                            
                            File f = Utils.processResultsToExcel( tSetResList, tSet );
                            
                            if ( f != null && 
                                 JOptionPane.showConfirmDialog( null, 
                                     bundle.getString( "MainWindow.confirmOpenExcelFile" ), 
                                     bundle.getString( "MainWindow.confirm" ), 
                                     JOptionPane.YES_NO_OPTION ) == JOptionPane.OK_OPTION ) {
                                
                                Desktop.getDesktop().open( f );
                                
                            }
                            
                        }
                        
                    } catch ( IOException exc ) {
                        Utils.addFormattedText( 
                                textPaneProcessOutput, 
                                bundle.getString( "MainWindow.btnRunTestActionPerformed.errorCompileAndRun" ),
                                Color.RED, false );
                        Utils.showException( exc );
                    } catch ( InterruptedException exc ) {
                        Utils.showException( exc );
                    }
                    
                    Utils.addFormattedText( 
                            textPaneProcessOutput, 
                            bundle.getString( "MainWindow.btnRunTestActionPerformed.finished" ),
                            Color.BLACK, false );
                    
                    listPackages.setEnabled( true );
                    listTestSets.setEnabled( true );
                    btnBuildTestPackage.setEnabled( true );
                    btnAddPackage.setEnabled( true );
                    btnRemovePackage.setEnabled( true );
                    btnLoadTestSet.setEnabled( true );
                    menuItemBuildTestPackage.setEnabled( true );
                    menuItemAddPackage.setEnabled( true );
                    menuItemLoadTestSets.setEnabled( true);
                    menuItemRunTest.setEnabled( true );
                    menuItemExit.setEnabled( true );
                    menuItemTestSets.setEnabled( true );
                    menuItemDependencies.setEnabled( true );
                    menuItemTheme.setEnabled( true );
                    menuItemPlagiarismDetector.setEnabled( true );
                    menuItemStyleChecker.setEnabled( true );
                    menuItemHowTo.setEnabled( true );
                    menuItemAbout.setEnabled( true );
                    lblStatus.setText( bundle.getString( "MainWindow.btnRunTestActionPerformed.done" ) );
                    
                    btnRunTest.setText( bundle.getString( "MainWindow.btnRunTest.text" ) );
                    btnRunTest.setIcon( RUN_ICON );
                    btnRunTest.setEnabled( true );
                    
                    running = false;
                    
                }
                
            }).start();
            
        } else if ( tSet == null ) {
            JOptionPane.showMessageDialog( 
                    this, 
                    bundle.getString( "MainWindow.btnRunTestActionPerformed.errorSelectTestSet" ), 
                    bundle.getString( "MainWindow.errorTitle" ), JOptionPane.ERROR_MESSAGE );
        } else {
            JOptionPane.showMessageDialog( 
                    this, 
                    bundle.getString( "MainWindow.btnRunTestActionPerformed.errorTest" ), 
                    bundle.getString( "MainWindow.errorTitle" ), JOptionPane.ERROR_MESSAGE );
        }
        
    }
    
    private void runPlagiarismDetector() {
        
        final List<File> packagesToTest = new ArrayList<>();
        final JFrame thisFrame = this;
        
        if ( !listPackagesModel.isEmpty() ) {
            for ( int i = 0; i < listPackagesModel.getSize(); i++ ) {
                File f = listPackagesModel.get( i );
                if ( f.exists() && f.getName().endsWith( ".jjd" ) ) {
                    packagesToTest.add( listPackagesModel.get( i ) );
                }
            }
        }
        
        if ( packagesToTest.size() > 1 ) {
            
            new Thread( new Runnable() {
                
                @Override
                public void run() {
                    
                    listPackages.setEnabled( false );
                    listTestSets.setEnabled( false );
                    btnBuildTestPackage.setEnabled( false );
                    btnAddPackage.setEnabled( false );
                    btnRemovePackage.setEnabled( false );
                    checkGenerateResultsSpreadsheet.setEnabled( false );
                    btnLoadTestSet.setEnabled( false );
                    btnRunTest.setEnabled( false );
                    menuItemBuildTestPackage.setEnabled( false );
                    menuItemAddPackage.setEnabled( false );
                    menuItemLoadTestSets.setEnabled( false );
                    menuItemRunTest.setEnabled( false );
                    menuItemExit.setEnabled( false );
                    menuItemTestSets.setEnabled( false );
                    menuItemDependencies.setEnabled( false );
                    menuItemTheme.setEnabled( false );
                    menuItemPlagiarismDetector.setEnabled( false );
                    menuItemStyleChecker.setEnabled( false );
                    menuItemHowTo.setEnabled( false );
                    menuItemAbout.setEnabled( false );
                    lblStatus.setText( bundle.getString( "MainWindow.menuItemPlagiarismDetector.pleaseWait" ) );
                    
                    try {
                        List<PlagiarismTestResult> results = PlagiarismUtils.runPlagiarismDetector( packagesToTest );
                        PlagiarismDetectorResultFrame frame = new PlagiarismDetectorResultFrame( results, plagiarismPanelBackgroundColor );
                        frame.setVisible( true );
                    } catch ( IOException exc ) {
                        Utils.showException( exc );
                    }
                    
                    listPackages.setEnabled( true );
                    listTestSets.setEnabled( true );
                    btnBuildTestPackage.setEnabled( true );
                    btnAddPackage.setEnabled( true );
                    btnRemovePackage.setEnabled( true );
                    checkGenerateResultsSpreadsheet.setEnabled( true );
                    btnLoadTestSet.setEnabled( true );
                    btnRunTest.setEnabled( true );
                    menuItemBuildTestPackage.setEnabled( true );
                    menuItemAddPackage.setEnabled( true );
                    menuItemLoadTestSets.setEnabled( true);
                    menuItemRunTest.setEnabled( true );
                    menuItemExit.setEnabled( true );
                    menuItemTestSets.setEnabled( true );
                    menuItemDependencies.setEnabled( true );
                    menuItemTheme.setEnabled( true );
                    menuItemPlagiarismDetector.setEnabled( true );
                    menuItemStyleChecker.setEnabled( true );
                    menuItemHowTo.setEnabled( true );
                    menuItemAbout.setEnabled( true );
                    lblStatus.setText( bundle.getString( "MainWindow.menuItemPlagiarismDetector.done" ) );
                    
                }
                
            }).start();
            
        } else {
            JOptionPane.showMessageDialog( 
                    this, 
                    bundle.getString( "MainWindow.menuItemPlagiarismDetector.errorTest" ), 
                    bundle.getString( "MainWindow.errorTitle" ), JOptionPane.ERROR_MESSAGE );
        }
        
    }
    
    private void runStyleChecker() {
        
        final List<File> filesToTest = new ArrayList<>();
        final JFrame thisFrame = this;
        
        if ( !listPackagesModel.isEmpty() ) {
            for ( int i = 0; i < listPackagesModel.getSize(); i++ ) {
                File f = listPackagesModel.get( i );
                if ( f.exists() ) {
                    filesToTest.add( listPackagesModel.get( i ) );
                }
            }
        }
        
        if ( filesToTest.size() > 1 ) {
            
            new Thread( new Runnable() {
                
                @Override
                public void run() {
                    
                    listPackages.setEnabled( false );
                    listTestSets.setEnabled( false );
                    btnBuildTestPackage.setEnabled( false );
                    btnAddPackage.setEnabled( false );
                    btnRemovePackage.setEnabled( false );
                    checkGenerateResultsSpreadsheet.setEnabled( false );
                    btnLoadTestSet.setEnabled( false );
                    btnRunTest.setEnabled( false );
                    menuItemBuildTestPackage.setEnabled( false );
                    menuItemAddPackage.setEnabled( false );
                    menuItemLoadTestSets.setEnabled( false );
                    menuItemRunTest.setEnabled( false );
                    menuItemExit.setEnabled( false );
                    menuItemTestSets.setEnabled( false );
                    menuItemDependencies.setEnabled( false );
                    menuItemTheme.setEnabled( false );
                    menuItemPlagiarismDetector.setEnabled( false );
                    menuItemStyleChecker.setEnabled( false );
                    menuItemHowTo.setEnabled( false );
                    menuItemAbout.setEnabled( false );
                    lblStatus.setText( bundle.getString( "MainWindow.menuItemPlagiarismDetector.pleaseWait" ) );
                    
                    try {
                        StyleCheckerUtils.runStyleChecker( filesToTest );
                    } catch ( IOException exc ) {
                        Utils.showException( exc );
                    }
                    
                    listPackages.setEnabled( true );
                    listTestSets.setEnabled( true );
                    btnBuildTestPackage.setEnabled( true );
                    btnAddPackage.setEnabled( true );
                    btnRemovePackage.setEnabled( true );
                    checkGenerateResultsSpreadsheet.setEnabled( true );
                    btnLoadTestSet.setEnabled( true );
                    btnRunTest.setEnabled( true );
                    menuItemBuildTestPackage.setEnabled( true );
                    menuItemAddPackage.setEnabled( true );
                    menuItemLoadTestSets.setEnabled( true);
                    menuItemRunTest.setEnabled( true );
                    menuItemExit.setEnabled( true );
                    menuItemTestSets.setEnabled( true );
                    menuItemDependencies.setEnabled( true );
                    menuItemTheme.setEnabled( true );
                    menuItemPlagiarismDetector.setEnabled( true );
                    menuItemStyleChecker.setEnabled( true );
                    menuItemHowTo.setEnabled( true );
                    menuItemAbout.setEnabled( true );
                    lblStatus.setText( bundle.getString( "MainWindow.menuItemPlagiarismDetector.done" ) );
                    
                }
                
            }).start();
            
        } else {
            JOptionPane.showMessageDialog( 
                    this, 
                    bundle.getString( "MainWindow.menuItemPlagiarismDetector.errorTest" ), 
                    bundle.getString( "MainWindow.errorTitle" ), JOptionPane.ERROR_MESSAGE );
        }
        
    }
    
    private void configureLightTheme() {

        FlatIntelliJLaf.setup();
        Utils.setPref( Utils.PREF_CURRENT_THEME, "light" );
        
        Color background = new Color( 255, 255, 255 );
        textPaneProcessOutputBackgroundColor = background;
        resultPanelBackgroundColor = background;
        testSetDetailsDialogBackgroundColor = background;
        plagiarismPanelBackgroundColor = background;
    
        textPaneProcessOutput.setBackground( textPaneProcessOutputBackgroundColor );
        resultPanel.setBackgroundColor( resultPanelBackgroundColor );
        
        SwingUtilities.updateComponentTreeUI( this );
        SwingUtilities.updateComponentTreeUI( popupMenu );
        
        resultPanel.repaint();
            
    }

    private void configureDarkTheme() {

        FlatDarculaLaf.setup();
        Utils.setPref( Utils.PREF_CURRENT_THEME, "dark" );
        
        Color background = new Color( 180, 180, 180 );
        textPaneProcessOutputBackgroundColor = background;
        resultPanelBackgroundColor = background;
        testSetDetailsDialogBackgroundColor = background;
        plagiarismPanelBackgroundColor = background;
        
        textPaneProcessOutput.setBackground( textPaneProcessOutputBackgroundColor );
        resultPanel.setBackgroundColor( resultPanelBackgroundColor );
        
        SwingUtilities.updateComponentTreeUI( this );
        SwingUtilities.updateComponentTreeUI( popupMenu );
        
        resultPanel.repaint();
        
    }
    
    private List<File> getJavaClasspathFiles() {
        
        List<File> javaClasspathFiles = new ArrayList<>();
        javaClasspathFiles.add( new File( "D:\\Google Drive\\Projetos e Códigos\\Corretor de Exercícios\\JJudge\\testSets\\dependencies\\AlgoritmosEstruturasDeDados.jar" ) );
        javaClasspathFiles.add( new File( "D:\\Google Drive\\Projetos e Códigos\\Corretor de Exercícios\\JJudge\\testSets\\dependencies" ) );
        
        return javaClasspathFiles;
        
    }
    
    private void prepareForJavaDebug() {
        
        testSets = Utils.loadTestSets( new File( "testSets/javaTestSets.json" ) );
        buildTestSetsModel();
        listPackagesModel.addElement( new File( "testSets/javaTest.jjd" ) );
        listPackagesModel.addElement( new File( "testSets/javaTestFiles/Exercicio1$1.java" ) );
        listPackagesModel.addElement( new File( "testSets/javaTestFiles/Exercicio1$2.java" ) );
        listPackagesModel.addElement( new File( "testSets/javaTestFiles/Exercicio1$3.java" ) );
        listPackagesModel.addElement( new File( "testSets/javaTestFiles/Exercicio1$4.java" ) );
        listPackagesModel.addElement( new File( "testSets/javaTestFiles/Exercicio1$5.java" ) );
        
    }
    
    private void prepareForCDebug() {
        
        testSets = Utils.loadTestSets( new File( "testSets/cTestSets.json" ) );
        buildTestSetsModel();
        listPackagesModel.addElement( new File( "testSets/cTest.jjd" ) );
        listPackagesModel.addElement( new File( "testSets/cTestFiles/ex1.1.c" ) );
        listPackagesModel.addElement( new File( "testSets/cTestFiles/ex1.2.c" ) );
        listPackagesModel.addElement( new File( "testSets/cTestFiles/ex1.3.c" ) );
        listPackagesModel.addElement( new File( "testSets/cTestFiles/ex1.4.c" ) );
        listPackagesModel.addElement( new File( "testSets/cTestFiles/ex1.5.c" ) );
        
    }
    
    private void prepareForGeneralDebug() {
        
        testSets = Utils.loadTestSets( new File( "testSets/testSets.json" ) );
        buildTestSetsModel();
        listPackagesModel.addElement( new File( "testSets/debugPackageC.jjd" ) );
        listPackagesModel.addElement( new File( "testSets/debugPackageCPP.jjd" ) );
        listPackagesModel.addElement( new File( "testSets/debugPackageJAVA.jjd" ) );
        listPackagesModel.addElement( new File( "testSets/debugPackagePYTHON.jjd" ) );
        
    }
    
    private void prepareForPlagiarismDetectorDebug() {
        
        listPackagesModel.addElement( new File( "testPlagiarism/student1.jjd" ) );
        listPackagesModel.addElement( new File( "testPlagiarism/student2.jjd" ) );
        listPackagesModel.addElement( new File( "testPlagiarism/student3.jjd" ) );
        listPackagesModel.addElement( new File( "testPlagiarism/student4.jjd" ) );
        
    }
    
    private void prepareForStyleCheckerDebug() {
        
        listPackagesModel.addElement( new File( "testStyle/ok.c" ) );
        listPackagesModel.addElement( new File( "testStyle/nok.c" ) );
        listPackagesModel.addElement( new File( "testStyle/c.jjd" ) );
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        menuItemShowDetails = new javax.swing.JMenuItem();
        btnGroupTheme = new javax.swing.ButtonGroup();
        panelPackages = new javax.swing.JPanel();
        scrollPackages = new javax.swing.JScrollPane();
        listPackages = new javax.swing.JList<>();
        btnBuildTestPackage = new javax.swing.JButton();
        btnAddPackage = new javax.swing.JButton();
        btnRemovePackage = new javax.swing.JButton();
        panelTestSets = new javax.swing.JPanel();
        scrollTestSets = new javax.swing.JScrollPane();
        listTestSets = new javax.swing.JList<>();
        lblStatus = new javax.swing.JLabel();
        btnLoadTestSet = new javax.swing.JButton();
        btnRunTest = new javax.swing.JButton();
        checkGenerateResultsSpreadsheet = new javax.swing.JCheckBox();
        panelProcessOutput = new javax.swing.JPanel();
        scrollProcessOutput = new javax.swing.JScrollPane();
        textPaneProcessOutput = new javax.swing.JTextPane();
        panelResults = new javax.swing.JPanel();
        scrollResults = new javax.swing.JScrollPane();
        resultPanel = new br.com.davidbuzatto.jjudge.gui.ResultPanel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemBuildTestPackage = new javax.swing.JMenuItem();
        menuItemAddPackage = new javax.swing.JMenuItem();
        sepMenuFile01 = new javax.swing.JPopupMenu.Separator();
        menuItemLoadTestSets = new javax.swing.JMenuItem();
        menuItemRunTest = new javax.swing.JMenuItem();
        sepMenuFile02 = new javax.swing.JPopupMenu.Separator();
        menuItemExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuItemTestSets = new javax.swing.JMenuItem();
        sepMenuEdit01 = new javax.swing.JPopupMenu.Separator();
        menuItemDependencies = new javax.swing.JMenuItem();
        sepMenuEdit02 = new javax.swing.JPopupMenu.Separator();
        menuItemTheme = new javax.swing.JMenu();
        menuItemRadioLightTheme = new javax.swing.JRadioButtonMenuItem();
        menuItemRadioDarkTheme = new javax.swing.JRadioButtonMenuItem();
        menuTools = new javax.swing.JMenu();
        menuItemPlagiarismDetector = new javax.swing.JMenuItem();
        menuItemStyleChecker = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuItemHowTo = new javax.swing.JMenuItem();
        sepMenuHelp01 = new javax.swing.JPopupMenu.Separator();
        menuItemAbout = new javax.swing.JMenuItem();

        menuItemShowDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/report.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle"); // NOI18N
        menuItemShowDetails.setText(bundle.getString("MainWindow.menuItemShowDetails.text")); // NOI18N
        menuItemShowDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemShowDetailsActionPerformed(evt);
            }
        });
        popupMenu.add(menuItemShowDetails);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JJudge - " + java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("JJudge.version"));

        panelPackages.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainWindow.panelPackages.border.title"))); // NOI18N

        listPackages.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listPackagesKeyReleased(evt);
            }
        });
        scrollPackages.setViewportView(listPackages);

        btnBuildTestPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/package.png"))); // NOI18N
        btnBuildTestPackage.setText(bundle.getString("MainWindow.btnBuildTestPackage.text")); // NOI18N
        btnBuildTestPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuildTestPackageActionPerformed(evt);
            }
        });

        btnAddPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/add.png"))); // NOI18N
        btnAddPackage.setText(bundle.getString("MainWindow.btnAddPackage.text")); // NOI18N
        btnAddPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPackageActionPerformed(evt);
            }
        });

        btnRemovePackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/delete.png"))); // NOI18N
        btnRemovePackage.setText(bundle.getString("MainWindow.btnRemovePackage.text")); // NOI18N
        btnRemovePackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePackageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPackagesLayout = new javax.swing.GroupLayout(panelPackages);
        panelPackages.setLayout(panelPackagesLayout);
        panelPackagesLayout.setHorizontalGroup(
            panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPackagesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPackages, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPackagesLayout.createSequentialGroup()
                        .addComponent(btnBuildTestPackage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddPackage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemovePackage)))
                .addContainerGap())
        );
        panelPackagesLayout.setVerticalGroup(
            panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPackagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPackages, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPackagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemovePackage)
                    .addComponent(btnAddPackage)
                    .addComponent(btnBuildTestPackage))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTestSets.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainWindow.panelTestSets.border.title"))); // NOI18N

        listTestSets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listTestSets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listTestSetsMousePressed(evt);
            }
        });
        scrollTestSets.setViewportView(listTestSets);

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(0, 102, 204));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblStatus.setText(bundle.getString("MainWindow.lblStatus.text")); // NOI18N

        btnLoadTestSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/database_refresh.png"))); // NOI18N
        btnLoadTestSet.setText(bundle.getString("MainWindow.btnLoadTestSet.text")); // NOI18N
        btnLoadTestSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadTestSetActionPerformed(evt);
            }
        });

        btnRunTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/accept.png"))); // NOI18N
        btnRunTest.setText(bundle.getString("MainWindow.btnRunTest.text")); // NOI18N
        btnRunTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunTestActionPerformed(evt);
            }
        });

        checkGenerateResultsSpreadsheet.setText(bundle.getString("MainWindow.checkGenerateResultsSpreadsheet.text")); // NOI18N

        javax.swing.GroupLayout panelTestSetsLayout = new javax.swing.GroupLayout(panelTestSets);
        panelTestSets.setLayout(panelTestSetsLayout);
        panelTestSetsLayout.setHorizontalGroup(
            panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestSetsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTestSetsLayout.createSequentialGroup()
                        .addComponent(scrollTestSets, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelTestSetsLayout.createSequentialGroup()
                        .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRunTest))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTestSetsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(checkGenerateResultsSpreadsheet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadTestSet)))
                .addContainerGap())
        );
        panelTestSetsLayout.setVerticalGroup(
            panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTestSetsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollTestSets, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadTestSet)
                    .addComponent(checkGenerateResultsSpreadsheet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTestSetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRunTest)
                    .addComponent(lblStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelProcessOutput.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainWindow.panelProcessOutput.border.title"))); // NOI18N

        textPaneProcessOutput.setEditable(false);
        textPaneProcessOutput.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scrollProcessOutput.setViewportView(textPaneProcessOutput);

        javax.swing.GroupLayout panelProcessOutputLayout = new javax.swing.GroupLayout(panelProcessOutput);
        panelProcessOutput.setLayout(panelProcessOutputLayout);
        panelProcessOutputLayout.setHorizontalGroup(
            panelProcessOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcessOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollProcessOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelProcessOutputLayout.setVerticalGroup(
            panelProcessOutputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcessOutputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollProcessOutput)
                .addContainerGap())
        );

        panelResults.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("MainWindow.panelResults.border.title"))); // NOI18N

        resultPanel.setFocusable(false);

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
        );

        scrollResults.setViewportView(resultPanel);

        javax.swing.GroupLayout panelResultsLayout = new javax.swing.GroupLayout(panelResults);
        panelResults.setLayout(panelResultsLayout);
        panelResultsLayout.setHorizontalGroup(
            panelResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollResults)
                .addContainerGap())
        );
        panelResultsLayout.setVerticalGroup(
            panelResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollResults)
                .addContainerGap())
        );

        menuFile.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuFile.m").charAt(0));
        menuFile.setText(bundle.getString("MainWindow.menuFile.text")); // NOI18N

        menuItemBuildTestPackage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        menuItemBuildTestPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/package.png"))); // NOI18N
        menuItemBuildTestPackage.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemBuildTestPackage.m").charAt(0));
        menuItemBuildTestPackage.setText(bundle.getString("MainWindow.menuItemBuildTestPackage.text")); // NOI18N
        menuItemBuildTestPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemBuildTestPackageActionPerformed(evt);
            }
        });
        menuFile.add(menuItemBuildTestPackage);

        menuItemAddPackage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        menuItemAddPackage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/add.png"))); // NOI18N
        menuItemAddPackage.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemAddPackage.m").charAt(0));
        menuItemAddPackage.setText(bundle.getString("MainWindow.menuItemAddPackage.text")); // NOI18N
        menuItemAddPackage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAddPackageActionPerformed(evt);
            }
        });
        menuFile.add(menuItemAddPackage);
        menuFile.add(sepMenuFile01);

        menuItemLoadTestSets.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menuItemLoadTestSets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/database_refresh.png"))); // NOI18N
        menuItemLoadTestSets.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemLoadTestSets.m").charAt(0));
        menuItemLoadTestSets.setText(bundle.getString("MainWindow.menuItemLoadTestSets.text")); // NOI18N
        menuItemLoadTestSets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLoadTestSetsActionPerformed(evt);
            }
        });
        menuFile.add(menuItemLoadTestSets);

        menuItemRunTest.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        menuItemRunTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/accept.png"))); // NOI18N
        menuItemRunTest.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemRunTest.m").charAt(0));
        menuItemRunTest.setText(bundle.getString("MainWindow.menuItemRunTest.text")); // NOI18N
        menuItemRunTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRunTestActionPerformed(evt);
            }
        });
        menuFile.add(menuItemRunTest);
        menuFile.add(sepMenuFile02);

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/door_out.png"))); // NOI18N
        menuItemExit.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemExit.m").charAt(0));
        menuItemExit.setText(bundle.getString("MainWindow.menuItemExit.text")); // NOI18N
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        menuEdit.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuEdit.m").charAt(0));
        menuEdit.setText(bundle.getString("MainWindow.menuEdit.text")); // NOI18N

        menuItemTestSets.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        menuItemTestSets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/database_edit.png"))); // NOI18N
        menuItemTestSets.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemTestSets.m").charAt(0));
        menuItemTestSets.setText(bundle.getString("MainWindow.menuItemTestSets.text")); // NOI18N
        menuItemTestSets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTestSetsActionPerformed(evt);
            }
        });
        menuEdit.add(menuItemTestSets);
        menuEdit.add(sepMenuEdit01);

        menuItemDependencies.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        menuItemDependencies.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/cog.png"))); // NOI18N
        menuItemDependencies.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemDependencies.m").charAt(0));
        menuItemDependencies.setText(bundle.getString("MainWindow.menuItemDependencies.text")); // NOI18N
        menuItemDependencies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDependenciesActionPerformed(evt);
            }
        });
        menuEdit.add(menuItemDependencies);
        menuEdit.add(sepMenuEdit02);

        menuItemTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/palette.png"))); // NOI18N
        menuItemTheme.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemTheme.m").charAt(0));
        menuItemTheme.setText(bundle.getString("MainWindow.menuItemTheme.text")); // NOI18N

        btnGroupTheme.add(menuItemRadioLightTheme);
        menuItemRadioLightTheme.setSelected(true);
        menuItemRadioLightTheme.setText(bundle.getString("MainWindow.menuItemRadioLightTheme.text")); // NOI18N
        menuItemRadioLightTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRadioLightThemeActionPerformed(evt);
            }
        });
        menuItemTheme.add(menuItemRadioLightTheme);

        btnGroupTheme.add(menuItemRadioDarkTheme);
        menuItemRadioDarkTheme.setText(bundle.getString("MainWindow.menuItemRadioDarkTheme.text")); // NOI18N
        menuItemRadioDarkTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemRadioDarkThemeActionPerformed(evt);
            }
        });
        menuItemTheme.add(menuItemRadioDarkTheme);

        menuEdit.add(menuItemTheme);

        menuBar.add(menuEdit);

        menuTools.setText(bundle.getString("MainWindow.menuTools.text")); // NOI18N

        menuItemPlagiarismDetector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/report.png"))); // NOI18N
        menuItemPlagiarismDetector.setText(bundle.getString("MainWindow.menuItemPlagiarismDetector.text")); // NOI18N
        menuItemPlagiarismDetector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPlagiarismDetectorActionPerformed(evt);
            }
        });
        menuTools.add(menuItemPlagiarismDetector);

        menuItemStyleChecker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/accept.png"))); // NOI18N
        menuItemStyleChecker.setText(bundle.getString("MainWindow.menuItemStyleChecker.text")); // NOI18N
        menuItemStyleChecker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemStyleCheckerActionPerformed(evt);
            }
        });
        menuTools.add(menuItemStyleChecker);

        menuBar.add(menuTools);

        menuHelp.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuHelp.m").charAt(0));
        menuHelp.setText(bundle.getString("MainWindow.menuHelp.text")); // NOI18N

        menuItemHowTo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        menuItemHowTo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/help.png"))); // NOI18N
        menuItemHowTo.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemHowTo.m").charAt(0));
        menuItemHowTo.setText(bundle.getString("MainWindow.menuItemHowTo.text")); // NOI18N
        menuItemHowTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemHowToActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemHowTo);
        menuHelp.add(sepMenuHelp01);

        menuItemAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/davidbuzatto/jjudge/gui/icons/information.png"))); // NOI18N
        menuItemAbout.setMnemonic(java.util.ResourceBundle.getBundle("br/com/davidbuzatto/jjudge/gui/Bundle").getString("MainWindow.menuItemAbout.m").charAt(0));
        menuItemAbout.setText(bundle.getString("MainWindow.menuItemAbout.text")); // NOI18N
        menuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuItemAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPackages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTestSets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelProcessOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelTestSets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelPackages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelProcessOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPackageActionPerformed
        addPackage();
    }//GEN-LAST:event_btnAddPackageActionPerformed

    private void btnRemovePackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePackageActionPerformed
        removePackage();
    }//GEN-LAST:event_btnRemovePackageActionPerformed

    private void btnRunTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunTestActionPerformed
        if ( !running ) {
            runTest();
        } else {
            running = false;
            btnRunTest.setText( bundle.getString( "MainWindow.btnRunTestStopping.text" ) );
            btnRunTest.setEnabled( false );
        }
    }//GEN-LAST:event_btnRunTestActionPerformed

    private void listTestSetsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTestSetsMousePressed
        
        if ( SwingUtilities.isRightMouseButton( evt ) ) {
            int index = listTestSets.locationToIndex( evt.getPoint() );
            if ( index != -1 ) {
                listTestSets.setSelectedIndex( index );
                listTestSets.requestFocus();
                popupMenu.show( listTestSets, evt.getX(), evt.getY() );
            }
        }
        
    }//GEN-LAST:event_listTestSetsMousePressed

    private void menuItemShowDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemShowDetailsActionPerformed
        
        TestSetDetailsDialog r = new TestSetDetailsDialog( 
                this, 
                true, 
                listTestSets.getSelectedValue(),
                testSetDetailsDialogBackgroundColor );
        r.setVisible( true );
        
    }//GEN-LAST:event_menuItemShowDetailsActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        System.exit( 0 );
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void menuItemTestSetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemTestSetsActionPerformed
        
        EditTestSetsDialog etsd = new EditTestSetsDialog( this, true, testSets );
        etsd.setVisible( true );
        
        if ( etsd.getTestSets() != null ) {
            testSets = etsd.getTestSets();
            buildTestSetsModel();
        }
        
    }//GEN-LAST:event_menuItemTestSetsActionPerformed

    private void menuItemHowToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemHowToActionPerformed

        JOptionPane.showMessageDialog( 
                this, 
                bundle.getString( "MainWindow.menuItemHowToActionPerformed.message" ),
                bundle.getString( "MainWindow.menuItemHowToActionPerformed.title" ), 
                JOptionPane.INFORMATION_MESSAGE );
        
    }//GEN-LAST:event_menuItemHowToActionPerformed

    private void menuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAboutActionPerformed
                
        JOptionPane.showMessageDialog( 
                this, 
                bundle.getString( "MainWindow.menuItemAboutActionPerformed.message" ), 
                bundle.getString( "MainWindow.menuItemAboutActionPerformed.title" ), 
                JOptionPane.INFORMATION_MESSAGE );
        
    }//GEN-LAST:event_menuItemAboutActionPerformed

    private void menuItemLoadTestSetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLoadTestSetsActionPerformed
        loadTestSet();
    }//GEN-LAST:event_menuItemLoadTestSetsActionPerformed

    private void menuItemBuildTestPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemBuildTestPackageActionPerformed
        buildTestPackage();
    }//GEN-LAST:event_menuItemBuildTestPackageActionPerformed

    private void btnBuildTestPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuildTestPackageActionPerformed
        buildTestPackage();
    }//GEN-LAST:event_btnBuildTestPackageActionPerformed

    private void btnLoadTestSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadTestSetActionPerformed
        loadTestSet();
    }//GEN-LAST:event_btnLoadTestSetActionPerformed

    private void menuItemAddPackageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemAddPackageActionPerformed
        addPackage();
    }//GEN-LAST:event_menuItemAddPackageActionPerformed

    private void menuItemRunTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRunTestActionPerformed
        runTest();
    }//GEN-LAST:event_menuItemRunTestActionPerformed

    private void menuItemRadioLightThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRadioLightThemeActionPerformed
        configureLightTheme();
    }//GEN-LAST:event_menuItemRadioLightThemeActionPerformed

    private void menuItemRadioDarkThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemRadioDarkThemeActionPerformed
        configureDarkTheme();
    }//GEN-LAST:event_menuItemRadioDarkThemeActionPerformed

    private void listPackagesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listPackagesKeyReleased
        if ( evt.getKeyCode() == KeyEvent.VK_DELETE ) {
            removePackage();
        }
    }//GEN-LAST:event_listPackagesKeyReleased

    private void menuItemDependenciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDependenciesActionPerformed
        ConfigureTestDependenciesDialog ctdd = new ConfigureTestDependenciesDialog( this, true, javaClasspathFiles );
        ctdd.setVisible( true );
    }//GEN-LAST:event_menuItemDependenciesActionPerformed

    private void menuItemPlagiarismDetectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPlagiarismDetectorActionPerformed
        runPlagiarismDetector();
    }//GEN-LAST:event_menuItemPlagiarismDetectorActionPerformed

    private void menuItemStyleCheckerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemStyleCheckerActionPerformed
        runStyleChecker();
    }//GEN-LAST:event_menuItemStyleCheckerActionPerformed
    
    public static void main( String[] args ) {
        
        if ( args.length == 4 && args[0].equals( "-nogui" ) ) {
            
            String fileToTestPath = args[1];
            String testSetsFilePath = args[2];
            int testSetToExecute = Integer.parseInt( args[3] );
            
            File fileToTest = new File( new File( fileToTestPath ).getAbsolutePath() );
            File testSetsFile = new File( testSetsFilePath );
            List<TestSet> testSets = Utils.loadTestSets( testSetsFile );
            
            if( testSetToExecute < testSets.size() ) {
                TestSet testSet = testSets.get( testSetToExecute );
                NoGuiModeWrapper.runTest( fileToTest, testSet );
            } else {
                System.out.println( "Invalid test set to execute!" );
            }
            
        } else {
            
            EventQueue.invokeLater( new Runnable() {

                public void run() {

                    System.setOut( new PrintStream( new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8 ) );
                    System.setErr( new PrintStream( new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8 ) );

                    int secondsToTimeout = 5;

                    if ( args.length == 2 ) {
                        if ( args[0].equals( "-stt" ) ) {
                            try {
                                secondsToTimeout = Integer.parseInt( args[1] );
                            } catch ( NumberFormatException exc ) {
                            }
                        }
                    }

                    Utils.preparePreferences( false );
                    MainWindow mainWindow = new MainWindow( secondsToTimeout );

                    switch ( Utils.getPref( Utils.PREF_CURRENT_THEME ) ) {
                        case "light":
                            mainWindow.configureLightTheme();
                            mainWindow.menuItemRadioLightTheme.setSelected( true );
                            break;
                        case "dark":
                            mainWindow.configureDarkTheme();
                            mainWindow.menuItemRadioDarkTheme.setSelected( true );
                            break;
                    }
                    
                    if ( args.length == 1 ) {
                        
                        File fileToOpen = new File( args[0] );
                        
                        if ( fileToOpen.exists() && fileToOpen.getName().endsWith( "jjd" ) ) {
                            fileToOpen = Utils.renameFileToValidName( fileToOpen.getAbsoluteFile() );
                            mainWindow.listPackagesModel.addElement( fileToOpen );
                        }
                        
                    }

                    Utils.updateSplashScreen( 6000 );
                    mainWindow.setVisible( true );

                }

            });
            
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPackage;
    private javax.swing.JButton btnBuildTestPackage;
    private javax.swing.ButtonGroup btnGroupTheme;
    private javax.swing.JButton btnLoadTestSet;
    private javax.swing.JButton btnRemovePackage;
    private javax.swing.JButton btnRunTest;
    private javax.swing.JCheckBox checkGenerateResultsSpreadsheet;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<File> listPackages;
    private javax.swing.JList<TestSet> listTestSets;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JMenuItem menuItemAddPackage;
    private javax.swing.JMenuItem menuItemBuildTestPackage;
    private javax.swing.JMenuItem menuItemDependencies;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemHowTo;
    private javax.swing.JMenuItem menuItemLoadTestSets;
    private javax.swing.JMenuItem menuItemPlagiarismDetector;
    private javax.swing.JRadioButtonMenuItem menuItemRadioDarkTheme;
    private javax.swing.JRadioButtonMenuItem menuItemRadioLightTheme;
    private javax.swing.JMenuItem menuItemRunTest;
    private javax.swing.JMenuItem menuItemShowDetails;
    private javax.swing.JMenuItem menuItemStyleChecker;
    private javax.swing.JMenuItem menuItemTestSets;
    private javax.swing.JMenu menuItemTheme;
    private javax.swing.JMenu menuTools;
    private javax.swing.JPanel panelPackages;
    private javax.swing.JPanel panelProcessOutput;
    private javax.swing.JPanel panelResults;
    private javax.swing.JPanel panelTestSets;
    private javax.swing.JPopupMenu popupMenu;
    private br.com.davidbuzatto.jjudge.gui.ResultPanel resultPanel;
    private javax.swing.JScrollPane scrollPackages;
    private javax.swing.JScrollPane scrollProcessOutput;
    private javax.swing.JScrollPane scrollResults;
    private javax.swing.JScrollPane scrollTestSets;
    private javax.swing.JPopupMenu.Separator sepMenuEdit01;
    private javax.swing.JPopupMenu.Separator sepMenuEdit02;
    private javax.swing.JPopupMenu.Separator sepMenuFile01;
    private javax.swing.JPopupMenu.Separator sepMenuFile02;
    private javax.swing.JPopupMenu.Separator sepMenuHelp01;
    private javax.swing.JTextPane textPaneProcessOutput;
    // End of variables declaration//GEN-END:variables
}
