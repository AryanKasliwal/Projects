#include <iostream>
#include <unordered_set>
#include <unordered_map>

using namespace std;

int main (){
    int n, c;
    unordered_map <long long int, long long int> map;
    unordered_set <long long int> array;
    while (cin >> n >> c){
        long long int count = 0;
        long long int x;
        for (long long int i = 0; i < n; i++){
            cin >> x;
            if (array.find(x) == array.end()){
                map.insert({x, 1});
            }
            else {
                map[x]++;
            }
            array.insert(x);
        }
        for (auto i = array.begin(); i != array.end(); ++i){
            if (array.find(*i + c) != array.end()){
                count += map[*i + c] * map[*i];
            }
        }
        map.clear();
        array.clear();
        cout << count << "\n";
    }
}