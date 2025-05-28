#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){

    float a;
    float b;
    float c;
    float del;
    float x1;
    float x2;

    printf("a: ");
    scanf("%f",&a);

    printf("b: ");
    scanf("%f",&b);

    printf("c: ");
    scanf("%f",&c);

    if( a != 0){//primeira coisa a verificar pq se a for 0 nao existe equacao de 2 grau
    
    del= b * b -( 4 * a * c);
//podereia usar pow(b, 2) -(4 * a * c)
        printf("Delta: %.2f\n",del);
        if(del < 0){
            printf("S = {}");
        }else if( del == 0){
         x1 = -b / (2*a);
         printf("S = {%.2f}", x1);;
        }else{
        x1 = (-b + sqrt(del)) / (2 * a);
        x2 = (-b -sqrt(del)) / (2 * a);

        if ( x1 < x2){
            printf("S = {%.2f, %.2f}", x1, x2);
        }else{
            printf("S = {%.2f, %.2f}", x2, x1);
        }
        }
    
    }else{
        printf("Nao existe equacao do segundo grau!");
    }


    return 0;
}