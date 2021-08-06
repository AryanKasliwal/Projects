#include <iostream>
#include <stack>
#include <stdlib.h>
using namespace std;
 
struct Point
{
    long long int x, y;
};
Point p0;

Point secondInStack(stack<Point> &S){
    Point p = S.top();
    S.pop();
    Point res = S.top();
    S.push(p);
    return res;
}

void swap(Point &a, Point &b){
    Point temp = a;
    a = b;
    b = temp;
}

int distSq(Point a, Point b){
    return (b.x - a.x)*(b.x - a.x) + (b.y - a.y)*(b.y - a.y);
}

int orientation(Point p, Point q, Point r){
    long long int val = ((q.x - p.x) * (r.y - p.y)) - ((q.y - p.y) * (r.x - p.x));
    if (val == 0){ 
        return 0;
    }
    else if (val > 0){
        return 1;
    }
    return 2;
}

int compare(const void *p1, const void *p2){
    Point *a = (Point *)p1;
    Point *b = (Point *)p2;
 
    int o = orientation(p0, *a, *b);
    if (o == 0){
        return (distSq(p0, *b) >= distSq(p0, *a))? -1 : 1;
    }
    else if(o == 2){
       return -1;
    }
    else{
        return 1;
    }
}

void convexHull(Point points[], long long int n){
    long long int xmin = points[0].x, min = 0;
    for (int i = 1; i < n; i++){
        int x = points[i].x;
        if ((x < xmin) || (xmin == x && points[i].y < points[min].y)){
            xmin = points[i].x, min = i;
        }
    }
    swap(points[0], points[min]);
    p0 = points[0];
    qsort(&points[1], n-1, sizeof(Point), compare);
    long long int m = 1;
    for (int i = 1; i < n; i++){
        while (i < n-1 && orientation(p0, points[i], points[i+1]) == 0){
           i++;
        }
        points[m] = points[i];
        m++;
    }
    if (m < 3) return;
    stack<Point> S;
    S.push(points[0]);
    S.push(points[1]);
    S.push(points[2]);
    for (int i = 3; i < m; i++){
        while (orientation(secondInStack(S), S.top(), points[i]) != 2){
            S.pop();
        }
        S.push(points[i]);
    }
    stack <Point> tempA;
    int length = (int)S.size();
    stack <Point> tempB;
    stack <Point> tempC;
    stack <Point> tempD;
    Point minimum = S.top();
    Point temp;
    stack <Point> ansStack;
    for (int i = 0; i < length; i++){
        temp = S.top();
        if (temp.x < minimum.x){
            minimum = temp;
        }
        else if (temp.x == minimum.x && temp.y < minimum.y){
            minimum = temp;
        }
        tempA.push(temp);
        S.pop();
    }
    temp = tempA.top();
    while (temp.x != minimum.x){
        tempB.push(temp);
        tempA.pop();
        temp = tempA.top();
    }
    tempA.pop();
    tempC.push(temp);
    while (!tempB.empty()){
        temp = tempB.top();
        tempB.pop();
        tempC.push(temp);
    }
    while (!tempA.empty()){
        temp = tempA.top();
        tempD.push(temp);
        tempA.pop();
    }
    while (!tempD.empty()){
        temp = tempD.top();
        tempC.push(temp);
        tempD.pop();
    }
    while (!tempC.empty()){
        ansStack.push(tempC.top());
        tempC.pop();
    }
    int s = ansStack.size();
    cout << s << endl;
    for (int i = 0; i < s; i++){
        cout << ansStack.top().x << " " << ansStack.top().y << "\n";
        ansStack.pop();
    }
}

int main(){
    int t;
    cin >> t;
    int n;
    long long int x, y;
    for (int test = 0; test < t; test++){
        cin >> n;
        Point array[n];
        for (int point = 0; point < n; point++){
            cin >> array[point].x >> array[point].y;
        }
        convexHull(array, n);
    }
}