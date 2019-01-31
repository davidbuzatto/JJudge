# -*- coding: utf-8 -*-

import math 

print( "a: " )
a = input()
print( "b: " )
b = input()
print( "c: " )
c = input()

if a == 0:
    print( "the second degree equation does not exist!" )
else:
    delta = b*b - (4 * a * c)
    if delta < 0:
        print( "Delta: {:.2f}\nS = {{}}".format( delta ) )
    else:
        x1 = ( -b + math.sqrt( delta ) ) / 2 * a
        x2 = ( -b - math.sqrt( delta ) ) / 2 * a
        if delta == 0:
            print( "Delta: {:.2f}\nS = {{{:.2f}}}".format( delta, x1 ) )
        else:
            if x1 <= x2:
                print( "Delta: {:.2f}\nS = {{{:.2f}, {:.2f}}}".format( delta, x1, x2 ) )
            else:
                print( "Delta: {:.2f}\nS = {{{:.2f}, {:.2f}}}".format( delta, x2, x1 ) )
