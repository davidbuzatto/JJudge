#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){

    float n;

    printf("Numero: ");
    scanf("%f", &n);

    if(n > 0){
    printf("Raiz quadrada de %.2f: %.2f", n, sqrt(n));
    }

    if(n < 0){
        printf("Quadrado de %.2f: %.2f", n, pow(n, 2));
    }

    return 0;
}