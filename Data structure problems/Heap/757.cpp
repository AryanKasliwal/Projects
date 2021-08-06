#include <iostream>
#include <cstdio>
#include <queue>

using namespace::std;

int arr[200000][3];

struct Node {
    int label, numOccurance;

    Node(int a, int b) {
        label = a;
        numOccurance = b;
    }
};

int calcWeight(int num, int weight) {
    if (arr[num][1] == -1){
        return weight * arr[num][0];
    }
    else{
        return calcWeight(arr[num][1], weight + 1) + calcWeight(arr[num][2], weight + 1);
    }
}

bool operator < (const Node &n1, const Node &n2) {
    return n1.numOccurance > n2.numOccurance;
}

int main(){
    int t = 0;
    while (cin >> t){
        priority_queue<Node> pQueue;
        for (int i = 0; i < t; i++){
            int n = 0;
            cin >> n;
            pQueue.emplace(Node(i, n));
            arr[i][0] = n;
            arr[i][1] = arr[i][2] = -1;
        }
        while (true){
            Node n1 = pQueue.top();
            pQueue.pop();

            if (pQueue.empty()){
                break;
            }
            Node n2 = pQueue.top();
            pQueue.pop();
            pQueue.emplace(Node(t, n1.numOccurance + n2.numOccurance));
            arr[t][0] = n1.numOccurance + n2.numOccurance;
            arr[t][1] = n1.label;
            arr[t++][2] = n2.label;
        }
        cout << calcWeight(t - 1, 0) << endl;
    }
}
