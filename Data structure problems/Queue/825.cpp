#include <iostream>
#include <set>
#include <vector>
#include <queue>

using namespace std;

int sum = 0;
int nationalities[300000] = {0};

class Cruize{
private:
    long long int time;
    int numPassengers;
    int numNationalities = 0;
    
public:
    vector <int> nationality;
    Cruize(long long int time, int numPassengers){
        this->time = time;
        this->numPassengers = numPassengers;
        int x;
        for (int i = 0; i < numPassengers; i++){
            cin >> x;
            nationality.emplace_back(x);
            if (nationalities[x] == 0){
                sum++;
            }
            nationalities[x]++;
        }
    }
    void removePassengers(){
        for (int i = 0; i < nationality.size(); i++){
            nationalities[nationality[i]]--;
            if (nationalities[nationality[i]] == 0){
                sum--;
            }
        }
    }
    long long int getTime(){
        return time;
    }
};

deque <Cruize> cruizes;

int main(){
    int n;
    cin >> n;
    for (int loop = 0; loop < n; loop++){
        long long int time;
        int numPassengers;
        cin >> time >> numPassengers;
        cruizes.push_back(Cruize(time, numPassengers));
        if (cruizes.back().getTime() - cruizes.front().getTime() >= 86400){
            do{
                cruizes.front().removePassengers();
                cruizes.pop_front();
            } while (cruizes.back().getTime() - cruizes.front().getTime() >= 86400);
        }
        cout << sum << "\n";
    }
}

/*
#include<iostream>
#include<queue>
#include <set>
#include<bits/stdc++.h>

using namespace std;

set <int> done_nationalities;

class Cruize{
private:
    
    long long int time;
    int numPassengers;
    int numNationalities = 0;
    
public:

    vector <int> nationality;
    Cruize(long long int time, int numPassengers){
        this->time = time;
        this->numPassengers = numPassengers;
        int x;
        for (int i = 0; i < numPassengers; i++){
            cin >> x;
            nationality.push_back(x);
        }
    }

    long long int getTime(){
        return time;
    }

    int getPassengers(){
        return numPassengers;
    }

    int getNationalities(){
        done_nationalities.insert(nationality.begin(), nationality.end());
        return done_nationalities.size();
    }
};

deque <Cruize> cruizes;

int main (){
    int n;
    cin >> n;
    int prevAns = 0;
    for (int loop = 0; loop < n; loop ++){
        long long int time;
        int numPassengers;
        cin >> time >> numPassengers;
        cruizes.push_back(Cruize(time, numPassengers));
        int answer = prevAns;
        if (cruizes.back().getTime() - cruizes.front().getTime() >= 86400){
            do{
                cruizes.pop_front();
            } while (cruizes.back().getTime() - cruizes.front().getTime() >= 86400);
            done_nationalities.clear();
            answer = 0;
            for (int i = 0; i < cruizes.size(); i++){
                answer = cruizes.at(i).getNationalities();
            }
            prevAns = answer;
        }
        else{
            answer = cruizes.back().getNationalities();
            prevAns = answer;
        }
        cout << answer << "\n";
    }
}*/