/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.utils;

import br.com.davidbuzatto.jjudge.processor.ExecutionState;
import br.com.davidbuzatto.jjudge.processor.Processor;
import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.testsets.Test;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

/**
 *
 * @author David
 */
public class Utils {

    private static final String PREFERENCES_PATH = "br.com.davidbuzatto.jjudge";
    private static final Preferences PREFS = Preferences.userRoot().node( PREFERENCES_PATH ); // cria o prefs
    public static final String PREF_THEME = "THEME";
    
    public static final ResourceBundle bundle = ResourceBundle.getBundle( "br/com/davidbuzatto/jjudge/gui/Bundle" );
    
    public static void zipFile( File fileToZip, File zipFile ) throws IOException {
        
        try ( FileOutputStream fos = new FileOutputStream( zipFile );
                FileInputStream fis = new FileInputStream( fileToZip );
                ZipOutputStream zipOut = new ZipOutputStream( fos ) ) {
            
            ZipEntry zipEntry = new ZipEntry( fileToZip.getName() );
            
            zipOut.putNextEntry( zipEntry );
            
            byte[] bytes = new byte[1024];
            int length;
            
            while( ( length = fis.read( bytes ) ) >= 0 ) {
                zipOut.write( bytes, 0, length );
            }
            
        }
        
    }
    
    public static void zipFiles( File[] filesToZip, File zipFile ) throws IOException {
        
        try ( FileOutputStream fos = new FileOutputStream( zipFile );
                ZipOutputStream zipOut = new ZipOutputStream( fos ) ) {
            
            for ( File fileToZip : filesToZip ) {
                
                try ( FileInputStream fis = new FileInputStream( fileToZip ) ) {
                    
                    ZipEntry zipEntry = new ZipEntry( fileToZip.getName() );
                    zipOut.putNextEntry( zipEntry );
                    
                    byte[] bytes = new byte[1024];
                    int length;
                    
                    while( ( length = fis.read( bytes ) ) >= 0 ) {
                        zipOut.write( bytes, 0, length );
                    }
                    
                }
                
            }
            
        }
        
    }
    
    public static void zipDirectory( File dirToZip, File zipFile ) throws IOException {
        
        try ( FileOutputStream fos = new FileOutputStream( zipFile );
                ZipOutputStream zipOut = new ZipOutputStream( fos ) ) {
            
            recursiveZipFile( dirToZip, dirToZip.getName(), zipOut );
            
        }
        
    }
    
    public static void recursiveZipFile( File fileToZip, String fileName, ZipOutputStream zipOut ) throws IOException {
        
        if ( fileToZip.isHidden() ) {
            return;
        } 
        
        if ( fileToZip.isDirectory() ) {
            
            if ( fileName.endsWith("/") ) {
                zipOut.putNextEntry( new ZipEntry( fileName ) );
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry( new ZipEntry( fileName + "/" ) );
                zipOut.closeEntry();
            }
            
            File[] children = fileToZip.listFiles();
            
            for ( File childFile : children ) {
                recursiveZipFile( childFile, fileName + "/" + childFile.getName(), zipOut );
            }
            
            return;
            
        }
        
        try ( FileInputStream fis = new FileInputStream( fileToZip ) ) {
            
            ZipEntry zipEntry = new ZipEntry( fileName );
            
            zipOut.putNextEntry( zipEntry );
            
            byte[] bytes = new byte[1024];
            int length;
            
            while ( ( length = fis.read( bytes ) ) >= 0 ) {
                zipOut.write( bytes, 0, length );
            }
            
        }
        
    }
    
    public static void unzip( File file, File destDir ) throws IOException {
        
        byte[] buffer = new byte[1024];
        
        try ( ZipInputStream zis = new ZipInputStream( new FileInputStream( file ) ) ) {
            
            ZipEntry zipEntry = zis.getNextEntry();
            
            if ( !destDir.exists() ) {
                destDir.mkdir();
            }
            
            while ( zipEntry != null ) {
                
                File newFile = newFile( destDir, zipEntry );
                try (FileOutputStream fos = new FileOutputStream( newFile )) {
                    int len;
                    while ( ( len = zis.read( buffer ) ) > 0 ) {
                        fos.write( buffer, 0, len );
                    }
                }
                zipEntry = zis.getNextEntry();
                
            }
            
            zis.closeEntry();
            
        }
        
    }

