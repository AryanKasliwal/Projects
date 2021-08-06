#include<iostream>
#include<set>
#include<vector>
#include<algorithm>

using namespace std;

vector<long long int> vec[100001], visited(100001), kush_bhi(100001);
long long int a = 1000000000000000000;
long long int num;

void DFS(long long int x, long long int y) {
    visited[x] = 1;
    for(long long int i=0;i<vec[x].size();i++) {
        if(visited[vec[x][i]] == 1) continue;
        DFS(vec[x][i], y + 1);
    }
    num += y * kush_bhi[x];
}

int main() {
    long long int n, i;
    cin >> n;
    for(i = 1; i <= n; i++) {
        int u, x;
        cin >> kush_bhi[i] >> u >> x;
        if(x != 0) {
            vec[x].push_back(i);
            vec[i].push_back(x);
        }
        if(u != 0) {
            vec[u].push_back(i);
            vec[i].push_back(u);
        }
    }
    for(i = 1; i <= n; i++) {
        for(long long int j = 1; j <= n; j++) {
            visited[j] = 0;
        }
        num = 0;
        DFS(i, 0);
        a = min(a, num);
    }
    cout << a << endl;
}