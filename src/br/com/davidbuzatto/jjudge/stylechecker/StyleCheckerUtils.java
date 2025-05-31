package br.com.davidbuzatto.jjudge.stylechecker;

import br.com.davidbuzatto.jjudge.testsets.Student;
import br.com.davidbuzatto.jjudge.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerUtils {
    
    private static final String[] TYPE_PREFIXES = { "int", "float", "char", "bool" };
    private static final String[] STATEMENT_PREFIXES = { "if", "else", "switch", "for", "while", "do" };
    
    public static void main( String[] args ) throws IOException {
        List<File> files = new ArrayList<>();
        files.add( new File( "testStyle/c.jjd" ) );
        files.add( new File( "testStyle/c.c" ) );
        runStyleChecker( files );
    }
    
    public static void runStyleChecker( List<File> files ) throws IOException {
        
        List<File> standaloneFiles = new ArrayList<>();
        List<File> destDirs = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        
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
        
        Map<String, List<File>> studentsFileMap = new LinkedHashMap<>();
        
        for ( File destDir : destDirs ) {
            Student s = Utils.loadStudent( destDir.getAbsolutePath() );
            List<File> pkgFiles = new ArrayList<>();
            studentsFileMap.put( s.getName(), pkgFiles );
            students.add( s );
            for ( File f : destDir.listFiles() ) {
                if ( f.isFile() && !f.getName().equals( "student.json" ) ) {
                    pkgFiles.add( f );
                }
            }
        }
        
        for ( File f : standaloneFiles ) {
            checkStyle( f );
        }
        
        for ( Map.Entry<String, List<File>> e : studentsFileMap.entrySet() ) {
            System.out.println( e.getKey() );
            for ( File f : e.getValue() ) {
                checkStyle( f );
            }
        }
        
        // cleanup
        for ( File destDir : destDirs ) {
            FileUtils.deleteDirectory( destDir );
        }
        
    }
    
    private static void checkStyle( File f ) throws IOException {
        
        System.out.println( "Testing: " + f );
        String fileData = Files.readString( f.toPath() );
        
        int lineNumber = 1;
        for ( String line : fileData.split( "\n" ) ) {
            if ( !line.trim().isEmpty() ) {
                StringBuilder sb = new StringBuilder();
                sb.append( lineNumber );
                sb.append( " " );
                sb.append( line.trim() );
                String checkVariableResult = checkVariable( line );
                String checkStatementResult = checkStatement( line );
                if ( checkVariableResult.isEmpty() && checkStatementResult.isEmpty() ) {
                    sb.append( " -> ok." );
                } else {
                    String checkResult = checkVariableResult.isEmpty() ? checkStatementResult : checkVariableResult;
                    sb.append( " -> error: " ).append( checkResult );
                }
                System.out.println( sb );
            }
            lineNumber++;
        }
        
    }
    
    private static String checkVariable( String line ) {
        
        String result = "";
        line = line.trim();
        
        for ( String prefix : TYPE_PREFIXES ) {
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
                    return "mais de uma variável por linha!";
                }
            }
        }
        
        return result;
        
    }
    
    private static String checkStatement( String line ) {
        
        String result = "";
        line = line.trim();
        
        for ( String prefix : STATEMENT_PREFIXES ) {
            if ( line.startsWith( prefix ) || line.startsWith( "}" ) ) {
                String[] tokens = line.split( "\\s+" );
                if ( tokens.length > 1 && !tokens[tokens.length-1].endsWith( "{" ) ) {
                    return "inicie o bloco na mesma linha!";
                } else if ( tokens.length == 1 ) {
                    if ( !tokens[0].endsWith( "{" ) ) {
                        return "inicie o bloco na mesma linha!";
                    }
                }
            }
        }
        
        return result;
        
    }
    
}
