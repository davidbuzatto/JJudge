#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
    float b;
    float exp;

    printf("Base: ");
    scanf("%f", &b);

    printf("Expoente: ");
    scanf("%f", &exp);

    printf("%.2f ^ %.2f = %.2f", b, exp, pow(b,exp));

    return 0;
}