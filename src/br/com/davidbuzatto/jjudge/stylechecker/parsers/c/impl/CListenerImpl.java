package br.com.davidbuzatto.jjudge.stylechecker.parsers.c.impl;

import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CBaseListener;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser.DeclarationContext;
import br.com.davidbuzatto.jjudge.stylechecker.parsers.c.CParser.StatementContext;
import java.util.Set;
import java.util.TreeSet;

/**
 * C language parser listener for style checking.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CListenerImpl extends CBaseListener {

    private Set<Integer> multipleDeclarationLines;
    private Set<Integer> missingLeftBraceLines;
    
    public CListenerImpl() {
        multipleDeclarationLines = new TreeSet<>();
        missingLeftBraceLines = new TreeSet<>();
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
                if ( ctx.If() != null ) {
                    missingLeftBraceLines.add( ctx.If().getSymbol().getLine() );
                    if ( ctx.Else() != null ) {
                        missingLeftBraceLines.add( ctx.Else().getSymbol().getLine() );
                    }
                } else if ( ctx.Switch() != null ) {
                    missingLeftBraceLines.add( ctx.Switch().getSymbol().getLine() );
                }
            }
        }
    }

    @Override
    public void enterIterationStatement( CParser.IterationStatementContext ctx ) {
        if ( ctx.statement().compoundStatement() == null ) {
            if ( ctx.For() != null ) {
                missingLeftBraceLines.add( ctx.For().getSymbol().getLine() );
            } else if ( ctx.Do() == null && ctx.While() != null ) {
                missingLeftBraceLines.add( ctx.While().getSymbol().getLine() );
            } else if ( ctx.Do() != null ) {
                missingLeftBraceLines.add( ctx.Do().getSymbol().getLine() );
            }
        }
    }
    
    public Set<Integer> getMultipleDeclarationLines() {
        return multipleDeclarationLines;
    }

    public Set<Integer> getMissingLeftBraceLines() {
        return missingLeftBraceLines;
    }
    
}
