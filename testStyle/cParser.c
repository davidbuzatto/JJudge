int x( void );
int x( int a );
int x( int a, int b );

int a;
int a = 1;
int a, b;

int main( void ) {
    int a;
    int a = 1;
    int a, b;
    
    if ( x ) {
        printf( "a" );
    }
    
    if ( x )
        printf( "a" );
    
    if ( x ) {
        printf( "a" );
    } else {
        printf( "a" );
    }
    
    if ( x ) {
        printf( "a" );
    } else if ( x )
        printf( "a" );
    else printf( "a" );
    
    
    if ( x )
        printf( "a" );
    else
        printf( "a" );
    
    
    switch ( x ) {
        case 1: printf( "x" );
    }
        
    switch ( x )
        case 1: printf( "x" );
    
    
    for ( int a = 0; a < 10; a++ ) {
        printf( "a" );
    }
     
    for ( int a = 0; a < 10; a++ )
        printf( "a" );
    
    while ( a ) {
        printf( "a" );
    }
    
    while ( a )
        printf( "a" );
    
    do {
        printf( "a" );
    } while ( x );
    
    do
        printf( "a" );
    while ( x ) ;
    
    
    /*if ( x )
        printf( "a" );
    else
        printf( "a" );
    
    if ( x )
        printf( "a" );
    else if ( x ) {
        printf( "a" );
    } else {
        printf( "a" );
    }
    
    if ( x ) {
        printf( "a" );
    } else if ( x )
        printf( "a" );
    else {
        printf( "a" );
    }*/
    
    
    
}

int x() {
    return 0;
}

int x( void ) {
    return 0;
}

int x( int a ) {
    return 0;
}

int x( int a, int b ) {
    return 0;
}

int x() {
    int a;
    int a = 1;
    int a, b;
}

/*if ( x )
if ( x ) {
if ( x ){
        
else
} else
}else
else {
} else {
}else {
else{
} else{
}else{

else if ( x )
} else if ( x )
}else if ( x )
    
else if ( x ) {
} else if ( x ) {
}else if ( x ) {
    
else if ( x ){
} else if ( x ){
}else if ( x ){

switch ( x )
switch ( x ) {
switch ( x ){

for ( x )
for ( x ) {
for ( x ){

while ( x )
while ( x ) {
while ( x ){

do
do {
do{*/