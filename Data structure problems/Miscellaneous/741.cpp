#include<iostream>
#include<vector>
#include<algorithm>
#include<cmath>
#include<bitset>

using namespace std;

int rFlip(char arr[100][10], int i, int c) {
    int tails = 0, heads = 0, j = 0;
    while (j < c) {
        if (arr[i][j] == '0'){
            tails++;   
        }
        else{
            heads++;
        }
        j++;
    }
    if (heads >= tails){
        return heads;
    }
    else{
        return tails;
    }
}

void cFlip(char arr[100][10], int r, int j) {
    int i = 0;
    while(i < r) {
        if (arr[i][j] == '0'){
            arr[i][j] = '1';
        }
        else{
            arr[i][j] = '0';
        }
        i++;
    }
}

int main() {
    int n, m, sum = 0, max_sum = 0, numcheck[10];;
    char arr[100][10];
    while (cin >> n) {
        max_sum = 0;
        cin >> m;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                cin >> arr[i][j];
            }
        }
        for (int i = 0; i < pow(2, m); i++) {
            string str = bitset<10>(i).to_string();
            int counter = 0;
            for (int j = 10 - m; j < 10; j++) {
                numcheck[counter] = str[j] - '0';
                if (numcheck[counter] == 1){
                    cFlip(arr, n, counter);
                }
                counter++;
            }
            for (int j = 0; j < n; j++){
                sum += rFlip(arr, j, m);
            }
            if (sum > max_sum){
                max_sum = sum;
            }
            sum = 0;
            for (int j = 0; j < m; j++){
                if (numcheck[j] == 1){
                    cFlip(arr, n, j);
                }
            }
        }
        cout << max_sum << endl;
    }
    return 0;
}
