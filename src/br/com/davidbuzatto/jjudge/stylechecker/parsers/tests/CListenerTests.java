package br.com.davidbuzatto.jjudge.stylechecker.parsers.tests;

import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CLexer;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.impl.CListenerImpl;
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
public class CListenerTests {
    
    public static void main( String[] args ) throws Exception {
        
        StringBuilder code = new StringBuilder();
        File f = new File( "testStyle/cParser.c" );
        Scanner s = new Scanner( f );
        
        while ( s.hasNextLine() ) {
            code.append( s.nextLine() ).append( "\n" );
        }
        
        s.close();
        
        analyse( code.toString() );
        
    }
    
    public static void analyse( String code ) {
        
        CLexer lexer = new CLexer(
                CharStreams.fromString( code ) );
        CommonTokenStream tokens = new CommonTokenStream( lexer );
        CParser parser = new CParser( tokens );
        ParseTree tree = parser.compilationUnit();
        
        CListenerImpl listener = new CListenerImpl();
        ParseTreeWalker.DEFAULT.walk( listener, tree );
        
        System.out.println( listener.getMultipleDeclarationLines() );
        System.out.println( listener.getMissingLeftBraceLines() );
        
    }
    
}
