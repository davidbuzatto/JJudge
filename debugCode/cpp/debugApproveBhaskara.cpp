#include <iostream>
#include <cmath>
#include <iomanip>

using namespace std;

int main() {

    float a;
    float b;
    float c;
    float delta;
    float x1;
    float x2;

    cout << "a: ";
    cin >> a;
    cout << "b: ";
    cin >> b;
    cout << "c: ";
    cin >> c;

    cout << fixed << setprecision( 2 );

    if ( a == 0 ) {
        cout << "the second degree equation does not exist!";
    } else {
        delta = b*b - (4 * a * c);
        if ( delta < 0 ) {
            cout << "Delta: " << delta << endl << "S = {}";
        } else {
            x1 = ( -b + sqrt( delta ) ) / 2 * a;
            x2 = ( -b - sqrt( delta ) ) / 2 * a;
            if ( delta == 0 ) {
                cout << "Delta: " << delta << endl << "S = {" << x1 << "}";
            } else {
                if ( x1 <= x2 ) {
                    cout << "Delta: " << delta << endl << "S = {" << x1 << ", " << x2 << "}";
                } else {
                    cout << "Delta: " << delta << endl << "S = {" << x2 << ", " << x1 << "}";
                }
            }
        }
    }

    return 0;

}
