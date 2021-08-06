#include <iostream>
#include <vector>

using namespace std;

class Node {
public:
    int data;
    Node* next;
    Node* bottom;
    Node* prv;
    Node* top;
    Node() {
        prv = nullptr;
        next = nullptr;
        data = 0;
    }
    Node (int data, Node* next, Node* prv) {
        this -> data = data;
        this -> next = next;
    }
};

class LinkedList {
public:
    Node* head;
    Node* tail;
    int len;
    LinkedList() {
        head = nullptr;
        tail = nullptr;
        len = 0;
    }

    void insertData(int data){
        if (head != nullptr){
            Node* newNode = new Node(data, nullptr, tail);
            tail -> next = newNode;
            tail = newNode;
        }
        else{
            Node* newNode = new Node(data, nullptr, nullptr);
            tail = newNode;
            head = newNode;
        }
        len++;
    }

    int size() {
        return len;
    }

    void printList() {
        Node* x = head;
        if (x == nullptr) {
            cout << "Empty\n";
            return;
        }
        while(x != nullptr) {
            cout << x -> data << " ";
            x = x -> next;
        }
        cout << endl;
    }

    void insertAt(int data, int pos) {
        if (pos >= len) insertData(data);
        else if (pos == 0) {
            Node* newNode = new Node(data, head, nullptr);
            head = newNode;
            len++;
        }
        else if (pos > len/2) {
            Node* x = tail;
            for (int i = len-1; i > pos; i--) {
                x = x -> prv;
            }
            Node* store = x;
            x = x -> prv;
            Node* newNode = new Node(data, store, x);
            x -> next = newNode;
            store -> prv = newNode;
            len++;
        }
        else {
            Node* x = head;
            for (int i = 0; i < pos-1; i++) {
                x = x -> next;
            }
            Node* store = x;
            x = x -> next;
            Node* newNode = new Node(data, x, store);
            x -> prv = newNode;
            store -> next = newNode;
            len++;
        }
    }

    void remove(int pos) {
        if (len == 0) {
            cout << "List underflow";
        }
        else {
            Node* x = head;
            if (pos == 0) {
                head = head -> next;
                head -> prv = nullptr;
            }
            else if (pos == len-1) {
                x = tail;
                tail = tail -> prv;
                tail -> next = nullptr;
            }
            else {
                if (pos < len/2)
                    for (int i=0; i < pos; i++)
                        x = x -> next;
                else {
                    x = tail;
                    for (int i=0; i < len-pos-1; i++)
                        x = x -> prv;
                }
                x -> prv -> next = x -> next;
                x -> next -> prv = x -> prv;
            }
            delete(x);
            len--;
        }

    }

    void clear() {
        Node* x = head;
        for (int i = 0; i < len; i++) {
            //deleting(i);
            Node* y = x -> next;
            delete(x);
            Node* x = y;
        }
        head = tail = nullptr;
    }

    bool find(int num) {
        for (Node* x = head; x != tail; x = x -> next)
            if (x -> data == num) return true;
        return false;
    }

    Node* findByPosition(int position){
        Node curr = *head;
        for (int i = 0; i < position; i++){
            curr = *curr.next;
        }
        return &curr;
    }
};

int main(){
    int n, m, q;
    while (cin >> n){
        cin >> m >> q;
        vector <LinkedList> rows;
        for (int i = 0; i < n; i++){
            rows.emplace_back(LinkedList());
            int x;
            for (int j = 0; j < m; j++){
                cin >> x;
                rows[i].insertAt(x, j);
            }
        }
        int r1, c1, r2, c2, h, w;
        for (int i = 0; i < q; i++){
            cin >> r1 >> c1 >> r2 >> c2 >> h >> w;
            for (int j = 0; j < h; j++){
                Node C1 = *rows[r1 + j].head;
                Node C1End = *rows[r1 + j].head;
                for (int k = 0; k < c1 + w - 1; k++){
                    C1End = *C1End.next;
                    if (k < c1 - 1){
                        C1 = *C1.next;
                    }
                }
                Node C2 = *rows[r2 + j].head;
                Node C2End = *rows[r2 + j].head;
                for (int k = 0; k < c2 + w - 1; k++){
                    if (C2End.next != nullptr){
                        C2End = *C2End.next;
                    }
                    else{
                        C2End = Node();
                    }
                    if (k < c2 - 1){
                        C2 = *C2.next;
                    }
                }
                Node* temp = &C1;
                C1 = C2;
                C2 = *temp;
                for (int k = 0; k < c1; k++){
                    C1 = *C1.next;
                }
                C1.next = &C1End;
                for (int k = 0; k < c2; k++){
                    C2 = *C2.next;
                }
                C2.next = &C2End;
            }
        }
    }
}