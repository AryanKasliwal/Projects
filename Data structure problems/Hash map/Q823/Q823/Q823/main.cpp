//
//  main.cpp
//  Q823
//
//  Created by Aarnav Gupta on 27/04/21.
//

// Attempt 3

#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <vector>

using namespace std;

int main() {
    int n;
    while(cin >> n) {
        int m, len1 = 0, len2 = 0;
        vector<string> academic_word_list;
        vector<string> essay;
        vector<string> academic_words_present;
        while(n--) {
            string a;
            cin >> a;
            academic_word_list.push_back(a);
        }
        cin >> m;
        while(m--) {
            string a;
            cin >> a;
            if (find(academic_word_list.begin(), academic_word_list.end(), a) != academic_word_list.end())
                if (find(academic_words_present.begin(), academic_words_present.end(), a) == academic_words_present.end()) {
                    academic_words_present.push_back(a);
                    len2++;
                }
            essay.push_back(a);
            len1++;
        }
        if (len2 == 0 or len2 == 1)
            cout << len2 << endl << len2;
        else {
            unordered_map<string, int> D;
            unordered_map<string, int> win;
            
            for (string s: academic_words_present)
                D[s]++;
            
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

// Attempt 2

//#include <algorithm>
//#include <iostream>
//#include <unordered_map>
//#include <vector>
//
//using namespace std;
//
//int main() {
//    int n;
//    while(cin >> n) {
//        int m, len1 = 0;
//        vector<string> academic_word_list;
//        vector<string> essay;
//        vector<string> academic_words_present;
//        while(n--) {
//            string a;
//            cin >> a;
//            academic_word_list.push_back(a);
//        }
//        cin >> m;
//        while(m--) {
//            string a;
//            cin >> a;
//            if (find(academic_word_list.begin(), academic_word_list.end(), a) != academic_word_list.end())
//                if (find(academic_words_present.begin(), academic_words_present.end(), a) == academic_words_present.end())
//                    academic_words_present.push_back(a);
//            essay.push_back(a);
//            len1++;
//        }
//        int len2 = academic_words_present.size(), ans = 100001, start = 0, cnt = 0;
//        unordered_map<string, int> stuff;
//
//        for (int i = 0; i < len2; i++) {
//            if (stuff.find(academic_words_present[i]) == stuff.end())
//                stuff[academic_words_present[i]] = 0;
//            if (stuff[academic_words_present[i]] == 0)
//                cnt++;
//            stuff[academic_words_present[i]]++;
//
//        }
//
//        int i = 0, j = 0;
//
//        while (j < len1) {
//            if (stuff.find(essay[j]) == stuff.end())
//                stuff[essay[j]] = -1;
//            else
//                stuff[essay[j]]--;
//            if (stuff[essay[j]] == 0)
//                cnt--;
//
//            while (cnt == 0) {
//                if (ans > j - i + 1) {
//                    ans = min(ans, j - i + 1);
//                    start = i;
//                }
//
//                stuff[essay[i]]++;
//
//                if (stuff[essay[i]] > 0)
//                    cnt++;
//
//                i++;
//            }
//            j++;
//        }
//
//        cout << len2 << endl << ans;
//    }
//}

// Attempt 1

//#include <algorithm>
//#include <iostream>
//#include <unordered_map>
//#include <vector>
//
//using namespace std;
//
//int main() {
//    int n;
//    while(cin >> n) {
//        int m;
//        vector<string> academic_word_list;
//        vector<string> essay;
//        vector<string> academic_words_present;
//        while(n--) {
//            string a;
//            cin >> a;
//            academic_word_list.push_back(a);
//        }
//        cin >> m;
//        while(m--) {
//            string a;
//            cin >> a;
//            if (find(academic_word_list.begin(), academic_word_list.end(), a) != academic_word_list.end())
//                if (find(academic_words_present.begin(), academic_words_present.end(), a) == academic_words_present.end())
//                    academic_words_present.push_back(a);
//            essay.push_back(a);
//        }
//        int len1 = essay.size(), len2 = academic_words_present.size();
//        if (len2 == 0 or len2 == 1)
//            cout << len2 << endl << len2 << endl;
//        else {
//            unordered_map<string, int> hash_acad;
//            unordered_map<string, int> hash_essay;
//
//            for (int i = 0; i < len2; i++)
//                if (hash_acad.find(academic_words_present[i]) == hash_acad.end())
//                    hash_acad[academic_words_present[i]] = 1;
//                else
//                    hash_acad[academic_words_present[i]]++;
//
//            int start = 0, start_index = -1, min_len = 1000000, cnt = 0;
//            for (int j = 0; j < len1; j++) {
//                if (hash_essay.find(essay[j]) == hash_essay.end())
//                    hash_essay[essay[j]] = 1;
//                else
//                    hash_essay[essay[j]]++;
//                if(hash_essay[essay[j]] <= hash_acad[essay[j]])
//                    cnt++;
//                if (cnt == len2) {
//                    while(hash_essay[essay[start]] > hash_acad[essay[start]] || hash_acad[essay[start]] == 0) {
//                        if (hash_essay[essay[start]] > hash_acad[essay[start]])
//                            hash_essay[essay[start]]--;
//                        start++;
//                    }
//
//                    int len_window = j - start + 1;
//                    if (min_len > len_window) {
//                        min_len = len_window;
//                        start_index = start;
//                    }
//                }
//            }
//
//            if (start_index == -1)
//                cout << 0 << endl << 0 << endl;
//            else
//                cout << len2 << endl << min_len << endl;
//        }
//    }
//}


// Attempt 0

//#include <iostream>
//#include <map>
//
//using namespace std;
//
//int main() {
//    int n, m;
//    string a, b;
//    map<string,int> academic_word_list;
//    while(cin >> n) {
//        while(n--) {
//            cin >> a;
//            academic_word_list[a]++;
//        }
//        cin >> m;
//        int mini = 100000, maxi = -1, cnt = 0;
//        for(int i = 0; i < m; i++) {
//            cin >> b;
//            if(academic_word_list.find(b) != academic_word_list.end()) {
//                cnt++;
//                if(maxi < i)
//                    maxi = i;
//                if(mini > i)
//                    mini = i;
//            }
//        }
//        if(cnt == 0 || cnt == 1) {
//            cout << cnt << endl;
//            cout << cnt;
//        }
//        else {
//            cout << cnt << endl;
//            cout << maxi - mini + 1;
//        }
//    }
//    return 0;
//}

// Kush's code

//#include <unordered_map>
//#include <string>
//#include <iostream>
//
//using namespace std;
//
//int main() {
//
//    long long int academic_words = 0;
//    string tmp;
//
//    while(cin >> academic_words) {
//        unordered_map<string, int> acadwords_list;
//        acadwords_list.clear();
//        long long int num_words = 0;
//
//        for(long long int c = 0; c < academic_words; c++) {
//            cin >> tmp;
//            acadwords_list[tmp] = 0;
//        }
//
//        cin >> num_words;
//
//        long long int max = 0;
//        long long int cnt = 0;
//        long long int tmpcnt = 0;
//        bool started = false;
//
//        for(long long int c = 0; c < num_words; c++) {
//            cin >> tmp;
//            if(acadwords_list.find(tmp) != acadwords_list.end() and not started) {
//                if(acadwords_list.at(tmp) == 0) {
//                    acadwords_list[tmp]++;
//                    started = true;
//                    tmpcnt++;
//                    cnt = 1;
//                    max++;
//                }
//            }
//            else if(started and acadwords_list.find(tmp) != acadwords_list.end()) {
//                tmpcnt++;
//                if(acadwords_list.at(tmp) == 0) {
//                    acadwords_list[tmp]++;
//                    max++;
//                    cnt=tmpcnt;
//                }
//            }
//            else if(started)
//                tmpcnt++;
//            else
//                continue;
//        }
//        cout << max << endl << cnt << endl;
//    }
//}

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
