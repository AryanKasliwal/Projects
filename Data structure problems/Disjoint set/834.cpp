/*#include <vector>
#include <iostream>
#include <cstdio>

using namespace std; 

class Starship{
public:
    int num, rank, parent, child, representative, distance_from_top = 0, tail;
    Starship(int *i){
        this->representative = *i;
        this->num = *i;
        this->parent = *i;
        this->rank = 0;
        this->child = *i;
        this->tail = *i;
    }
};

class DisjointSet{
public:
    int parent[30001] = {0}, child[30001] = {0}, representative[300001] = {0}, tail[30001] = {0}, distance_from_top[30001] = {0};
    vector <Starship> starships;

    DisjointSet(int i){
        for (int j = 0; j < i; j++){
            starships.emplace_back(Starship(&j));
        }
    }

    void Union1(int i, int j){
        int x, y;
        if (representative[i] == 0){
            representative[i] = i;
            x = i;
        }
        else{
            x = representative[i];
        }
        if (tail[j] == 0){
            tail[j] = j;
            y = j;
        }
        else{
            y = tail[representative[j]];
        }
        if (representative[j] == 0){
            representative[j] = j;
        }
        if (tail[x] == 0){
            tail[x] = x;
        }
        tail[representative[j]] = tail[x];
        parent[x] = y;
        child[y] = x;
        representative[x] = representative[y];
        distance_from_top[x] = distance_from_top[y] + 1;
        while (child[x] != 0){
            x = child[x];
            representative[x] = representative[y];
            distance_from_top[x] += distance_from_top[y] + 1;
        }
    }

    int printAns(int i, int j){
        if (representative[i] != representative[j]){
            return -1;
        }

        int a = distance_from_top[i];
        int b = distance_from_top[j];
        if (a > b){
            return a - b - 1;
        }
        else{
            return b - a - 1;
        }
    }

    void Union(int i, int j){
        int x = starships[i].representative;
        int y = starships[starships[j].representative].tail;
        starships[starships[j].representative].tail = starships[x].tail;
        starships[x].parent = y;
        starships[y].child = x;
        starships[x].representative = starships[y].representative;
        starships[x].distance_from_top += starships[y].distance_from_top + 1;
        while (starships[x].child != x){
            x = starships[x].child;
            starships[x].representative = starships[y].representative;
            starships[x].distance_from_top += starships[y].distance_from_top + 1;
        }
    }

    int num_of_ships_in_the_middle(int i, int j){
        if (starships[i].representative != starships[j].representative){
            return -1;
        }
        int a = starships[i].distance_from_top;
        int b = starships[j].distance_from_top;
        if (a > b){
            return a - b - 1;
        }
        else{
            return b - a - 1;
        }
    }
};


int main(){
    int N;
    // scanf("%d", &N);
    cin >> N;
    char command;
    DisjointSet ships = DisjointSet(1);
    int a, b;
    for (int i = 0; i < N; i++){
        cin >> command >> a >> b;
        // scanf("%s", &command);
        // scanf("%d", &a);
        // scanf("%d", &b);
        if (command == 77){
            ships.Union1(a, b);
        }
        else{
            cout << ships.printAns(a, b) << endl;
        }
    }
}*/


#include <iostream>

using namespace std;

const int const_number = 30001;

int gArr1[const_number], gArr2[const_number], gArr3[const_number], p, q;

int find(int number) {
    if (gArr1[number] != number){
        int lehre = find(gArr1[number]);
        gArr2[number] += gArr2[gArr1[number]];
        gArr1[number] = lehre;
    }
    return gArr1[number];
}


int main() {
    int T;
    cin >> T;

    for (int i = 0; i < const_number; i ++){
        gArr1[i] = i;
        gArr3[i] = 1;
        gArr2[i] = 0;
    }

    for(int i = 0; i < T; i++){
        char command;
        cin >> command >> p >> q;
        if (command == 'M'){
            int x = find(p), b = find(q);
            gArr2[x] += gArr3[b];
            gArr3[b] += gArr3[x];
            gArr1[x] = b;
        } 
        else{
            int x = find(p), b = find(q);
            if (x != b){
                cout << "-1" << endl;
            }
            else{
                cout << abs(gArr2[p] - gArr2[q]) - 1 << endl;
            }
        }
    }
    return 0;
}