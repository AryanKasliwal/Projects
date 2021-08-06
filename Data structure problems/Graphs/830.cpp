/*#include <iostream>
#include <vector>
#include <list>
#include <queue>

using namespace std;

bool isPathBFS(vector <vector <int>> &ways, int nodes, int start, int end){
    vector <bool> visited;
    visited.resize(nodes);
    queue <int> traversal;
    traversal.push(start);
    visited[start] = true;
    while (!traversal.empty()){
        start = traversal.front();
        traversal.pop();
        for (int i = 0; i < (int)ways[start].size(); ++i){
            if (ways[start][i] == end){
                return true;
            }
            if (visited[ways[start][i]] == false){
                traversal.push(ways[start][i]);
                visited[ways[start][i]] = true;
            }
        }
    }
    return false;
}

int main(){
    int n, k, m;
    cin >> k >> n >> m;
    int friends[k];
    vector <int> finalCities;
    for (int i = 0; i < k; i++){
        cin >> friends[i];
    }
    int count = 0;
    vector <vector <int>> ways;
    ways.resize(n + 1);
    for (int i = 0; i < m; i++){
        int start;
        int end;
        cin >> start >> end;
        finalCities.emplace_back(end);
        ways[start].push_back(end);
    }

    for (int i = 0; i < finalCities.size(); i++){
        bool possible = true;
        for (int j = 0; j < k; j++){
            possible = isPathBFS(ways, n + 1, friends[j], finalCities[i]);
            if (friends[j] == i){
                possible = true;
            }
            if (possible == false){
                break;
            }
        }
        if (possible){
            count++;
        }
    }
    cout << count << endl;
}*/










/*
#include <iostream>
#include <map>
#include <list>
#include <vector>
#include <stdlib.h>
#include <unordered_set>

using namespace std;

class Graph
{
public:
    map<int, list<int>> adj;
    vector <long long int> visited;
    
    void addEdge(int v, int vec2){
        adj[v].push_back(vec2);
    }
    bool DFS(int v, int final){
        visited[v] = 1;return true;
        list<int>::iterator i;
        for (i = adj[v].begin(); i != adj[v].end(); ++i){
            if (! arr[*i]){
                return DFS(*i, final, arr);
            }
        }
        return false;
    }
};

int main(){
    Graph vec1;
    long long int k, n, m;
    // k = rand() % 100;
    // n = rand() % 1000;
    // m = rand() % 10000;
    cin >> k >> n >> m;
    vector <int> friends(k + 1);
    int x;
    for (int i = 0; i < k; i++){
        cin >> x;
        friends[i] = x;
        // x = rand() % n;
    }
    long long int a, b;
    for (int i = 0; i < m; i ++){
        cin >> a >> b;
        // a = rand() % n;
        // b = rand() % n;
        vec1.addEdge(a, b);
    }
    for(auto it = possibleAnswers.begin(); it != possibleAnswers.end(); ++it){
        vecPossibleAns.emplace_back(*it);
    }
    int count1 = 0, count = 0;
    /*
    const int z = n;
    for (int i = 0; i < vecPossibleAns.size(); i++){
        bool possible = false;
        for (int j = 0; j < k; j++){
            if (friends[j] != vecPossibleAns[i]){
                if (vec1.BFS(friends[j], vecPossibleAns[i], n)){
                    possible = true;
                }
                else{
                    possible = false;
                    break;
                }
            }
        }
        if (possible){
            count1++;
        }
    }
    cout << count1 << endl;

    for (int i = 0; i < vecPossibleAns.size(); i++){
        bool possible = false;
        for (int j = 0; j < k; j++){
            if (friends[j] != vecPossibleAns[i]){
                vector <bool> visited;
                for (int i = 0; i < n; i++){
                    visited.push_back(false);
                }
                if(vec1.DFS(friends[j], vecPossibleAns[i], visited)){
                    possible = true;
                }
                else{
                    possible = false;
                    break;
                }
            }
        }
        if (possible){
            count++;
        }
    }
    cout << count << endl;
}
// Driver code
int main(){
    int iteration = 0;
    while (true){
        iteration++;
        Graph vec1;
        int k, n, m;
        k = rand() % 100 + 1;
        n = rand() % 1000 + 1;
        m = rand() % 10000 + 1;
        // cin >> k >> n >> m;
        vector <int> friends;
        unordered_set <int> possibleAnswers;
        int x;
        for (int i = 0; i < k; i++){
            // cin >> x;
            x = rand() % n;
            friends.emplace_back(x);
            possibleAnswers.insert(friends[i]);
        }
        int a, b;
        for (int i = 0; i < m; i ++){
            // cin >> a >> b;
            a = rand() % n;
            b = rand() % n;
            possibleAnswers.insert(b);
            vec1.addEdge(a, b);
        }
        int count = 0;

        for (auto it = possibleAnswers.begin(); it != possibleAnswers.end(); ++it){
            bool visited[n] = {false};
            bool possible = false;
            for (int j = 0; j < k; j++){
                if (friends[j] != *it){
                    if(vec1.DFS(friends[j], *it, visited)){
                        possible = true;
                    }
                    else{
                        possible = false;
                        break;
                    }
                }
            }
            if (possible){
                count++;
            }
        }
        cout << iteration << ": " << count << endl;
    }
}*/


#include<iostream>
#include<vector>
#include<algorithm>
#include<set>

using namespace std;

vector<long long int> vec1[100001], visited(100001), vec2(100001);

void DFS(long long int x) {
    visited[x]=1;
    for(long long int i = 0; i < vec1[x].size(); i++) {
        if(visited[vec1[x][i]] == 1){
            continue;
        }
        DFS(vec1[x][i]);
    }
}

int main() {
    long long int k, n, m, i, j;
    cin >> k >> n >> m;
    vector<long long int> friends(k + 1);
    for(i = 1; i <= k; i++){
        cin >> friends[i];
    }
    int x, y;
    for(i = 1; i <= m; i++) {
        cin >> x >> y;
        vec1[x].push_back(y);
    }
    vector<long long int> reachable(n + 1);
    for(i = 1; i <= k; i++){
        for(j = 1; j <= n; j++){
            visited[j]=0;
        }
        DFS(friends[i]);
        for(j = 1; j <= n; j++) {
            if(visited[j] == 0){
                continue;
            }
            reachable[j]++;
        }
    }
    long long int count = 0;
    for(i = 1; i <= n; i++){
        if(reachable[i] == k){
            count++;
        }
    }
    cout << count << endl;
}