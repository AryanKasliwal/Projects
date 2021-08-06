#include <iostream>
#include <deque>

using namespace std;

int main(){
    deque <int> nums;
    nums.emplace_back(1);
    nums.emplace_back(2);
    nums.emplace_back(3);
    while (!nums.empty()){
        cout << nums.back();
        nums.pop_back();
    }
}