    public static File newFile( 
            File destinationDir, ZipEntry zipEntry ) throws IOException {
        
        File destFile = new File( destinationDir, zipEntry.getName() );

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if ( !destFilePath.startsWith( destDirPath + File.separator ) ) {
            throw new IOException( "Entry is outside of the target dir: " + zipEntry.getName() );
        }

        return destFile;
        
    }
    
    public static void completeUnzip( File file, File destDir ) throws IOException {
        
        try( ZipFile zipFile = new ZipFile( file ) ) {
            
            FileSystem fileSystem = FileSystems.getDefault();
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
             
            String uncompressedDirectory = destDir.getPath();
            Files.createDirectory( fileSystem.getPath( uncompressedDirectory ) );
            
            while ( entries.hasMoreElements() ) {
                
                ZipEntry entry = entries.nextElement();
                
                if ( entry.isDirectory() ) {
                    Files.createDirectories( fileSystem.getPath( uncompressedDirectory + "/" + entry.getName() ) );
                } else {
                    try ( InputStream is = zipFile.getInputStream( entry );
                          BufferedInputStream bis = new BufferedInputStream( is ) ) {
                        
                        String uncompressedFileName = uncompressedDirectory + "/" + entry.getName();
                        Path uncompressedFilePath = fileSystem.getPath( uncompressedFileName );
                        Files.createFile( uncompressedFilePath );
                        
                        try ( FileOutputStream fileOutput = new FileOutputStream( uncompressedFileName ) ) {
                            while ( bis.available() > 0 ) {
                                fileOutput.write( bis.read() );
                            }
                        }
                        
                    }
                }
            }
        }
        
    }
    
    public static List<TestSet> loadTestSets() {
        
        List<TestSet> testSets;

        JsonReader reader = new JsonReader(
                new InputStreamReader( 
                        Utils.class.getResourceAsStream( 
                                "/testSets.json"  ) ) );

        Type listType = new TypeToken<ArrayList<TestSet>>(){}.getType();
        testSets = new Gson().fromJson( reader, listType );
        
        return testSets;
        
    }
    
    public static List<TestSet> loadTestSets( File file ) {
        
        List<TestSet> testSets = null;
        
        try {
            
            JsonReader reader = new JsonReader(
                    new FileReader( file, StandardCharsets.UTF_8 ) );

            Type listType = new TypeToken<ArrayList<TestSet>>(){}.getType();
            testSets = new Gson().fromJson( reader, listType );
            
        } catch ( FileNotFoundException exc ) {
            Utils.showException( exc );
        } catch ( IOException exc ) {
            Utils.showException( exc );
        } catch ( JsonSyntaxException exc ) {
            Utils.showException( exc );
        }
        
        return testSets;
        
    }
    
    public static Student loadStudent( String baseDir ) throws IOException {
        
        Gson gson = new Gson();
        Student student;
        
        try (JsonReader reader = new JsonReader(
                new FileReader(
                        new File( String.format( "%s/student.json", baseDir )), StandardCharsets.UTF_8 ))) {
            student = gson.fromJson( reader, Student.class );
        }
        
        return student;
        
    }
    
    public static void saveStudent( Student student, File file ) throws IOException {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String json = gson.toJson( student );
        
        try (PrintWriter writer = new PrintWriter( file, StandardCharsets.UTF_8 )) {
            writer.print( json );
        }
        
    }
    
    public static String showScapeChars( String text ) {
        
        return text
                .replace( "\n", "\\n" )
                .replace( "\t", "\\t" );
        
    }
    
