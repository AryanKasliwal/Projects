#include <iostream>
#include <bits/stdc++.h>

using namespace std;

int main(){
    int n;
    cin >> n;
    long long int arr[n];
    for (int i = 0; i < n; i++){
        cin >> arr[i];
    }
    int m = sizeof(arr)/sizeof(arr[0]);
    sort(arr, arr + m);
    int minNaturalNum = 0;
    for (int i = 0; i < n; i++){
        if (arr[0] != 0){
            cout << 0 << "\n";
            break;
        }
        if ((arr[i + 1] - arr[i]) > 1){
            cout << arr[i] + 1 << "\n";
            break;
        }
    }
}