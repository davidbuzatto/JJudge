/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.davidbuzatto.jjudge.utils;

import br.com.davidbuzatto.jjudge.processor.Processor;
import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.testsets.Test;
import br.com.davidbuzatto.jjudge.testsets.TestResult;
import br.com.davidbuzatto.jjudge.testsets.TestSet;
import br.com.davidbuzatto.jjudge.testsets.TestSetResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.awt.Color;
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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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
    
    public static void zipFile( File fileToZip, File zipFile ) throws IOException {
        
        FileOutputStream fos = new FileOutputStream( zipFile );
        ZipOutputStream zipOut = new ZipOutputStream( fos );
        FileInputStream fis = new FileInputStream( fileToZip );
        ZipEntry zipEntry = new ZipEntry( fileToZip.getName() );
        
        zipOut.putNextEntry( zipEntry );
        
        byte[] bytes = new byte[1024];
        int length;
        
        while( ( length = fis.read( bytes ) ) >= 0 ) {
            zipOut.write( bytes, 0, length );
        }
        
        zipOut.close();
        fis.close();
        fos.close();
        
    }
    
    public static void zipFiles( File[] filesToZip, File zipFile ) throws IOException {
        
        FileOutputStream fos = new FileOutputStream( zipFile );
        ZipOutputStream zipOut = new ZipOutputStream( fos );
        
        for ( File fileToZip : filesToZip ) {
            
            FileInputStream fis = new FileInputStream( fileToZip );
            ZipEntry zipEntry = new ZipEntry( fileToZip.getName() );
            zipOut.putNextEntry( zipEntry );
 
            byte[] bytes = new byte[1024];
            int length;
            
            while( ( length = fis.read( bytes ) ) >= 0 ) {
                zipOut.write( bytes, 0, length );
            }
            
            fis.close();
            
        }
        
        zipOut.close();
        fos.close();
        
    }
    
    public static void zipDirectory( File dirToZip, File zipFile ) throws IOException {
        
        FileOutputStream fos = new FileOutputStream( zipFile );
        ZipOutputStream zipOut = new ZipOutputStream( fos );
 
        recursiveZipFile( dirToZip, dirToZip.getName(), zipOut );
        zipOut.close();
        fos.close();
        
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
        
        FileInputStream fis = new FileInputStream( fileToZip );
        ZipEntry zipEntry = new ZipEntry( fileName );
        
        zipOut.putNextEntry( zipEntry );
        
        byte[] bytes = new byte[1024];
        int length;
        
        while ( ( length = fis.read( bytes ) ) >= 0 ) {
            zipOut.write( bytes, 0, length );
        }
        
        fis.close();
        
    }
    
    public static void unzip( File file, File destDir ) throws IOException {
        
        byte[] buffer = new byte[1024];
        
        ZipInputStream zis = new ZipInputStream( new FileInputStream( file ) );
        ZipEntry zipEntry = zis.getNextEntry();
        
        if ( !destDir.exists() ) {
            destDir.mkdir();
        }
        
        while ( zipEntry != null ) {
            
            File newFile = newFile( destDir, zipEntry );
            FileOutputStream fos = new FileOutputStream( newFile );
            
            int len;
            
            while ( ( len = zis.read( buffer ) ) > 0 ) {
                fos.write( buffer, 0, len );
            }
            
            fos.close();
            zipEntry = zis.getNextEntry();
            
        }
        
        zis.closeEntry();
        zis.close();
        
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
    
    public static List<TestSet> loadTestSets() {
        
        List<TestSet> testSets = null;

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
                    new FileReader( file ) );

            Type listType = new TypeToken<ArrayList<TestSet>>(){}.getType();
            testSets = new Gson().fromJson( reader, listType );
            
        } catch ( FileNotFoundException exc ) {
            exc.printStackTrace();
        }
        
        return testSets;
        
    }
    
    public static Student loadStudent( String baseDir ) throws IOException {
        
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(
                new FileReader( 
                        new File( String.format( "%s/student.json", baseDir ))));
        Student student = gson.fromJson( reader, Student.class );
        reader.close();
        
        return student;
        
    }
    
    public static void saveStudent( Student student, File file ) throws IOException {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String json = gson.toJson( student );
        
        PrintWriter writer = new PrintWriter( file );
        writer.print( json );
        
        writer.close();
        
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
            JTextPane textPane ) throws IOException, InterruptedException {
        
        TestSetResult testSetResult = new TestSetResult();
        testSetResult.setStudent( student );
        testSetResult.setTestResults( new ArrayList<>() );
        
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
            File zipFile, 
            TestSet testSet, 
            boolean outputStreams, 
            int secondsToTimeout,
            JTextPane textPane ) throws IOException, InterruptedException {
        
        File destDir = new File( zipFile.getAbsolutePath().replace( ".zip", "" ) );
        String baseDir = destDir.getAbsolutePath();
        
        Utils.unzip( zipFile, destDir );
        Student student = loadStudent( baseDir );
        
        TestSetResult tResSet = verify( 
                testSet, 
                student, 
                baseDir, 
                secondsToTimeout, 
                outputStreams,
                textPane );
        
        return tResSet;
        
    }
    
    public static void addFormattedText( JTextPane textPane, String text, Color color ) {
        
        try {
            
            SimpleAttributeSet attr = new SimpleAttributeSet();
            
            StyleConstants.setForeground( attr, color );
            StyleConstants.setBold( attr, true );
            textPane.getDocument().insertString( 
                    textPane.getDocument().getLength(), text, attr );
            
        } catch ( BadLocationException exc ) {
            exc.printStackTrace();
        }
        
    }
    
    public static void processResultsToExcel( List<TestSetResult> tSetResList, TestSet testSet ) {
        
        try {
            
            InputStream inp = Utils.class.getResourceAsStream( "/template.xlsx" );
            XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook( inp );
            XSSFSheet sheet = workbook.getSheetAt(0);
            
            Row r = sheet.getRow( 0 );
            r.getCell( 0 ).setCellValue( "student" );
            r.getCell( 1 ).setCellValue( "code" );
            int cc = 2;   // current cell
            int cr = 1;   // current row
            
            for ( TestResult tr : tSetResList.get( 0 ).getTestResults() ) {
                r.getCell( cc++ ).setCellValue( tr.getName() );
            }
            r.getCell( cc ).setCellValue( "grade" );
            
            for ( TestSetResult tsr : tSetResList ) {
            
                r = sheet.getRow( cr++ );
                r.getCell( 0 ).setCellValue( tsr.getStudent().getName() );
                r.getCell( 1 ).setCellValue( tsr.getStudent().getCode() );
                cc = 2;
                
                for ( TestResult tr : tsr.getTestResults() ) {

                    String result = "-";

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
                    }
                    
                    r.getCell( cc++ ).setCellValue( result );

                }

                r.getCell( cc ).setCellValue( tsr.getGrade() );

            }
            
            JFileChooser jfc = new JFileChooser( new File( Utils.getPref( "saveSheetPath" ) ) );
            jfc.setDialogTitle( "Save test results" );
            jfc.setMultiSelectionEnabled( false );
            jfc.setFileSelectionMode( JFileChooser.FILES_ONLY );
            jfc.removeChoosableFileFilter( jfc.getFileFilter() );
            jfc.setFileFilter( new FileNameExtensionFilter( "MS Excel File" , "xlsx" ) );
            jfc.setSelectedFile( new File( testSet.getDescription() + ".xlsx" ) );
            
            if ( jfc.showSaveDialog( null ) == JFileChooser.APPROVE_OPTION ) {
                
                File f = jfc.getSelectedFile();
                boolean save = true;
                
                if ( f.exists() ) {
                    if ( JOptionPane.showConfirmDialog( null, 
                            "Do you whant to overwrite the existing file?", 
                            "Confirm", 
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
                    }
                }
            }
            
        } catch ( IOException | InvalidFormatException exc ) {
            exc.printStackTrace();
        }
        
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
        
        // trim end
        
        
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

}
