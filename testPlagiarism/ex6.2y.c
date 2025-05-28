#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
    double base;
    double expoente;
    double resultado;

    printf("Base: ");
    scanf("%lf", &base);

    printf("Expoente: ");
    scanf("%lf", &expoente);

    resultado = pow(base, expoente);

    printf("%.2f ^ %.2f = %.2f\n", base, expoente, resultado);
    
   return 0; 
}