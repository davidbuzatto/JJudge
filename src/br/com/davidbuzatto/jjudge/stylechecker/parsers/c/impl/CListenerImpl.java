package br.com.davidbuzatto.jjudge.stylechecker.parsers.c.impl;

import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CBaseListener;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser.DeclarationContext;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser.StatementContext;
import java.util.ArrayList;
import java.util.List;

/**
 * C language parser listener for style checking.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CListenerImpl extends CBaseListener {

    private List<Integer> multipleDeclarationLines;
    private List<Integer> missingLeftBraceLines;
    
    public CListenerImpl() {
        multipleDeclarationLines = new ArrayList<>();
        missingLeftBraceLines = new ArrayList<>();
    }

    @Override
    public void enterBlockItem( CParser.BlockItemContext ctx ) {
        if ( ctx.declaration() != null ) {
            checkDeclarationContext( ctx.declaration() );
        }
    }

    @Override
    public void enterExternalDeclaration( CParser.ExternalDeclarationContext ctx ) {
        if ( ctx.declaration() != null ) {
            checkDeclarationContext( ctx.declaration() );
        }
    }
    
    private void checkDeclarationContext( DeclarationContext ctx ) {
        CParser.InitDeclaratorListContext ct = ctx.initDeclaratorList();
        if ( ct != null ) {
            if ( ct.children != null && ct.children.size() > 1 ) {
                multipleDeclarationLines.add( ctx.start.getLine() );
            }
        }
    }

    @Override
    public void enterSelectionStatement( CParser.SelectionStatementContext ctx ) {
        for ( StatementContext stmt : ctx.statement() ) {
            if ( stmt.selectionStatement() != null ) {
                // else if (next listener will manage it)
            } else if ( stmt.compoundStatement() == null ) {
                missingLeftBraceLines.add( stmt.start.getLine() );
            }
        }
    }

    @Override
    public void enterIterationStatement( CParser.IterationStatementContext ctx ) {
        if ( ctx.statement().compoundStatement() == null ) {
            missingLeftBraceLines.add( ctx.start.getLine() );
        }
    }
    
    public List<Integer> getMultipleDeclarationLines() {
        return multipleDeclarationLines;
    }

    public List<Integer> getMissingLeftBraceLines() {
        return missingLeftBraceLines;
    }
    
}