    public static TestSetResult verify( 
            TestSet testSet, 
            Student student, 
            String baseDir, 
            int secondsToTimeout, 
            boolean outputStreams,
            JTextPane textPane,
            File file ) throws IOException, InterruptedException {
        
        TestSetResult testSetResult = new TestSetResult();
        testSetResult.setStudent( student );
        testSetResult.setTestResults( new ArrayList<>() );
        
        // run all
        if ( file == null ) {
            
            for ( Test t : testSet.getTests() ) {

                TestResult testResult = Processor.compileAndRun( 
                        t.getName(), 
                        baseDir, 
                        secondsToTimeout, 
                        outputStreams,
                        t.getTestCases(),
                        testSet.getProgrammingLanguage(),
                        textPane );

                testSetResult.getTestResults().add( testResult );

            }
            
        } else {
            
            for ( Test t : testSet.getTests() ) {

                String filenameWithoutExt = file.getName();
                filenameWithoutExt = filenameWithoutExt.substring( 0, filenameWithoutExt.lastIndexOf( "." ) );
                
                TestResult testResult;
                
                if ( filenameWithoutExt.equals( t.getName() ) ) {
                    
                    testResult = Processor.compileAndRun( 
                            t.getName(), 
                            baseDir, 
                            secondsToTimeout, 
                            outputStreams,
                            t.getTestCases(),
                            testSet.getProgrammingLanguage(),
                            textPane );
                
                } else {
                    
                    testResult = new TestResult();
                    testResult.setName( t.getName() );
                    testResult.setTestCasesResult( new ArrayList<>() );
                    testResult.setExecutionState( ExecutionState.DONT_CHECK );
                    
                }
                
                testSetResult.getTestResults().add( testResult );

            }
            
        }
        
        int approved = 0;
        int total = testSet.getTests().size();
        for ( TestResult tr : testSetResult.getTestResults() ) {
            if ( tr.isApproved() ) {
                approved++;
            }
        }
        
        testSetResult.setGrade( 10.0 * approved / total );
        
        return testSetResult;
        
    }
    
    public static TestSetResult runTest( 
            File file, 
            TestSet testSet, 
            boolean outputStreams, 
            int secondsToTimeout,
            JTextPane textPane ) throws IOException, InterruptedException {
        
        File destDir;
        Student student = new Student();
        student.setName( file.getName() );
        student.setCode( "" );
        
        boolean loadStudent = false;
        boolean errorUnzipping = false;
        
        if ( file.getName().endsWith( ".zip" ) ) {
            
            destDir = new File( file.getAbsolutePath().replace( ".zip", "" ).trim() );
            
            try {
                if ( destDir.exists() ) {
                    FileUtils.deleteDirectory( destDir );
                }
                Utils.completeUnzip( file, destDir );
            } catch ( IllegalArgumentException | IOException exc ) {
                errorUnzipping = true;
            }
            
            loadStudent = true;
            
        } else {
            destDir = new File( file.getParent().trim() );
        }
        
        String baseDir = destDir.getAbsolutePath();
            
        if ( loadStudent ) {
            try {
                student = loadStudent( baseDir );
            } catch ( FileNotFoundException exc ) {
                // student file not found
            }
        }
        
        if ( errorUnzipping ) {
            
            TestSetResult tsResult = new TestSetResult();
            tsResult.setStudent( student );
            tsResult.setGrade( 0 );
            tsResult.setTestResults( new ArrayList<>() );
            tsResult.setError( bundle.getString( "MainWindow.resultPanel.wrongFileFormat" ) );
            
            return tsResult;
            
        } else { 
            
            if ( file.getName().endsWith( ".zip" ) ) {
                return verify( 
                        testSet, 
                        student, 
                        baseDir, 
                        secondsToTimeout, 
                        outputStreams,
                        textPane,
                        null );
            } else {
                return verify( 
                        testSet, 
                        student, 
                        baseDir, 
                        secondsToTimeout, 
                        outputStreams,
                        textPane,
                        file );
            }
            
        }
        
    }
    
