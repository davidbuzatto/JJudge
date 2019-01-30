#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main() {

    float a;
    float b;
    float c;
    float delta;
    float x1;
    float x2;
    
    printf( "a: " );
    scanf( "%f", &a );
    printf( "b: " );
    scanf( "%f", &b );
    printf( "c: " );
    scanf( "%f", &c );
    
    if ( a == 0 ) {
        printf( "the second degree equation does not exist!" );
    } else {
        delta = b*b - (4 * a * c);
        if ( delta < 0 ) {
            printf( "Delta: %.2f\nS = {}", delta );
        } else {
            x1 = ( -b + sqrt( delta ) ) / 2 * a;
            x2 = ( -b - sqrt( delta ) ) / 2 * a;
            if ( delta == 0 ) {
                printf( "Delta: %.2f\nS = {%.2f}", delta, x1 );
            } else {
                if ( x1 <= x2 ) {
                    printf( "Delta: %.2f\nS = {%.2f, %.2f}", delta, x1, x2 );
                } else {
                    printf( "Delta: %.2f\nS = {%.2f, %.2f}", delta, x2, x1 );
                }
            }
        }
    }

    return 0;

}
