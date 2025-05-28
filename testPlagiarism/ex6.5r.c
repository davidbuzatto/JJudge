#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
    float n;

    printf("Numero: ");
    scanf("%f", &n);

    if(n > 0){
        printf("Raiz quadrada de %.2f: %.2f", n, sqrt(n));

    }else{
        printf("Quadrado de %.2f: %.2f", n, n*n);
    }

    return 0;
}