    public static void addFormattedText( JTextPane textPane, String text, Color color, boolean formatOutputAsConsole ) {
        
        try {
            
            SimpleAttributeSet attr = new SimpleAttributeSet();
            
            StyleConstants.setForeground( attr, color );
            StyleConstants.setBold( attr, true );
            
            if ( formatOutputAsConsole ) {
                
                for ( String line : text.split( "\n" ) ) {
                    
                    int identEnd = line.indexOf( "|-- " );

                    String identText = line.substring( 0, identEnd + 4 );
                    textPane.getDocument().insertString( 
                            textPane.getDocument().getLength(), identText, attr );

                    String textToFormat = line.substring( identEnd + 4 );
                    
                    StyleConstants.setBackground( attr, Color.BLACK );
                    StyleConstants.setForeground( attr, Color.LIGHT_GRAY );
                    textPane.getDocument().insertString( 
                                textPane.getDocument().getLength(), textToFormat, attr );
                    
                    StyleConstants.setBackground( attr, textPane.getBackground() );
                    StyleConstants.setForeground( attr, color );
                    textPane.getDocument().insertString( 
                                textPane.getDocument().getLength(), "\n", attr );
                    
                }
                
            } else {
                textPane.getDocument().insertString( 
                        textPane.getDocument().getLength(), text, attr );
            }
            
        } catch ( BadLocationException exc ) {
            Utils.showException( exc );
        }
        
    }
    
    public static void changeFormattedText( 
            JTextPane textPane, 
            String text, 
            int offset, 
            int length, 
            Color textColor,
            Color backColor ) {
        
        try {
            
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground( attr, textColor );
            StyleConstants.setBackground( attr, backColor );
            StyleConstants.setBold( attr, true );
            
            textPane.getDocument().remove( offset, length );
            textPane.getDocument().insertString( 
                                offset, text, attr );
            
        } catch ( BadLocationException exc ) {
            Utils.showException( exc );
        }
        
    }
    
