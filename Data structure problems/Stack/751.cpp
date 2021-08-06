#include <iostream>
#include <stack>
#include <string>
#include <fstream>
#include <vector>
#include<sstream>

using namespace std;

stack<int> expression;
void operate(char symbol){
    int n = 0;
    if (symbol == 42){
        n = expression.top();
        expression.pop();
        n *= expression.top();
        expression.pop();
    }
    else if (symbol == 43){
        n = expression.top();
        expression.pop();
        n += expression.top();
        expression.pop();
    }
    else if (symbol == 45){
        n = expression.top();
        expression.pop();
        int m = expression.top();
        n = m - n;
        expression.pop();
    }
    expression.push(n);
}

void clearStack(){
    while (!expression.empty()){
        expression.pop();
    }
}
int main (){
    string input;
    while (getline(cin, input)){
        if (input == ""){
            return 0;
        }
        for (int i = 0; i < input.length() + 1; i++){
            if (isdigit(input.at(i))){
                expression.push(input[i] - 48);
            }
            else{
                operate(input.at(i));
            }
            i++;
        }
        cout << expression.top() << '\n';
        clearStack();
    }
}