#include <algorithm>
#include <vector>
#include <iostream>
#include <queue>

using namespace std;

priority_queue<int, vector<int>, greater<int>> getAns(priority_queue<int, vector<int>, greater<int>> array) {
    while(true) {
        if(array.size() == 1){
            break;
        }
        int chhota = array.top();
        array.pop();
        int bada = array.top();
        array.pop();
        if((2*chhota) <= array.top()) {
            array.push(bada);
        }
        else {
            array.push(2*chhota);
        }
    }
    return array;
}

priority_queue<int, vector<int>, greater<int>> givenFunction(priority_queue<int, vector<int>, greater<int>> array, int n, int m, int seed) {
    unsigned x = seed;
    for(int i = 1; i <= n; i++) {
        x ^= x << 13;
        x ^= x >> 17;
        x ^= x << 5;
        array.push(x % m + 1);
    }
    return array;
}


int main() {
    int n, m, seed;
    cin >> n >> m >> seed;
    priority_queue<int, vector<int>, greater<int>> array;
    array = givenFunction(array, n, m, seed);
    array = getAns(array);
    cout << array.top() << endl;
}
