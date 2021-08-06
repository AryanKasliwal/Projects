#include<iostream>

using namespace std;

bool arr[100000 + 2] = {false};

int main(){
    int n;
    cin >> n;
    arr[0] = true;
    arr[n + 1] = true;
    int x = 0, left_count = 0, right_count = 0;
    for (int i = 0; i < n; i++){
        cin >> x;
        arr[x] = true;
        bool right_found = false, left_found = false;
        left_count = x - 1, right_count = x + 1;
        while (true){
            if (arr[left_count]){
                left_found = true;
            }
            if (arr[right_count]){
                right_found = true;
            }
            if (right_found && left_found){
                break;
            }
            if (!left_found){
                left_count--;
            }
            if (!right_found){
                right_count++;
            }
        }
        cout << left_count << " " << right_count << '\n';
    }
}