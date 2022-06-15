#include<iostream>
#include<string>
#include<cstring>
using namespace std;

class Cell{
    private:
        double v1, v2;
        char op;
        Cell *c1, *c2;
        bool empty, val2 = false, val1 = false, al2 = false, al1 = false;
    
    public:
    //Default constructor setting all members to zero / NULL
        Cell(){
            v1 = 0;
            v2 = 0;
            op = '0';
            c1 = NULL;
            c2 = NULL;
            empty = true;
        }
    //Report whether the Cell is Empty, or directly/indirectly refers to another Empty cell
        bool isEmpty(){
            return empty;
        }
    //Should be called only when isEmpty() is false. It returns the final numeric value of the cell. 
    //Eg. If A1 is 3, and A2 is 2 * A1, then calling the getValue() of Cell A2 will return 6
        double getValue(){
            if (empty == true){
                return NULL;
            }
            else{
                double x1, x2;
                if (v1 == 0 && c1 != NULL){
                    x1 = c1->getValue();
                }
                else if (c1 == NULL && v1 != 0){
                    x1 = v1;
                }
                else if (v1 == 0 && c1 == NULL){
                    x1 = 0;
                }
                if (v2 == 0 && c2 != NULL){
                    x2 = c2->getValue();
                }
                else if (c2 == NULL && v2 != 0){
                    x2 = v2;
                }
                else if (v2 == 0 && c2 == NULL){
                    x2 = 0;
                }
                if (al1 && x1 == NULL){
                    return NULL;
                }
                if (al2 && x2 == NULL){
                    return NULL;
                }
                if (x2 == 0){
                    return x1;
                }
                else if (x1 == 0){
                    return x2;
                }
                if (op == '+'){
                    return x1 + x2;
                }
                else if(op == '-'){
                    return x1 - x2;
                }
                else if (op == '*'){
                    return x1 * x2;
                }
                else if (op == '/'){
                    return x1 / x2;
                }
            }
        }
    //Whether the cells directly/indirectly refers to another Cell c Eg. If A1 is 3, and A2 is 2 * A1. 
    //When you pass A1’s address (pointer) to A2’s refers() function, it will give true
    //refers(Cell *c) also checks for circular references
        bool refers(Cell * c){
            Cell * test = this;
            bool worked = false;
                if (c1 != NULL){
                    if (c1->refers(c) || c1->refers(test)){
                        worked = true;
                    }
                }
                if (c2 != NULL){
                    if (c2->refers(c) || c2->refers(test)){
                        worked = true;
                    }
                }
                if (c2 == c || c1 == c || c == test){
                    worked = true;
                }
            return worked;
        }
    //Update the member variables / pointer using the cstring F. Cell Sheet[26][26] is the array you created in main(). 
    //It is passed to the function to facilitate getting the pointer of the referenced cells. (e.g. &Sheet[0][0] is the address of A1).
    //The Sheet[][] array is accessed in a read-only manner.
        bool setFormula(char * F, Cell Sheet[26][26]){
            v1 = 0;
            v2 = 0;
            c1 = NULL;
            c2 = NULL;
            int counter = 0;
            char first[10], operate[2], second[10];
            for (int i = 0; i < 10; i++){
                first[i] = '\000';
                second[i] = '\000';
            }
            for (int i = 1; i < strlen(F); i++){
                char temp[10];
                int looper = 0;
                while (F[i] != ' ' && i < strlen(F)){
                    temp[looper] = F[i];
                    looper ++;
                    i++;
                }
                temp[looper] = '\000';
                if (counter == 0){
                    for (int j = 0; j < strlen(temp) + 1; j++){
                        first[j] = temp[j]; 
                    }
                }
                else if (counter == 1){
                    for (int j = 0; j < strlen(temp) + 1; j++){
                        operate[j] = temp[j];
                    }
                }
                else if (counter == 2){
                    for(int j = 0; j < strlen(temp) + 1; j++){
                        second[j] = temp[j];
                    }
                }
                counter ++;
            }
            if (first[0] >= 'A'){
                al1 = true;
            }
            else if (first[0] >= '1'){
                val1 = true;
            }
            if (second[0] >= 'A'){
                al2 = true;
            }
            else if (second[0] >= '1'){
                val2 = true;
            }
            if (first[0] > 57){
                Cell * t1;
                int mentionedRow, mentionedCol;
                mentionedRow = first[0] - 'A';
                if (first[2] == '\000'){
                    mentionedCol = first[1] - '1';
                }
                else {
                    mentionedCol = ((first[1] - '1' + 1)*10) + (first[2] - '1');
                }
                c1 = &Sheet[mentionedRow][mentionedCol];
                empty = false;
            }
            else if (first[0] <= 57){
                string temp = "";
                for (int i = 0; i < strlen(first); i++){
                    temp += first[i];
                }
                v1 = stod(temp);
                empty = false;
            }
            op = operate[0];
            if (second[0] > 57){
                val2 = true;
                Cell * t2;
                int mentionedRow, mentionedCol;
                mentionedRow = second[0] - 'A';
                if (second[2] == '\000'){
                    mentionedCol = second[1] - '1';
                }
                else {
                    mentionedCol = ((second[1] - '1' + 1)*10) + (second[2] - '1');
                }
                c2 = &Sheet[mentionedRow][mentionedCol];
                v2 = 0;
                empty = false;
            }
            else if (second[0] <= 57){
                if (second[0] == '\000'){
                    v2 = 0;
                }
                else {
                    val2 = true;
                    string temp = "";
                    for (int i = 0; i < strlen(second); i++){
                        temp += second[i];
                    }
                    v2 = stod(temp);
                    c2 = NULL;
                    empty = false;
                }
            }
        }
};

