#include <stdio.h>
#include <stdlib.h>

int main() {

    int n1;
    int n2;
    int r;

    printf( "n1: " );
    scanf( "%d", &n1 );

    printf( "n2: " );
    scanf( "%d", &n2 );

    r = n1 + n2 + 1;

    printf( "output: %d", r );

    return 0;

}
