#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

int main() {
    int n;
    while(cin >> n) {
        int m, lengthA = 0, lengthB = 0;
        vector<string> essay;
        unordered_set<string> academicWords;
        unordered_set<string> academicWordsPresent;
        while(n--) {
            string a;
            cin >> a;
            academicWords.insert(a);
        }
        cin >> m;
        lengthA = m;
        while(m--) {
            string a;
            cin >> a;
            if (academicWords.find(a) != academicWords.end()) {
                academicWordsPresent.insert(a);
            }
            essay.push_back(a);
        }
        lengthB = (int) academicWordsPresent.size();
        if (lengthB == 0 or lengthB == 1){
            cout << lengthB << endl << lengthB;
        }
        else {
            unordered_map<string, int> map1;
            unordered_map<string, int> map2;
            
            for (auto i = academicWordsPresent.begin(); i != academicWordsPresent.end(); ++i){
                map1[*i]++;
            }
            
            int l = 0, r = 0, formed = 0, req = map1.size();
            
            int ans[] = {-1, 0, 0};
            
            while (r < essay.size()) {
                map2[essay[r]]++;
                
                if(map1.count(essay[r]) && map1[essay[r]] == map2[essay[r]]){
                    formed++;
                }
                
                while(l <= r and formed == req) {
                    map2[essay[l]]--;
                    
                    if(ans[0] == -1 or ans[0] > r - l + 1) {
                        ans[0] = r - l +1;
                        ans[1] = l;
                        ans[2] = r;
                    }
                    
                    if(map1.count(essay[l]) and map1[essay[l]] > map2[essay[l]]){
                        formed--;
                    }
                    l++;
                }
                r++;
            }
            if (ans[0] != -1){
                cout << lengthB << endl << ans[0] << endl;
            }
        }
    }
}

/*
int main(){
    long long int n;
    while (cin >> n){
        unordered_set <string> academic_words;
        unordered_set <string> doneWords;
        string word;
        for (long long int i = 0; i < n; i++){
            cin >> word;
            academic_words.insert(word);
        }
        int count = 0;
        int m;
        cin >> m;
        long long int firstOccurance = 0, lastOccurance = 0;
        bool firstTime = true;
        for (int i = 0; i < m; i++){
            cin >> word;
            if (academic_words.find(word) != academic_words.end()){
                if (firstTime){
                    firstOccurance = i;
                    firstTime = false;
                }
                if (doneWords.find(word) != doneWords.end()){
                    continue;
                }
                else{
                    lastOccurance = i;
                    count++;
                    doneWords.insert(word);
                }
            }
        }
        cout << count << endl << lastOccurance - firstOccurance + 1 << endl;
    }
}*/




/*
int main(){
    int n;
    while (cin >> n){
        unordered_map <string, int> map;
        unordered_map <string, int> doneMap;
        vector <string> vec;
        string word;
        for (int i = 0; i < n; i++){
            cin >> word;
            map[word] = i;
        }
        int m;
        cin >> m;
        for (int i = 0; i < m; i++){
            cin >> word;
            vec.emplace_back(word);
        }
        bool firstTime = true;
        int firstOccurance = 0, lastOccurance = 0, numWords = 0;
        for (int i = 0; i < m; i++){
            if (map.find(vec[i]) != map.end()){
                lastOccurance = i;
                if (doneMap.find(vec[i]) != doneMap.end()){
                    continue;
                }
                doneMap[vec[i]] = numWords;
                numWords++;
                if (firstTime){
                    firstOccurance = i;
                    firstTime = false;
                }
            }
        }
        cout << numWords << "\n";
        if (lastOccurance == 0){
            cout << 0 << "\n";
        }
        else {
            cout << lastOccurance - firstOccurance + 1 << "\n";
        }
    }
}
/*
using namespace std;
class Word{
public:
    string word;
    bool doneBefore = false;
    Word(string word){
        this->word = word;
    }
    void changeStatus(){
        doneBefore = true;
    }
};

int main(){
    int n;
    while ((scanf("%d", &n)) != EOF){ 
        bool firstTime = true;
        int numWords = 0, firstOccurance = 0, lastOccurance = 0;
        vector <Word> academicWords;
        string word;
        for (int i = 0; i < n; i++){
            cin >> word;
            Word thisWord = Word(word);
            academicWords.emplace_back(thisWord);
        }
        int m;
        cin >> m;
        vector <string> essayWords;
        for (int i = 0; i < m; i++){
            cin >> word;
            essayWords.emplace_back(word);
        }
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if (essayWords[i] == academicWords[j].word){
                    if (academicWords[j].doneBefore){
                        break;
                    }
                    numWords++;
                    lastOccurance = i;
                    academicWords[j].changeStatus();
                    if (firstTime){
                        firstOccurance = i;
                        firstTime = false;
                    }
                    break;
                }
            }
        }
        cout << numWords << "\n";
        int answer = lastOccurance - firstOccurance;
        if (lastOccurance == 0){
            cout << 0 << "\n";
        }
        else {
            cout << lastOccurance - firstOccurance + 1 << "\n";
        }
    }
}*/


/*
3
analyze
initiates
split
14
when
a
nuclear
reaction
initiates
the
old
uranium
nucleus
will
split
into
two
nuclei
3
analyze
initiates
split
6
i
do
not
love
university
english
*/

/*
2
data
structure
6
data
data
data
structure
structure
structure
*/


/*
2
data
structure
8
data
a
a
a
structure
a
a
data
*/

/*
2
data
structure
3
data
a
a
*/

/*
2
data
structure
4
a
data
data
a
*/

/*
2
data
structure
4
data
a
a
data
 */

/*
3
data
structure
algorithm
8
data
structure
data
data
algorithm
data
structure
algorithm
*/

/*
2
data
structure
6
data
structure
data
a
a
structure
*/

/*
3
data
structure
algorithm
5
data
structure
is
data
algorithm
*/