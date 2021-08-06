
#include <cstdio>
#include <cstring>
#include <iostream>
#include <queue>

using namespace std;

const int N = 2e3 + 5, M = 2 * N * N;
int n, m, num1, k, num2, gArr1[N], gArr2[N], gArr3[N], gArr4[N], indexes[N];

struct ped {
    int value, next;
} ped_arr[M];

void workFunc(int u, int v) {
    ped_arr[++num2].value = v;
    ped_arr[num2].next = gArr1[u];
    gArr1[u] = num2;
}

int sortPed() {
    queue<int> que;
    for (int i = 1; i <= num1; i++) {
        if (!indexes[i]) {
            que.push(i);
        }
        if (i <= n) {
            gArr4[i] = 1;
        }
    }
    while (!que.empty()) {
        int u = que.front(); que.pop();
        for (int j = gArr1[u]; j; j = ped_arr[j].next) {
            int v = ped_arr[j].value;
            if (v <= n) {
                gArr4[v] = gArr4[u] + 1;
            }
            else {
                gArr4[v] = gArr4[u];
            }
            indexes[v]--;
            if (!indexes[v]) {
                que.push(v);
            }
        }
    }
    int ans = 0;
    for (int i = 1; i <= n; i++) {
        ans = max(ans, gArr4[i]);
    }
    return ans;
}
int main() {
    cin >> n >> m;
    num1 = n;
    for (int i = 1; i <= m; i++) {
        cin >> k;
        for (int j = 1; j <= k; j++) {
            cin >> gArr2[j];
        }
        gArr2[k + 1] = -1;
        for (int j = 1; j <= k; j++) {
            for (int u = gArr2[j] + 1; u < gArr2[j + 1]; u++) {
                workFunc(u , num1 + 1);
                indexes[num1 + 1]++;
            }
            workFunc(num1 + 1, gArr2[j]); indexes[gArr2[j]]++;
        }
        num1++;
    }
    cout << sortPed() << endl;
    return 0;
}
