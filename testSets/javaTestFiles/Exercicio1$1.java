import aesd.ds.implementations.linear.*;
import aesd.ds.interfaces.*;
import java.io.*;
import java.math.*;
import java.util.Scanner;

public class Exercicio1$1 {

    public static void main( String[] args ) {
        
        Queue<Integer> q = new ResizingArrayQueue<>();
        q.enqueue( 10 );
        System.out.println( q.dequeue() );
        
        Scanner scan = new Scanner( System.in );

        System.out.printf( "Ola Mundo!" );

    }

}
