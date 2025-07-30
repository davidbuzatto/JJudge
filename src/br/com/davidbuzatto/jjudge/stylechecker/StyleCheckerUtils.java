package br.com.davidbuzatto.jjudge.stylechecker;

import br.com.davidbuzatto.jjudge.gui.StyleCheckerResultFrame;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CLexer;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.impl.CListenerImpl;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaLexer;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.impl.JavaListenerImpl;
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
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerUtils {
    
    private static ResourceBundle bundle = Utils.bundle;
    
    public static void main( String[] args ) throws IOException {
        List<File> files = new ArrayList<>();
        files.add( new File( "testStyle/c.jjd" ) );
        files.add( new File( "testStyle/c.c" ) );
        StyleCheckerTestResult r = runStyleChecker( files, StyleCheckerLanguage.C );
        JFrame f = new StyleCheckerResultFrame( r, Color.WHITE, false );
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
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        
        try ( Scanner scan = new Scanner( f, StandardCharsets.UTF_8 ) ) {
            while ( scan.hasNextLine() ) {
                String line = scan.nextLine();
                sb.append( line ).append( "\n" );
                lines.add( line );
            }
        }
        
        CListenerImpl listener = parseCCode( sb.toString(), true );
        
        int lineNumber = 1;
        for ( String line : lines ) {
            
            if ( listener.getMultipleDeclarationLines().contains( lineNumber ) ) {
                result.addItem( lineNumber, line, bundle.getString( "StyleCheckerResultFrame.sourceCodeError.multipleVar" ) );
            } else if ( listener.getMissingLeftBraceLines().contains( lineNumber ) ) {
                result.addItem( lineNumber, line, bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" ) );
            } else {
                result.addItem( lineNumber, line, null );
            }
            lineNumber++;
        }
        
        return result;
        
    }
    
    private static StyleCheckerResult checkStyleJava( File f, Student s ) throws IOException {
        
        StyleCheckerResult result = new StyleCheckerResult( f, s );
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        
        try ( Scanner scan = new Scanner( f, StandardCharsets.UTF_8 ) ) {
            while ( scan.hasNextLine() ) {
                String line = scan.nextLine();
                sb.append( line ).append( "\n" );
                lines.add( line );
            }
        }
        
        JavaListenerImpl listener = parseJavaCode( sb.toString(), true );
        
        int lineNumber = 1;
        for ( String line : lines ) {
            
            if ( listener.getMultipleDeclarationLines().contains( lineNumber ) ) {
                result.addItem( lineNumber, line, bundle.getString( "StyleCheckerResultFrame.sourceCodeError.multipleVar" ) );
            } else if ( listener.getMissingLeftBraceLines().contains( lineNumber ) ) {
                result.addItem( lineNumber, line, bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" ) );
            } else {
                result.addItem( lineNumber, line, null );
            }
            lineNumber++;
        }
        
        return result;
        
    }
    
    private static StyleCheckerResult checkStyleCCPPOld( File f, Student s ) throws IOException {
        
        StyleCheckerResult result = new StyleCheckerResult( f, s );
        
        try ( Scanner scan = new Scanner( f, StandardCharsets.UTF_8 ) ) {
            
            int lineNumber = 1;
            
            while ( scan.hasNextLine() ) {
                String line = scan.nextLine();
                String checkVariableResult = checkVariableCCPPOld( line );
                String checkStatementResult = checkStatementCCPPOld( line );
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
    
    private static String checkVariableCCPPOld( String line ) {
        
        String result = "";
        line = line.trim();
        
        List<StyleCheckerToken> tokens = getTokens( new StyleCheckerTokenizer( line ) );
        
        int commaCounter = 0;
        int parenthesisCounter = 0;
        
        for ( StyleCheckerToken t : tokens ) {
            if ( t.type() == StyleCheckerTokenType.COMMA ) {
                commaCounter++;
            }
            if ( t.type() == StyleCheckerTokenType.LEFT_PAREN || t.type() == StyleCheckerTokenType.RIGHT_PAREN ) {
                parenthesisCounter++;
            }
        }
        
        if ( commaCounter > 0 && parenthesisCounter == 0 ) {
            return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.multipleVar" );
        }
        
        return result;
        
    }
    
    private static String checkStatementCCPPOld( String line ) {
        
        String result = "";
        line = line.trim();
        
        List<StyleCheckerToken> tokens = getTokens( new StyleCheckerTokenizer( line ) );
        
        // start -> something -> EOF (minimum)
        if ( tokens.size() > 2 ) {
            
            int leftCurlyExpectedPos = tokens.size() - 2;
            
            if ( StyleCheckerTokenizer.isStatemenentReservedWord( tokens.get( 0 ) ) && tokens.get( leftCurlyExpectedPos ).type() != StyleCheckerTokenType.LEFT_CURLY ) {
                return bundle.getString( "StyleCheckerResultFrame.sourceCodeError.blockStart" );
            }
            
        }
        
        return result;
        
    }

    private static StyleCheckerResult checkStyleJavaOld( File f, Student s ) throws IOException {
        return checkStyleCCPPOld( f, s );
    }
    
    private static List<StyleCheckerToken> getTokens( StyleCheckerTokenizer st ) {
        
        List<StyleCheckerToken> tokens = new ArrayList<>();
        
        while ( true ) {
            
            st.nextToken();
            StyleCheckerToken token = st.getToken();
            tokens.add( token );
            
            if ( token.type() == StyleCheckerTokenType.EOF ) {
                break;
            }
            
        }
        
        return tokens;
        
    }
    
    private static CListenerImpl parseCCode( String code, boolean disableErrorReporting ) {
        
        CLexer lexer = new CLexer(
                CharStreams.fromString( code ) );
        
        if ( disableErrorReporting ) {
            lexer.removeErrorListener( ConsoleErrorListener.INSTANCE );
        }
        
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        
        CParser parser = new CParser( tokens );
        if ( disableErrorReporting ) {
            parser.removeErrorListener( ConsoleErrorListener.INSTANCE );
        }
        
        ParseTree tree = parser.compilationUnit();
        
        CListenerImpl listener = new CListenerImpl();
        ParseTreeWalker.DEFAULT.walk( listener, tree );
        
        return listener;
        
    }
    
    private static JavaListenerImpl parseJavaCode( String code, boolean disableErrorReporting ) {
        
        JavaLexer lexer = new JavaLexer(
                CharStreams.fromString( code ) );
        
        if ( disableErrorReporting ) {
            lexer.removeErrorListener( ConsoleErrorListener.INSTANCE );
        }
        
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        
        JavaParser parser = new JavaParser( tokens );
        if ( disableErrorReporting ) {
            parser.removeErrorListener( ConsoleErrorListener.INSTANCE );
        }
        
        ParseTree tree = parser.compilationUnit();
        
        JavaListenerImpl listener = new JavaListenerImpl();
        ParseTreeWalker.DEFAULT.walk( listener, tree );
        
        return listener;
        
    }
    
}
