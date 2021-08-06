//
//  main.cpp
//  Q830
//
//  Created by Aarnav Gupta on 29/04/21.
//

#include<iostream>
#include<set>
#include<vector>
#include<algorithm>

using namespace std;

vector<long long int> g[100001], vis(100001), w(100001);

void dfs(long long int v) {
    vis[v]=1;
    for(long long int i = 0; i < g[v].size(); i++) {
        if(vis[g[v][i]] == 1) continue;
        dfs(g[v][i]);
    }
}

int main() {
    long long int k, n, m, i, j;
    cin >> k >> n >> m;
    vector<long long int> city(k + 1);
    for(i = 1; i <= k; i++)
        cin>>city[i];
    for(i = 1; i <= m; i++) {
        int u, v;
        cin >> u >> v;
        g[u].push_back(v);
    }
    vector<long long int> reach(n + 1);
    for(i = 1; i <= k; i++) {
        for(j = 1; j <= n; j++)
            vis[j]=0;
        dfs(city[i]);
        for(j = 1; j <= n; j++) {
            if(vis[j] == 0) continue;
            reach[j]++;
        }
    }
    long long int ans = 0;
    for(i = 1; i <= n; i++) {
        if(reach[i] == k)
            ans++;
    }
    cout<<ans<<endl;
}
