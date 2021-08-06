#include <iostream>
#include <map>
#include <vector>

using namespace std;

struct Node {
    vector<Node*> branches;
    int arr[101] = {0};
    map<int, int> tree;
    bool to_be_cut = true, already_cut = false;
};


int numCuts(Node* n){
    int count = 0;
    if(n->to_be_cut){
        if(n->already_cut){
            return 0;
        }
        n->already_cut = true;
        return 1;
    }
    else{
        for(int i = 0; i < n->tree.size(); i++){
            count += numCuts(n->branches[i]);
        }
    }
    return count;
}

int main() {
    int t;
    cin >> t;
    for (int i = 0; i < t; i++){
        int n, m;
        cin >> n >> m;
        Node* curNode = new Node;
        map<int, int> map;
        int arr[100] = {0};
        for (int j = 0; j < n; j++){
            Node* temp = curNode;
            int num;
            cin >> num;
            if(arr[num] == 0){
                arr[num] = 1;
                temp->tree.insert(pair<int, int>(num, temp -> tree.size()));
                Node* newNode = new Node;
                temp->branches.push_back(newNode);
            }
            temp = temp->branches[temp->tree.find(num)->second];
            cin >> num;
            while(num != -1) {
                if(temp == NULL)
                    temp = new Node;
                if(temp->arr[num] == 0) {
                    temp->arr[num] = 1;
                    temp->tree.insert(pair<int, int>(num, temp -> tree.size()));
                    Node* newNode = new Node;
                    temp->branches.push_back(newNode);
                }
                temp = temp->branches[temp->tree.find(num)->second];
                cin >> num;
            }
        }
        for (int i = 0; i < m; i++){
            Node* temp = curNode;
            int num;
            cin >> num;
            if(arr[num] == 0){
                cin >> num;
                while(num != -1)
                    cin >> num;
                continue;
            }
            temp = temp->branches[temp->tree.find(num)->second];
            temp->to_be_cut = false;
            cin >> num;
            while(num != -1) {
                if(temp == NULL || temp->arr[num] == 0) {
                    cin >> num;
                    while(num != -1)
                        cin >> num;
                    continue;
                }
                temp = temp->branches[temp->tree.find(num)->second];
                temp->to_be_cut = false;
                cin >> num;
            }
        }
        int count = 0;
        for(int i = 0; i < curNode->tree.size(); i++) {
            if(curNode->branches[i]->tree.size() == 0)
                count += 1;
            else
                count += numCuts(curNode->branches[i]);
        }
        cout << count << endl;
    }
}
