
import static java.lang.Math.sqrt;
import java.util.Scanner;


public class debugApproveBhaskara {

    public static void main( String[] args ) {

        double a;
        double b;
        double c;
        double delta;
        double x1;
        double x2;

        Scanner s = new Scanner( System.in );
        
        System.out.printf( "a: " );
        a = s.nextDouble();
        System.out.printf( "b: " );
        b = s.nextDouble();
        System.out.printf( "c: " );
        c = s.nextDouble();

        if ( a == 0 ) {
            System.out.printf( "the second degree equation does not exist!" );
        } else {
            delta = b * b - ( 4 * a * c );
            if ( delta < 0 ) {
                System.out.printf( "Delta: %.2f\nS = {}", delta );
            } else {
                x1 = ( -b + sqrt( delta ) ) / 2 * a;
                x2 = ( -b - sqrt( delta ) ) / 2 * a;
                if ( delta == 0 ) {
                    System.out.printf( "Delta: %.2f\nS = {%.2f}", delta, x1 );
                } else {
                    if ( x1 <= x2 ) {
                        System.out.printf( "Delta: %.2f\nS = {%.2f, %.2f}", delta, x1, x2 );
                    } else {
                        System.out.printf( "Delta: %.2f\nS = {%.2f, %.2f}", delta, x2, x1 );
                    }
                }
            }
        }

    }

}
