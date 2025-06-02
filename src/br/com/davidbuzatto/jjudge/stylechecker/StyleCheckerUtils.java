package br.com.davidbuzatto.jjudge.stylechecker;

import br.com.davidbuzatto.jjudge.gui.StyleCheckerResultFrame;
import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerUtils {
    
    private static ResourceBundle bundle = Utils.bundle;
    private static final String[] C_CPP_TYPE_PREFIXES = { "int", "float", "char", "bool" };
    private static final String[] C_CPP_STATEMENT_PREFIXES = { "if", "else", "switch", "for", "while", "do" };
    private static final String[] JAVA_TYPE_PREFIXES = { "int", "double", "char", "boolean" };
    private static final String[] JAVA_STATEMENT_PREFIXES = { "if", "else", "switch", "for", "while", "do" };
    
    public static void main( String[] args ) throws IOException {
        List<File> files = new ArrayList<>();
        files.add( new File( "testStyle/c.jjd" ) );
        files.add( new File( "testStyle/c.c" ) );
        StyleCheckerTestResult r = runStyleChecker( files, StyleCheckerLanguage.C );
        //new TestFrame( r );
        JFrame f = new StyleCheckerResultFrame( r, Color.WHITE );
        f.setVisible( true );
    }
    
    public static StyleCheckerTestResult runStyleChecker( List<File> files, StyleCheckerLanguage language ) throws IOException {
        
        StyleCheckerTestResult testResult = new StyleCheckerTestResult();
        
        List<File> standaloneFiles = new ArrayList<>();
        List<File> destDirs = new ArrayList<>();
        
        for ( File f : files ) {
            String dirName = f.getName();
            if ( dirName.endsWith( ".jjd" ) ) {
                dirName = dirName.substring( 0, dirName.lastIndexOf( ".jjd" ) );
                File destDir = new File( f.getParentFile().getAbsolutePath() + File.separator + dirName + "-jjd-temp" );
                destDirs.add( destDir );
                if ( destDir.exists() ) {
                    FileUtils.deleteDirectory( destDir );
                }
                Utils.completeUnzip( f, destDir );
            } else {
                standaloneFiles.add( f );
            }
        }
        
        Map<Student, List<File>> studentsFileMap = new LinkedHashMap<>();
        
        for ( File destDir : destDirs ) {
            Student s = Utils.loadStudent( destDir.getAbsolutePath() );
            List<File> pkgFiles = new ArrayList<>();
            studentsFileMap.put( s, pkgFiles );
            for ( File f : destDir.listFiles() ) {
                if ( f.isFile() && !f.getName().equals( "student.json" ) ) {
                    pkgFiles.add( f );
                }
            }
        }
        
        for ( File f : standaloneFiles ) {
            switch ( language ) {
                case C:
                case CPP:
                    testResult.addStandaloneResult( checkStyleCCPP( f, null ) );
                    break;
                case JAVA:
                    testResult.addStandaloneResult( checkStyleJava( f, null ) );
                    break;
                default:
                    break;
            }
        }
        
        for ( Map.Entry<Student, List<File>> e : studentsFileMap.entrySet() ) {
            for ( File f : e.getValue() ) {
                switch ( language ) {
                    case C:
                    case CPP:
                        testResult.addStudentResult( e.getKey(), checkStyleCCPP( f, e.getKey() ) );
                        break;
                    case JAVA:
                        testResult.addStudentResult( e.getKey(), checkStyleJava( f, e.getKey() ) );
                        break;
                    default:
                        break;
                }
            }
        }
        
        // cleanup
        for ( File destDir : destDirs ) {
            FileUtils.deleteDirectory( destDir );
        }
        
        return testResult;
        
    }
    
    private static StyleCheckerResult checkStyleCCPP( File f, Student s ) throws IOException {
        
        StyleCheckerResult result = new StyleCheckerResult( f, s );
        try ( Scanner scan = new Scanner( f, StandardCharsets.UTF_8 ) ) {
            
            int lineNumber = 1;
            
            while ( scan.hasNextLine() ) {
                String line = scan.nextLine();
                String checkVariableResult = checkVariableCCPP( line );
                String checkStatementResult = checkStatementCCPP( line );
                if ( checkVariableResult.isEmpty() && checkStatementResult.isEmpty() ) {
                    result.addItem( lineNumber, line, null );
                } else {
                    String checkResult = checkVariableResult.isEmpty() ? checkStatementResult : checkVariableResult;
                    result.addItem( lineNumber, line, checkResult );
                }
                lineNumber++;
            }
            
        }
        
        return result;
        
    }
    
    private static String checkVariableCCPP( String line ) {
        
        String result = "";
        line = line.trim();
        
        for ( String prefix : C_CPP_TYPE_PREFIXES ) {
            if ( line.startsWith( prefix ) ) {
                String[] tokens = line.split( "\\s+" );
                int commaCounter = 0;
                int parenthesisCounter = 0;
                if ( tokens[tokens.length-1].contains( ";" ) ) {
                    for ( int i = 1; i < tokens.length; i++ ) {
                        String token = tokens[i];
                        if ( token.contains( "," ) ) {
                            commaCounter++;
                        }
                        if ( token.contains( "(" ) || token.contains( ")" ) ) {
                            parenthesisCounter++;
                        }
                    }
                }
                if ( commaCounter > 0 && parenthesisCounter == 0 ) {
                    return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.multipleVar" );
                }
            }
        }
        
        return result;
        
    }
    
    private static String checkStatementCCPP( String line ) {
        
        String result = "";
        line = line.trim();
        
        for ( String prefix : C_CPP_STATEMENT_PREFIXES ) {
            if ( line.startsWith( prefix ) || line.startsWith( "}" ) ) {
                String[] tokens = line.split( "\\s+" );
                if ( tokens.length > 1 && !tokens[tokens.length-1].endsWith( "{" ) ) {
                    return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" );
                } else if ( tokens.length == 1 ) {
                    if ( !tokens[0].endsWith( "{" ) && tokens.length > 1 ) {
                        return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" );
                    }
                }
            }
        }
        
        return result;
        
    }

    private static StyleCheckerResult checkStyleJava( File f, Student s ) throws IOException {
        
        StyleCheckerResult result = new StyleCheckerResult( f, s );
        try ( Scanner scan = new Scanner( f, StandardCharsets.UTF_8 ) ) {
            
            int lineNumber = 1;
            
            while ( scan.hasNextLine() ) {
                String line = scan.nextLine();
                String checkVariableResult = checkVariableJava( line );
                String checkStatementResult = checkStatementJava( line );
                if ( checkVariableResult.isEmpty() && checkStatementResult.isEmpty() ) {
                    result.addItem( lineNumber, line, null );
                } else {
                    String checkResult = checkVariableResult.isEmpty() ? checkStatementResult : checkVariableResult;
                    result.addItem( lineNumber, line, checkResult );
                }
                lineNumber++;
            }
            
        }
        
        return result;
        
    }
    
    private static String checkVariableJava( String line ) {
        
        String result = "";
        line = line.trim();
        
        for ( String prefix : JAVA_TYPE_PREFIXES ) {
            if ( line.startsWith( prefix ) ) {
                String[] tokens = line.split( "\\s+" );
                int commaCounter = 0;
                int parenthesisCounter = 0;
                if ( tokens[tokens.length-1].contains( ";" ) ) {
                    for ( int i = 1; i < tokens.length; i++ ) {
                        String token = tokens[i];
                        if ( token.contains( "," ) ) {
                            commaCounter++;
                        }
                        if ( token.contains( "(" ) || token.contains( ")" ) ) {
                            parenthesisCounter++;
                        }
                    }
                }
                if ( commaCounter > 0 && parenthesisCounter == 0 ) {
                    return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.multipleVar" );
                }
            }
        }
        
        return result;
        
    }
    
    private static String checkStatementJava( String line ) {
        
        String result = "";
        line = line.trim();
        
        for ( String prefix : JAVA_STATEMENT_PREFIXES ) {
            if ( line.startsWith( prefix ) || line.startsWith( "}" ) ) {
                String[] tokens = line.split( "\\s+" );
                if ( tokens.length > 1 && !tokens[tokens.length-1].endsWith( "{" ) ) {
                    return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" );
                } else if ( tokens.length == 1 ) {
                    if ( !tokens[0].endsWith( "{" ) ) {
                        return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" );
                    }
                }
            }
        }
        
        return result;
        
    }
    
}
