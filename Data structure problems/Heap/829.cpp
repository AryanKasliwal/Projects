#include <iostream>
#include <queue>

using namespace std;

void printAns(vector<long long int> arr, int n) {
    priority_queue<long long int> s;
    priority_queue<long long int,vector<long long int>,greater<long long int>> g;

    long long int num = arr[0];
    s.push(num);

    cout << num << endl;

    for (int i=1; i < n; i++) {
        double var = arr[i];
        if (s.size() > g.size()) {
            if (var < num) {
                g.push(s.top());
                s.pop();
                s.push(var);
            }
            else
                g.push(var);

            num = (s.top() + g.top())/2.0;
        }
        else if (s.size() == g.size()) {
            if (var < num) {
                s.push(var);
                num = (double)s.top();
            }
            else {
                g.push(var);
                num = (double)g.top();
            }
        }
        else {
            if (var > num) {
                s.push(g.top());
                g.pop();
                g.push(var);
            }
            else
                s.push(var);

            num = (s.top() + g.top())/2.0;
        }
        if (i%2 == 0){
            cout << num << endl;
        }
    }
}

int main() {
    int n;
    cin >> n;
    vector<long long int> arr;
    for (int i = 0; i < n; i++) {
        long long int temp;
        cin >> temp;
        arr.push_back(temp);
    }
    printAns(arr, n);
}
