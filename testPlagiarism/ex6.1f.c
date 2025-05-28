#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){

    float a;
    float b;
    float c;
    float delta;
    float x1, x2;

    printf("a: ");
    scanf("%f", &a);
    printf("b: ");
    scanf("%f", &b);
    printf("c: ");
    scanf("%f", &c);

    delta = b * b - 4 * a * c;

    printf("Delta: %.2f\n", delta);

    if(delta < 0){
        printf("S = {}\n");
    } else if(delta == 0){
        x1 = -b / 2 * a;
        printf("S = {%.2f}", x1);
    } else{
        x1 = (-b + sqrt(delta)) / 2*a;
        x2 = (-b - sqrt(delta)) / 2*a;
        if(x1 > x2){
            float temp = x1;
            x1 = x2;
            x2 = temp;
        }

        printf("S = {%.2lf, %.2lf}\n", x1, x2);
    }

    if(a == 0){
        printf("Nao existe equacao do segundo grau!");
        return 0;
    }


    return 0;
}