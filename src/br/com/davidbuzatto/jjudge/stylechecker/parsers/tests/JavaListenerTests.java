package br.com.davidbuzatto.jjudge.stylechecker.parsers.tests;

import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaLexer;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.impl.JavaListenerImpl;
import java.io.File;
import java.util.Scanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class JavaListenerTests {
    
    public static void main( String[] args ) throws Exception {
        
        StringBuilder code = new StringBuilder();
        File f = new File( "testStyle/JavaParserTest.java" );
        Scanner s = new Scanner( f );
        
        while ( s.hasNextLine() ) {
            code.append( s.nextLine() ).append( "\n" );
        }
        
        s.close();
        
        analyse( code.toString() );
        
    }
    
    public static void analyse( String code ) {
        
        JavaLexer lexer = new JavaLexer(
                CharStreams.fromString( code ) );
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        JavaParser parser = new JavaParser( tokens );
        ParseTree tree = parser.compilationUnit();
        
        JavaListenerImpl listener = new JavaListenerImpl();
        ParseTreeWalker.DEFAULT.walk( listener, tree );
        
        System.out.println( listener.getMultipleDeclarationLines() );
        System.out.println( listener.getMissingLeftBraceLines() );
        
    }
    
}
