//
//  main.cpp
//  Q823
//
//  Created by Aarnav Gupta on 27/04/21.
//


#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>

using namespace std;

int main() {
    int n;
    while(cin >> n) {
        int m, len1 = 0, len2 = 0;
        vector<string> essay;
        vector<string> academic_words_present;
        unordered_set<string> set_acad_all;
        unordered_set<string> set_acad_p;
        while(n--) {
            string a;
            cin >> a;
            set_acad_all.insert(a);
        }
        cin >> m;
        len1 = m;
        while(m--) {
            string a;
            cin >> a;
            if (set_acad_all.find(a) != set_acad_all.end()) {
                set_acad_p.insert(a);
            }
            essay.push_back(a);
        }
        len2 = (int) set_acad_p.size();
        if (len2 == 0 or len2 == 1)
            cout << len2 << endl << len2;
        else {
            unordered_map<string, int> D;
            unordered_map<string, int> win;
            
            for (auto i = set_acad_p.begin(); i != set_acad_p.end(); ++i) {
                D[*i]++;
            }
            
            int l = 0, r = 0, formed = 0, req = D.size();
            
            int ans[] = {-1, 0, 0};
            
            while (r < essay.size()) {
                win[essay[r]]++;
                
                if(D.count(essay[r]) && D[essay[r]] == win[essay[r]])
                    formed++;
                
                while(l <= r and formed == req) {
                    win[essay[l]]--;
                    
                    if(ans[0] == -1 or ans[0] > r - l + 1) {
                        ans[0] = r - l +1;
                        ans[1] = l;
                        ans[2] = r;
                    }
                    
                    if(D.count(essay[l]) and D[essay[l]] > win[essay[l]])
                        formed--;
                    l++;
                }
                r++;
            }
            if (ans[0] != -1)
                cout << len2 << endl << ans[0] << endl;
        }
    }
}

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
