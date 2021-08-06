#include<iostream>
#include<deque>
#include<queue>

using namespace std;

int main(){
    int m, n;
    cin >> m >> n;
    deque <int> myDictionary;
    int numReferences = 0;
    for (int i = 0; i < n; i++){
        int word;
        cin >> word;
        bool wordInDict = false;
        for (int i = 0; i < myDictionary.size(); i++){
            if (myDictionary[i] == word){
                wordInDict = true;
                break;
            }
            
            if (myDictionary.size() >= m){
                myDictionary.pop_front();
            }
        }
        if (!wordInDict){
            numReferences++;
            myDictionary.emplace_back(word);
        }
    }
    cout << numReferences << "\n";
}