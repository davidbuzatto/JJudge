package br.com.davidbuzatto.jjudge.stylechecker;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public enum StyleCheckerTokenType {
    
    EOF,
    
    CHAR_RW( "char" ),
    BYTE_RW( "byte" ),
    SHORT_RW( "short" ),
    INT_RW( "int" ),
    LONG_RW( "long" ),
    FLOAT_RW( "float" ),
    DOUBLE_RW( "double" ),
    BOOL_RW( "bool" ),
    BOOLEAN_RW( "boolean" ),
    VOID_RW( "void" ),
    
    IF_RW( "if" ),
    ELSE_RW( "else" ),
    SWITCH_RW( "switch" ),
    FOR_RW( "for" ),
    WHILE_RW( "while" ),
    DO_RW( "do" ),
    
    COMMA,
    SEMICOLON,
    
    LEFT_PAREN,
    LEFT_BRACK,
    LEFT_CURLY,
    RIGHT_PAREN,
    RIGHT_BRACK,
    RIGHT_CURLY,
    
    GENERIC;
    
    private final String text;
    
    StyleCheckerTokenType() {
        this.text = "";
    }
    
    StyleCheckerTokenType( String text ) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
}
