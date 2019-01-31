
import java.util.Scanner;

public class debugApproveSum {

    public static void main( String[] args ) {

        int n1;
        int n2;
        int r;

        Scanner s = new Scanner( System.in );

        System.out.print( "n1: " );
        n1 = s.nextInt();

        System.out.print( "n2: " );
        n2 = s.nextInt();

        r = n1 + n2;

        System.out.printf( "output: %d", r );

    }

}
