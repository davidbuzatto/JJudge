package br.com.davidbuzatto.jjudge.stylechecker;

/**
 * A simple tokenizer for source code written in C, C++ or Java.
 * 
 * It tokenizes only the data that I need to make the style checker work.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class StyleCheckerTokenizer {
    
    private static final char EOF_CHAR = '\0';
    private static final StyleCheckerToken EOF_TOKEN = new StyleCheckerToken( "", -1, -1, StyleCheckerTokenType.EOF );
    
    private static final StyleCheckerTokenType[] TYPE_RESERVED_WORDS = {
        StyleCheckerTokenType.CHAR_RW,
        StyleCheckerTokenType.BYTE_RW,
        StyleCheckerTokenType.SHORT_RW,
        StyleCheckerTokenType.INT_RW,
        StyleCheckerTokenType.LONG_RW,
        StyleCheckerTokenType.FLOAT_RW,
        StyleCheckerTokenType.DOUBLE_RW,
        StyleCheckerTokenType.BOOL_RW,
        StyleCheckerTokenType.BOOLEAN_RW,
        StyleCheckerTokenType.VOID_RW
    };
    
    private static final StyleCheckerTokenType[] STATEMENT_RESERVED_WORDS = {
        StyleCheckerTokenType.IF_RW,
        StyleCheckerTokenType.ELSE_RW,
        StyleCheckerTokenType.SWITCH_RW,
        StyleCheckerTokenType.FOR_RW,
        StyleCheckerTokenType.WHILE_RW,
        StyleCheckerTokenType.DO_RW
    };
    
    private final String text;
    
    private int currentLine;
    private int currentColumn;
    private int currentChar;
    
    private StyleCheckerToken token;
    
    public static void main( String[] args ) {
        
        StyleCheckerTokenizer st = new StyleCheckerTokenizer(
            """
            int main( void ) {
                int a = 9;
                int x, y = 10;
                if ( c ) {
                    x = a + y;
                }
            }"""
        );
        
        /*while ( true ) {
            st.nextChar();
            char c = st.getChar();
            if ( c == EOF_CHAR ) {
                break;
            }
            if ( c == '\n' ) {
                System.out.printf( "'\\n' (%d, %d)\n", st.getCurrentLine(), st.getCurrentColumn() );
            } else {
                System.out.printf( "'%c' (%d, %d)\n", c, st.getCurrentLine(), st.getCurrentColumn() );
            }
        }*/
        
        System.out.println( st.text );
        
        while ( true ) {
            
            st.nextToken();
            StyleCheckerToken token = st.getToken();
            
            if ( token.type() == StyleCheckerTokenType.EOF ) {
                break;
            }
            
            System.out.println( token );
            
        }
        
    }
    
    public StyleCheckerTokenizer( String text ) {
        this.text = text;
        this.currentLine = 1;
        this.currentColumn = 1;
        this.currentChar = 0;
    }
    
    public void nextToken() {
        
        char c = getChar();
        
        // skip whitespace
        while ( Character.isWhitespace( c ) ) {
            nextChar();
            c = getChar();
        }
        
        switch ( c ) {
            
            case ',':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.COMMA );
                nextChar();
                break;
                
            case ';':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.SEMICOLON );
                nextChar();
                break;
                
            case '/':
                
                nextChar();
                
                if ( getChar() == '/' ) {
                    while ( getChar() != '\n' && getChar() != EOF_CHAR ) {
                        nextChar();
                    }
                    nextToken();
                } else {
                    token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.GENERIC );
                    nextChar();
                }
                break;
                
            case '(':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.LEFT_PAREN );
                nextChar();
                break;
                
            case ')':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.RIGHT_PAREN );
                nextChar();
                break;
                
            case '[':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.LEFT_BRACK );
                nextChar();
                break;
                
            case ']':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.RIGHT_BRACK );
                nextChar();
                break;
                
            case '{':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.LEFT_CURLY );
                nextChar();
                break;
                
            case '}':
                token = new StyleCheckerToken( String.valueOf( c ), currentLine, currentColumn, StyleCheckerTokenType.RIGHT_CURLY );
                nextChar();
                break;
            
            default:
                
                StringBuilder sb = new StringBuilder();
                
                while ( isValidForGeneric( c ) && c != EOF_CHAR ) {
                    sb.append( c );
                    nextChar();
                    c = getChar();
                }
                
                if ( c == EOF_CHAR ) {
                    token = EOF_TOKEN;
                } else {
                    String tokenText = sb.toString();
                    token = new StyleCheckerToken( tokenText, currentLine, currentColumn - tokenText.length(), getTokenType( tokenText ) );
                }
                
                break;
            
        }
        
    }
    
    public StyleCheckerToken getToken() {
        return token;
    }
    
    private void nextChar() {
        if ( currentChar <= text.length() ) {
            currentChar++;
            currentColumn++;
            if ( currentChar - 1 >= 0 && currentChar - 1 < text.length() ) {
                if ( text.charAt( currentChar - 1 ) == '\n' ) {
                    currentLine++;
                    currentColumn = 1;
                }
            }
        }
    }
    
    private char getChar() {
        if ( currentChar < text.length() ) {
            return text.charAt( currentChar );
        }
        return EOF_CHAR;
    }
    
    private boolean isValidForGeneric( char c ) {
        return !Character.isWhitespace( c ) &&
               c != ',' &&
               c != ';' &&
               c != '(' &&
               c != ')' &&
               c != '[' &&
               c != ']' &&
               c != '{' &&
               c != '}';
    }
    
    private StyleCheckerTokenType getTokenType( String text ) {
        
        for ( StyleCheckerTokenType sctt : TYPE_RESERVED_WORDS ) {
            if ( sctt.getText().equals( text ) ) {
                return sctt;
            }
        }
        
        for ( StyleCheckerTokenType sctt : STATEMENT_RESERVED_WORDS ) {
            if ( sctt.getText().equals( text ) ) {
                return sctt;
            }
        }
        
        return StyleCheckerTokenType.GENERIC;
        
    }
    
    public static boolean isTypeReservedWord( StyleCheckerToken token ) {
        for ( StyleCheckerTokenType sctt : TYPE_RESERVED_WORDS ) {
            if ( token.type() == sctt ) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isStatemenentReservedWord( StyleCheckerToken token ) {
        for ( StyleCheckerTokenType sctt : STATEMENT_RESERVED_WORDS ) {
            if ( token.type() == sctt ) {
                return true;
            }
        }
        return false;
    }
    
}
