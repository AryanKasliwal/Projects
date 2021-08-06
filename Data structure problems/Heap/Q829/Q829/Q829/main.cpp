//
//  main.cpp
//  Q829
//
//  Created by Aarnav Gupta on 28/04/21.
//

#include <iostream>
#include <queue>

using namespace std;

void printMedian(vector<long long int> arr, int n) {
    priority_queue<long long int> s;
    priority_queue<long long int,vector<long long int>,greater<long long int>> g;

    long long int med = arr[0];
    s.push(arr[0]);

    cout << med << endl;

    for (int i=1; i < n; i++) {
        double x = arr[i];

        if (s.size() > g.size()) {
            if (x < med) {
                g.push(s.top());
                s.pop();
                s.push(x);
            }
            else
                g.push(x);

            med = (s.top() + g.top())/2.0;
        }

        else if (s.size() == g.size()) {
            if (x < med) {
                s.push(x);
                med = (double)s.top();
            }
            else {
                g.push(x);
                med = (double)g.top();
            }
        }

        else {
            if (x > med) {
                s.push(g.top());
                g.pop();
                g.push(x);
            }
            else
                s.push(x);

            med = (s.top() + g.top())/2.0;
        }
        if (i%2 == 0) cout << med << endl;
    }
}

int main() {
    int n;
    cin >> n;
    vector<long long int> A;
    for (int i = 0; i < n; i++) {
        long long int temp;
        cin >> temp;
        A.push_back(temp);
    }
    printMedian(A, n);
}

/*
7
1 3 5 7 9 11 6
*/
