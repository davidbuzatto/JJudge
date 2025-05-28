#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void) {
    double numero;
    double absoluto;

    printf("Numero: ");
    scanf("%lf", &numero);

    absoluto = fabs(numero);

    printf("Valor absoluto: %.2f\n", absoluto);

    return 0;
}