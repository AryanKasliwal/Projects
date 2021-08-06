#include <iostream>
#include <vector>
#include <unordered_set>
#include <string>

using namespace std;

class Gangster{
public:
    int num;
    vector <int> enemies;
    
    Gangster(int i){
        this->num = i;
    }
};

class DisjointSet{
public:
    int *rank, n;
    vector <Gangster> gangsters;
    unordered_set <int> nums;

    DisjointSet(int size){
        rank = new int[size];
        this->n = size;
        for (int i = 0; i < size + 1; i++){
            gangsters.emplace_back(Gangster(i));
        }
    }

    int find(int i){
        if (gangsters[i].num != i){
            gangsters[i].num = find(gangsters[i].num);
        }
        return gangsters[i].num;
    }

    void Union(int i, int j){
        int xset = find(i);
        int yset = find(j);
        if (xset == yset){
            return;
        }
        if (rank[xset] < rank[yset]) {
            gangsters[xset] = yset;
        }
        else if (rank[xset] > rank[yset]) {
            gangsters[yset] = xset;
        }
        else {
            gangsters[yset] = xset;
            rank[xset] = rank[xset] + 1;
        }
    }

    void add_enemy(int i, int j){
        for (int a = 0; a < gangsters[i].enemies.size(); a++){
            this->Union(j, gangsters[i].enemies[a]);
        }
        for (int b = 0; b < gangsters[b].enemies.size(); b++){
            this->Union(i, gangsters[j].enemies[b]);
        }
        gangsters[i].enemies.push_back(gangsters[j].num);
        gangsters[j].enemies.emplace_back(gangsters[i].num);
    }

    int calculateDifferentGroups(){
        int count = 0;
        for (int i = 0; i < gangsters.size(); i++){
            if (nums.find(gangsters[i].num) == nums.end()){
                nums.insert(gangsters[i].num);
                count ++;
            }
        }
        return count - 1;
    }
};

int main(){
    int n, m, a, b;
    cin >> n;
    DisjointSet gangsters = DisjointSet(n);
    cin >> m;
    string s;
    for (int i = 0; i < m; i++){
        cin >> s >> a >> b;
        if (s == "F"){
            gangsters.Union(a, b);
        }
        else{
            gangsters.add_enemy(a, b);
        }
    }
    cout << gangsters.calculateDifferentGroups() << endl;
}