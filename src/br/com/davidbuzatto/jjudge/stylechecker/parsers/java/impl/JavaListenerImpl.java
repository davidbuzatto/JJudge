package br.com.davidbuzatto.jjudge.stylechecker.parsers.java.impl;

import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaLexer;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.java.JavaParserBaseListener;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Java language parser listener for style checking.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class JavaListenerImpl extends JavaParserBaseListener {
    
    private Set<Integer> multipleDeclarationLines;
    private Set<Integer> missingLeftBraceLines;
    
    public JavaListenerImpl() {
        multipleDeclarationLines = new TreeSet<>();
        missingLeftBraceLines = new TreeSet<>();
    }
    
    @Override
    public void enterVariableDeclarators( JavaParser.VariableDeclaratorsContext ctx ) {
        if ( ctx.variableDeclarator() != null && ctx.variableDeclarator().size() > 1 ) {
            multipleDeclarationLines.add( ctx.start.getLine() );
        }
    }

    @Override
    public void enterStatement( JavaParser.StatementContext ctx ) {
            
        if ( ctx.IF() != null ) {
            checkIfStatementHasBlock( ctx.statement( 0 ), ctx.IF().getSymbol().getLine() );
        }

        if ( ctx.ELSE() != null ) {
            checkIfStatementHasBlock( ctx.statement( 1 ), ctx.ELSE().getSymbol().getLine() );
        }

        if ( ctx.FOR() != null ) {
            checkIfStatementHasBlock( ctx.statement( 0 ), ctx.FOR().getSymbol().getLine() );
        }

        if ( ctx.DO() == null && ctx.WHILE() != null ) {
            checkIfStatementHasBlock( ctx.statement( 0 ), ctx.WHILE().getSymbol().getLine() );
        }

        if ( ctx.DO() != null ) {
            checkIfStatementHasBlock( ctx.statement( 0 ), ctx.DO().getSymbol().getLine() );
        }
            
    }
    
    private void checkIfStatementHasBlock( JavaParser.StatementContext ctx, int line ) {
        
        // basta para o if, não para o else if
        if ( ctx.block() == null ) {
            
            // teste para o else if
            if ( !ctx.statement().isEmpty() ) {
                JavaParser.StatementContext sc = ctx.statement().getFirst();
                if ( ctx.statement().getFirst().start.getType() == JavaLexer.LBRACE ) {
                    return;
                }
            }
            
            missingLeftBraceLines.add( line );
            
        }
        
    }
    
    public Set<Integer> getMultipleDeclarationLines() {
        return multipleDeclarationLines;
    }

    public Set<Integer> getMissingLeftBraceLines() {
        return missingLeftBraceLines;
    }
    
}
