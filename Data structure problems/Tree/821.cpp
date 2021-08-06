#include <algorithm>
#include <iostream>
#include <set>
#include <vector>
using namespace std;

const long long int hugeNum=1000000000000000000;

long long int power(long long int x, unsigned long long int y, long long int p){
    long long int ans = 1;
    x = x % p;
    if (x == 0){
        return 0;
    }
    while (y > 0){
        if (y & 1){
            ans = (ans*x) % p;
        }
        y = y >> 1;
        x = (x*x) % p;
    }
    return ans;
}

long long int invMod(long long int a,long long int m){
    return power(a, m - 2, m);
}

long long int _pow(long long int a, long long int b){
    if(not b){
        return 1;
    }
    long long int temp = _pow(a, b / 2);
    temp = (temp * temp);
    if(b % 2){
        return (a * temp);
    }
    return temp;
}

long long int greatest_common_divisor(long long int a, long long int b){
    if (b == 0){
        return a;
    }
    return greatest_common_divisor(b, a % b);
      
}

long long int cl(long long int a,long long int x)
{
    if(a % x == 0){
        return a / x;
    }
    else{
        return a / x + 1;
    }
}

vector<pair<long long int,long long int>> g[100001];
vector <long long int> visited(100001);
long long int ans = -hugeNum;

long long int DFS(long long int v){
    visited[v] = 1;
    multiset<long long int> set;
    for(long long int i = 0; i < g[v].size(); i++){
        if(visited[g[v][i].first] == 1){
            continue;
        }
        long long int x = g[v][i].second + DFS(g[v][i].first);
        set.insert(x);
    }
    if(set.size() == 0){
        return 0;
    }
    if(set.size() == 1){
        auto num1 = set.end();
        num1--;
        return *num1;
    }
    auto num1 = set.end();
    num1--;
    auto num2 = num1;
    num2--;
    long long int x = *num1 + *num2;
    ans = max(ans, x);
    return *num1;
}

int main() {
    long long int n;
    cin >> n;
    long long int u, v, w;
    for(int i = 1; i <= n; i++) {
        cin >> u >> v >> w;
        g[v].push_back(make_pair(u,w));
        g[u].push_back(make_pair(v,w));
    }
    ans = max(ans, DFS(1));
    cout << ans << endl;
}