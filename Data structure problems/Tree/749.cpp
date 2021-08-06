#include<bits/stdc++.h>
#include <vector>
#include <string>
using namespace std;
 
vector <int> in;
int counter = 0;
int curIndex = 0; 
 
int findRoot(vector <int> arr, int start, int end, int root){ 
    int i = 0; 
    for (i = start; i < end; i++){ 
        if (arr[i] == root){ 
            return i; 
        } 
    } 
    return i; 
} 

void postOrder(vector <int> arr, vector <int> pre, int start, int end){ 
    if (start > end){ 
        return; 
    }
    int inIndex = findRoot(arr, start, end,pre[curIndex++]);
    postOrder(arr, pre, start, inIndex - 1);
    postOrder(arr, pre, inIndex + 1, end);
    if (counter != in.size() - 1){
        cout << arr[inIndex] << " ";
        counter ++;
    } 
    else{
        cout << arr[inIndex] << endl;
    }
}

int main(){ 
    int t = 0;
    cin >> t;
    for (int test = 0; test < t; test ++){
        curIndex = 0;
        counter = 0;
        in.clear();
        string input1, input2;
        int n = 0;
        cin >> n;
        string ignore;
        getline(cin, ignore);
        getline(cin, input1);
        getline(cin, input2);
        stringstream ss(input1);
        istream_iterator <string> begin(ss);
        istream_iterator <string> end;
        vector <string> preOrder(begin, end);
        stringstream ss1 (input2);
        istream_iterator <string> begin1(ss1);
        istream_iterator <string> end1;
        vector <string> inOrder(begin1, end1);
        for (int i = 0; i < inOrder.size(); i++){
            in.emplace_back(stoi(inOrder[i]));
        }
        vector <int> pre;
        for (int i = 0; i < inOrder.size(); i++){
            pre.emplace_back(stoi(preOrder[i]));
        }
        postOrder(in, pre, 0, in.size() - 1); 
    } 
    return 0;
} 