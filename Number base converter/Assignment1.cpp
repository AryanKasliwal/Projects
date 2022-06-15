#include <iostream>
#include<string>
using namespace std;

int main (){
    int num, base, display_num;
    string answer;
    cout << "Input the number in decimal: ";
    cin >> num;
    display_num = num;
    if (num < 0){
        cout << "Error: Input cannot be negative\n";
        return 0;
    }
    cout << "Input the base to convert to: ";
    cin >> base;
    if (2 > base || 16 < base){
        cout << "Error: Base must be 2..16\n";
        return 0;
    }
    if (num == 0){
        answer = "0";
    }
    while (num > 0){
        int remainder = num % base;
        string to_append = to_string(remainder);
        if (remainder == 10){
            to_append = "A";
        }
        else if (remainder == 11){
            to_append = "B";
        }
        else if (remainder == 12){
            to_append = "C";
        }
        else if (remainder == 13){
            to_append = "D";
        }
        else if (remainder == 14){
            to_append = "E";
        }
        else if (remainder == 15){
            to_append = "F";
        }
        answer.insert(0, to_append);
        if (answer.length() > 4){
            break;
        }
        num = num - remainder;
        num /= base;
    }
    if (num != 0){
        cout << "Error: Number too large\n";
    }
    else{
        cout << "Answer:\n";
        cout << "##";
        for (int i = 0; i < to_string(display_num).length(); i++){
            cout << "#";
        }
        cout << "###############";
        for (int i = 0; i < answer.length(); i++){
            cout << "#";
        }
        cout << "#########";
        for (int i = 0; i < to_string(base).length(); i++){
            cout << "#";
        }
        cout << "##";

        cout << endl;

        cout << "# " << display_num << " in decimal is " << answer << " in base " << base << " #\n";

        cout << "##";
        for (int i = 0; i < to_string(display_num).length(); i++){
            cout << "#";
        }
        cout << "###############";
        for (int i = 0; i < answer.length(); i++){
            cout << "#";
        }
        cout << "#########";
        for (int i = 0; i < to_string(base).length(); i++){
            cout << "#";
        }
        cout << "##\n";
    }
}
