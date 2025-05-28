#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){

    float num;

    printf("Numero: ");
    scanf("%f",&num);

    printf("Maior inteiro mais proximo: %.2f\n",ceil(num));
    printf("Menor inteiro mais proximo: %.2f", floor(num));

    return 0;
}