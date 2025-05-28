#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
  double numero;
  double maior;
  double menor;
  
  printf("Numero: ");
  scanf("%lf", &numero);

  maior = ceil(numero);
  menor = floor(numero);

  printf("Maior inteiro mais proximo: %.2f\n", maior);
    printf("Menor inteiro mais proximo: %.2f\n", menor);
   return 0; 
}