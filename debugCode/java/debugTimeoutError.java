
public class debugTimeoutError {

    public static void main( String[] args ) {
        f();
    }

    public static void f() {
        int i = 0;
        for ( ;; ) {
            i = 0;
        }
    }
    
}
