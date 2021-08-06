#include <iostream>

using namespace std;

long long int arr[10000000] = {0};

void generateArray(int * arr, int n, int m, int seed){
    unsigned x = seed;
    for(int i = 1; i <= n; i++){
        x ^= x << 13;
        x ^= x >> 17;
        x ^= x << 5;
        arr[i] = x % m + 1;
    }
}

int main(){
    long long int n, m, seed;
    cin >> n >> m >> seed;
}