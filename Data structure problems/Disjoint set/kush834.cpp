#include <stdio.h>
#include <vector>


class Starship{
public:
    int cur_zone;
    int thisZone;
    int ship_below = 0;
    int ship_above = 0;
    int distance_from_top = 0;

    Starship(int i){
        this->cur_zone = i;
    }
};

class DisjSet {
    int *rank, n;
    std::vector <Starship> starShips;
  
public:
    DisjSet(int n){
        rank = new int[n];
        this->n = n;
        for (int i = 0; i < n; i++) {
            starShips.emplace_back(Starship(i));
        }
    }

    int find(int i){
        if (starShips[i].ship_above == 0){
            return i;
        }
        int temp = starShips[i].ship_above;
        while (starShips[temp].ship_above != 0){
            temp = starShips[temp].ship_above;
        }
        return temp;
    }

    int find_tail(int i){
        if (starShips[i].ship_below == 0){
            return i;
        }
        int temp = starShips[i].ship_below;
        while (starShips[temp].ship_below != 0){
            temp = starShips[temp].ship_below;
        }
        return temp;
    }

    void Union(int i, int j){
        int xset = find(i);
        int yset = find_tail(j);
        starShips[xset].cur_zone = starShips[yset].cur_zone;
        starShips[xset].ship_above = yset;
        starShips[yset].ship_below = xset;
    }

    int num_of_ships_in_the_middle(int i, int j){
        if (find(i) != find(j)){
            return -1;
        }
        
        int count_up = 0;
        int count_down = 0;
        int temp_up = i, temp_down = i;
        if (i == j){
            return 0;
        }

        while (true){
            temp_up = starShips[temp_up].cur_zone;
            if (temp_up == j){
                return count_up;
            }
            else{
                count_up++;
            }
            temp_down = starShips[temp_down].ship_below;
            if (temp_down == j){
                return count_down;
            }
            else{
                count_down++;
            }
        }
    }
};
  

int main(){
    DisjSet ships = DisjSet(30001);
    int n;
    scanf("%d",&n);
   
    char c;
    int i, j;
    for (int order = 0; order < n; order++){
        scanf("\n%c",&c);
        scanf("%d",&i);
        scanf("%d",&j);
        //cin >> c >> i >> j;
        if (c == 77){
            ships.Union(i, j);
        }
        else{
           printf("%d\n",ships.num_of_ships_in_the_middle(i, j));
        }
    }
}