    public static File processResultsToExcel( List<TestSetResult> tSetResList, TestSet testSet ) {
        
        try {
            
            InputStream inp = Utils.class.getResourceAsStream( "/template.xlsx" );
            XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook( inp );
            XSSFSheet gradeSheet = workbook.getSheetAt( 0 );
            XSSFSheet labelSheet = workbook.getSheetAt( 1 );
            
            workbook.setSheetName( 0, bundle.getString( "Utils.processResultsToExcel.gradeSheet.name" ) );
            workbook.setSheetName( 1, bundle.getString( "Utils.processResultsToExcel.labelSheet.name" ) );
            
            // grades
            Row r = gradeSheet.getRow( 0 );
            r.getCell( 0 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.gradeSheet.student" ) );
            r.getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.gradeSheet.code" ) );
            int cc = 2;   // current cell
            int cr = 1;   // current row
            
            for ( TestResult tr : tSetResList.get( 0 ).getTestResults() ) {
                r.getCell( cc++ ).setCellValue( tr.getName() );
            }
            r.getCell( cc ).setCellValue( bundle.getString( "Utils.processResultsToExcel.gradeSheet.grade" ) );
            
            for ( TestSetResult tsr : tSetResList ) {
            
                r = gradeSheet.getRow( cr++ );
                r.getCell( 0 ).setCellValue( tsr.getStudent().getName() );
                r.getCell( 1 ).setCellValue( tsr.getStudent().getCode() );
                cc = 2;
                
                for ( TestResult tr : tsr.getTestResults() ) {

                    String result;

                    switch ( tr.getExecutionState() ) {
                        case APPROVED:
                            result = "A";
                            break;
                        case REPROVED:
                            result = "R";
                            break;
                        case COMPILATION_ERROR:
                            result = "CE";
                            break;
                        case RUNTIME_ERROR:
                            result = "RE";
                            break;
                        case TIMEOUT_ERROR:
                            result = "TE";
                            break;
                        case FILE_NOT_FOUND_ERROR:
                            result = "FE";
                            break;
                        case DONT_CHECK:
                            result = "DC";
                            break;
                        default:
                            result = "?";
                            break;
                    }
                    
                    r.getCell( cc++ ).setCellValue( result );

                }

                r.getCell( cc ).setCellValue( tsr.getGrade() );

            }
            
            // labels
            r = labelSheet.getRow( 0 );
            r.getCell( 0 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.label" ) );
            r.getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning" ) );
            
            labelSheet.getRow( 1 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.A" ) );
            labelSheet.getRow( 2 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.R" ) );
            labelSheet.getRow( 3 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.CE" ) );
            labelSheet.getRow( 4 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.RE" ) );
            labelSheet.getRow( 5 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.TE" ) );
            labelSheet.getRow( 6 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.FE" ) );
            labelSheet.getRow( 7 ).getCell( 1 ).setCellValue( bundle.getString( "Utils.processResultsToExcel.labelSheet.meaning.DC" ) );
            
            JFileChooser jfc = new JFileChooser( new File( Utils.getPref( "saveSheetPath" ) ) );
            jfc.setDialogTitle( bundle.getString( "Utils.processResultsToExcel.saveResults" ) );
            jfc.setMultiSelectionEnabled( false );
            jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
            jfc.removeChoosableFileFilter( jfc.getFileFilter() );
            jfc.setFileFilter( new FileNameExtensionFilter( bundle.getString( "Utils.processResultsToExcel.fileTypes" ), "xlsx" ) );
            jfc.setSelectedFile( new File( testSet.getDescription() + ".xlsx" ) );
            
            if ( jfc.showSaveDialog( null ) == JFileChooser.APPROVE_OPTION ) {
                
                File f = jfc.getSelectedFile();
                boolean save = true;
                
                if ( f.exists() ) {
                    if ( JOptionPane.showConfirmDialog( null, 
                            bundle.getString( "Utils.processResultsToExcel.confirmOverwrite" ), 
                            bundle.getString( "Utils.processResultsToExcel.confirm" ), 
                            JOptionPane.YES_NO_OPTION ) == JOptionPane.NO_OPTION ) {
                        save = false;
                    }
                } else {
                    if ( !f.getName().endsWith( ".xlsx" ) ) {
                        f = new File( f.getAbsolutePath() + ".xlsx" );
                    }
                }
                
                if ( save ) {
                    Utils.setPref( "saveSheetPath", f.getParentFile().getAbsolutePath() );
                    try ( OutputStream fileOut = new FileOutputStream( f ) ) {
                        workbook.write( fileOut );
                        return f;
                    }
                }
            }
            
        } catch ( IOException | InvalidFormatException exc ) {
            Utils.showException( exc );
        }
        
        return null;
        
    }
    
    public static String identText( String text, int identTabs ) {
        
        StringBuilder identedText = new StringBuilder();
        String[] splited = text.split( "\n" );
        String identation = "";
        
        for ( int i = 0; i < identTabs-1; i++ ) {
            identation += "|   ";
        }
        identation += "|-- ";
        
        boolean first = true;
        for ( String s : splited ) {
            if ( first ) {
                first = false;
            } else {
                identedText.append( "\n" );
            }
            identedText.append( identation ).append( s );
        }
        
        return identedText.toString();
        
    }
    
    public static boolean verifyBackwards( String output, String test ) {
        
        int outputLastIndex = output.length()-1;
        
        if ( output.length() == 0 ) {
            return false;
        }
        
        if ( test.length() == 0 ) {
            return false;
        }
        
        if ( output.length() < test.length() ) {
            return false;
        }
        
        try {
            for ( int i = test.length()-1; i >= 0; i-- ) {
                if ( test.charAt( i ) != output.charAt( outputLastIndex-- ) ) {
                    return false;
                }
            }
        } catch ( IndexOutOfBoundsException exc ) {
            return false;
        }
        
        return true;
        
    }
    
    private static void preparePreferences() {
        Preferences prefs = Preferences.userRoot().node( PREFERENCES_PATH );
        prefs.get( "addPackagePath", new File( "" ).getAbsolutePath() );
        prefs.get( "saveSheetPath", new File( "" ).getAbsolutePath() );
        prefs.get( "loadTestSets", new File( "" ).getAbsolutePath() );
        prefs.get( "saveTestSets", new File( "" ).getAbsolutePath() );
        prefs.get( "buildTestPackagePath", new File( "" ).getAbsolutePath() );
        prefs.get(PREF_THEME, PREFS.get(PREF_THEME, "dark")); // pref de theme
    }
    
    public static String getPref( String key ) {
        preparePreferences();
        Preferences prefs = Preferences.userRoot().node( PREFERENCES_PATH );
        return prefs.get( key, "" );
    }
    
    public static void setPref( String key, String value ) {
        preparePreferences();
        Preferences prefs = Preferences.userRoot().node( PREFERENCES_PATH );
        prefs.put( key, value );
    }
    
    public static Color retrieveStateColor( ExecutionState state ) {
            
        switch ( state ) {
            case PASSED:
                return Colors.PASSED;
            case NOT_PASSED:
                return Colors.NOT_PASSED;
            case APPROVED:
                return Colors.APPROVED;
            case REPROVED:
                return Colors.REPROVED;
            case COMPILATION_ERROR:
                return Colors.COMPILATION_ERROR;
            case RUNTIME_ERROR:
                return Colors.RUNTIME_ERROR;
            case TIMEOUT_ERROR:
                return Colors.TIMEOUT_ERROR;
            case FILE_NOT_FOUND_ERROR:
                return Colors.FILE_NOT_FOUND_ERROR;
            case DONT_CHECK:
                return Colors.DONT_CHECK;
        }
        
        return Colors.ERROR;
        
    }
    
    public static String getExecutionStateIntString( ExecutionState state ) {
        return bundle.getString( "ExecutionState." + state.toString() );
    }
    
    public static void updateSplashScreen( int millisecondsToWait ) {
        
        SplashScreen sp = SplashScreen.getSplashScreen();
        if ( sp != null ) {
            
            Graphics2D g2d = (Graphics2D) sp.createGraphics();
            g2d.setRenderingHint( 
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON );
            g2d.setColor( new Color( 51, 0, 102 ) );
            
            Font f = new Font( "Century Gothic", Font.BOLD, 30 ) ;
            if ( f.getFamily().equals( Font.DIALOG ) ) {
                f = new Font( Font.MONOSPACED, Font.BOLD, 30 ) ;
            }
            g2d.setFont( f );
            
            FontMetrics fm = g2d.getFontMetrics();
            String v = Utils.bundle.getString( "JJudge.version" );
            int vWidth = fm.stringWidth( v );
            
            g2d.drawString( v, 306 - vWidth, 134 );
            g2d.dispose();
            
            sp.update();
            
            try {
                Thread.sleep( millisecondsToWait );
            } catch ( InterruptedException exc ) {
            }
            
        }
        
    }
    
    public static File renameFileToValidName( File file ) {
        
        String oldName = file.getName().trim();
        String newName = oldName.replaceAll( "[^a-zA-Z0-9[.]\\_]", "x" );
        String oldPath = file.getPath().trim();
        String newPath = oldPath.replace( oldName, newName );

        file = new File( oldPath );
        file.renameTo( new File( newPath ) );
        return new File( newPath );
        
    }
    
    public static void renameFilesToValidName( File[] files ) {
        
        for ( int i = 0; i < files.length; i++ ) {
            files[i] = renameFileToValidName( files[i] );
        }
        
    }
    
    public static void showException( Exception exc ) {
        
        try ( StringWriter sw = new StringWriter();
              PrintWriter pw = new PrintWriter( sw ) ) {
            
            exc.printStackTrace( pw );
            
            JOptionPane.showMessageDialog( 
                    null, 
                    new JScrollPane( new JTextArea( sw.toString(), 15, 40 ) ), 
                    bundle.getString( "Utils.processResultsToExcel.exceptionErrorMessage" ), 
                    JOptionPane.ERROR_MESSAGE );
            
        } catch ( IOException iexc ) {
            iexc.printStackTrace();
        }
        
    }

}
