
public class debugInfiniteRecursion {

    public static void main( String[] args ) {
        f();
    }

    public static void f() {
        f();
    }
    
}