int main(){
    char firstInput[3];
    char op;
    char nextInputs[15];
    cin >> firstInput >> op;
    if (op != '?'){
        cin.getline(nextInputs, 15);
    }
    Cell sheet[26][26];
    while(strcmp(firstInput, "END") != 0){
        int curRow = firstInput[0] - 'A';
        int curCol;
        if (firstInput[2] == '\000'){
            curCol = firstInput[1] - '1';
        }
        else{
            curCol = ((firstInput[1] - '1' + 1)*10) + (firstInput[2] - '1'); 
        }
        if (op == '?'){
            double curValue = sheet[curRow][curCol].getValue();
            if (curValue == NULL){
                cout << firstInput << " is Empty" << endl;    
            }
            else {
                cout << firstInput << " is " << curValue << endl;
            }
        }
        else if (op == '='){
            bool islooping1 = false, islooping2 = false;
            int mentionedRow1, mentionedRow2, mentionedCol1, mentionedCol2;
            int counter = 0;
            char first[10], operate[2], second[10];
            for (int i = 0; i < 10; i++){
                first[i] = '\000';
                second[i] = '\000';
            }
            for (int i = 1; i < strlen(nextInputs); i++){
                char temp[10];
                int looper = 0;
                while (nextInputs[i] != ' ' && i < strlen(nextInputs)){
                    temp[looper] = nextInputs[i];
                    looper ++;
                    i++;
                }
                temp[looper] = '\000';
                if (counter == 0){
                    for (int j = 0; j < strlen(temp) + 1; j++){
                        first[j] = temp[j]; 
                    }
                }
                else if (counter == 1){
                    for (int j = 0; j < strlen(temp) + 1; j++){
                        operate[j] = temp[j];
                    }
                }
                else if (counter == 2){
                    for(int j = 0; j < strlen(temp) + 1; j++){
                        second[j] = temp[j];
                    }
                }
                counter ++;
            }
            if (first[0] >= 65 && first[0] <= 90){
                mentionedRow1 = first[0] - 'A';
                if (first[2] == '\000'){
                    mentionedCol1 = first[1] - '1';
                }
                else {
                    mentionedCol1 = ((first[1] - '1' + 1) * 10) + (first[2] - '1');
                }
            }
            if (second[0] >= 65 && second[0] <= 90){
                mentionedRow2 = second[0] - 'A';
                if (second[2] == '\000'){
                    mentionedCol2 = second[1] - '1';
                }
                else {
                    mentionedCol2 = ((second[1] - '1' + 1) * 10) + (second[2] - '1');
                }
            }
            if (first[0] >= 65 && first[0] <= 90){
                if (sheet[mentionedRow1][mentionedCol1].refers(&sheet[curRow][curCol])){
                    islooping1 = true;
                }
            }
            if (second[0] >= 65 && second[0] <= 90){
                if (sheet[mentionedRow2][mentionedCol2].refers(&sheet[curRow][curCol])){
                    islooping2 = true;
                }
            }
            if (islooping1 == true || islooping2 == true) {
                cout << firstInput << " ERROR: Circular reference!" << endl;
            }
            else {
                sheet[curRow][curCol].setFormula(nextInputs, sheet);
            }
        }
        cin >> firstInput;
        if (strcmp(firstInput, "END") == 0){
            break;
        }
        cin >> op;
        if (op != '?'){
            cin.getline(nextInputs, 15);
        }
    }
}