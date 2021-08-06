#include<stack>
#include <iostream>
#include <vector>
#include<bits/stdc++.h> 
#include <cstdlib>

using namespace std;

int main(){
    int t;
    cin >> t;
    for (int test = 0; test < t; test ++){
        stack <long long int> priceStack;
        int n;
        cin >> n;
        long long int graph[n];
        for (int i = 0; i < n; i++){
            cin >> graph[i];
        }
        long long int thisIndex, maxArea = 0, area, i = 0;
        while (i < n){
            if (priceStack.empty() || graph[i] > graph[priceStack.top()]){
                priceStack.push(i++);
            }
            else{
                thisIndex = priceStack.top();
                priceStack.pop();
                if (priceStack.empty()){
                    area = graph[thisIndex] * i;
                }
                else {
                    area = graph[thisIndex] * (i - priceStack.top() - 1);
                }
                if (area > maxArea){
                    maxArea = area;
                }
            }
        }
        while (!priceStack.empty()){
            thisIndex = priceStack.top();
            priceStack.pop();
            if (priceStack.empty()){
                area = graph[thisIndex] * i;
            }
            else {
                area = graph[thisIndex] * (i - priceStack.top() - 1);
            }
            if (area > maxArea){
                maxArea = area;
            }
        }
        cout << maxArea << "\n";
    }
}