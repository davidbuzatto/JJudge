#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
    double numero, resultado;

    printf("Numero: ");
    scanf("%lf", &numero);

    if (numero > 0) {
        resultado = sqrt(numero);
        printf("Raiz quadrada de %.2f: %.2f\n", numero, resultado);
    } else {
        resultado = numero * numero;
        printf("Quadrado de %.2f: %.2f\n", numero, resultado);
    }

   return 0; 
}