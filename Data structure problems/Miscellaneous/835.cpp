#include <iostream>
#include <cmath>
#include <vector>

using namespace std;

class Circle{
public:
    int x, y, r;
    Circle(int x, int y, int r){
        this->x = x;
        this->y = y;
        this->r = r;
    }
};

bool pointInCircle(Circle c, int x, int y){
    if (pow(c.x - x, 2) + pow(c.y - y, 2) < pow(c.r, 2)){
        return true;
    }
    return false;
}

int main(){
    vector <Circle> walls;
    int n;
    cin >> n;
    int x, y, r;
    for (int i = 0; i < n; i++){
        cin >> x >> y >> r;
        walls.emplace_back(Circle(x, y, r));
    }
    int q;
    cin >> q;
    int a, b, c, d;
    int count;
    for (int i = 0; i < q; i++){
        count = 0;
        bool oneInCircle = false, twoInCircle = false;
        cin >> a >> b >> c >> d;
        for (int i = 0; i < walls.size(); i++){
            if (pointInCircle(walls[i], a, b)){
                oneInCircle = true;
            }
            if (pointInCircle(walls[i], c, d)){
                twoInCircle = true;
            }
            if (oneInCircle && !twoInCircle){
                count++;
            }
            else if (!oneInCircle && twoInCircle){
                count++;
            }
            oneInCircle = false;
            twoInCircle = false;
        }
        cout << count << endl;
    }
}