#include <iostream>
#include <deque>
#include <vector>

using namespace std;

class Grid{
public:
    long long int height;
    Grid(long long int a){
        this->height = a;
    }
};

class DisjSet{
public:
    int *rank, n;
    int ROW, COL;
    deque <long long int> years;
    vector <vector <Grid>> originalGrids;
    vector <vector<bool>> visited;
    vector <vector <Grid>> grids;
    DisjSet(int n, int m){
        rank = new int[n];
        ROW = n;
        COL = m;
        this->n = n;
        for (int i = 0; i < n; i++){
            vector <Grid> temp;
            long long int a;
            for (int i = 0; i < m; i++){
                cin >> a;
                temp.emplace_back(Grid(a));
            }
            originalGrids.emplace_back(temp);
        }
        int T;
        cin >> T;
        grids = originalGrids;
        long long int year;
        for (int i = 0; i < T; i++){
            cin >> year;
            years.emplace_back(year);
        }
    }

    int safeCell(int row, int col){
        return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL) && (grids[row][col].height && !visited[row][col]);
    }
    
    void bfs(int row, int col){
        static int rowNbr[] = {-1, 0, 0, 1};
        static int colNbr[] = {0, -1, 1, 0};
        visited[row][col] = true;
        for (int k = 0; k < 8; ++k){
            if (safeCell(row + rowNbr[k], col + colNbr[k])){
                bfs(row + rowNbr[k], col + colNbr[k]);
            }
        }
    }

    int countIslands(int n, int m){
        visited.clear();
        for (int i = 0; i < n; i++){
            vector <bool> temp;
            for (int j = 0; j < m; j++){
                temp.push_back(false);
            }
            visited.push_back(temp);
        }
        int count = 0;
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < m; ++j){
                if (grids[i][j].height && !visited[i][j]){
                    bfs(i, j);
                    ++count;
                }
            }
        }
        return count;
    }

    void calcAns(){
        while (!years.empty()){
            for (int i = 0; i < ROW; i++){
                for (int j = 0; j < COL; j++){
                    if (grids[i][j].height <= years.front()){
                        grids[i][j] = 0;
                    }
                    else{
                        grids[i][j] = 1;
                    }
                }
            }
            cout << countIslands(ROW, COL) << " ";
            grids = originalGrids;
            years.pop_front();
        } 
    }
};

int main(){
    int n, m;
    cin >> n >> m;
    DisjSet island = DisjSet(n, m);
    island.calcAns();
}