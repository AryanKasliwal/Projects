#include<iostream>
#include<list>

using namespace std;

class Node { 
    public:
    int data; 
    Node* next; 
    Node* prev; 
}; 

void insertBegin(struct Node** start, int value){

    struct Node *last = (*start)->prev;
    struct Node* new_node = new Node;
    new_node->data = value;
    new_node->next = *start;
    new_node->prev = last;
    last->next = (*start)->prev = new_node;
    *start = new_node;
}

void insertAfter(struct Node** start, int value1, int value2){

    struct Node* new_node = new Node;
    new_node->data = value1;
    struct Node *temp = *start;
    while (temp->data != value2)
        temp = temp->next;
    struct Node *next = temp->next;
    temp->next = new_node;
    new_node->prev = temp;
    new_node->next = next;
    next->prev = new_node;
}

void insertEnd(struct Node** start, int value){

    if (*start == NULL)
    {
        struct Node* new_node = new Node;
        new_node->data = value;
        new_node->next = new_node->prev = new_node;
        *start = new_node;
        return;
    }
    Node *last = (*start)->prev;
    struct Node* new_node = new Node;
    new_node->data = value;
    new_node->next = *start;
    (*start)->prev = new_node;
    new_node->prev = last;
    last->next = new_node;
}

Node* deleteNode(Node *to_be_deleted){
    to_be_deleted->prev->next = to_be_deleted->next;
    to_be_deleted->next->prev = to_be_deleted->prev;
    to_be_deleted = to_be_deleted->next;
    return to_be_deleted;
}

int main(){
    
    int n, m;
    cin >> n >> m;
    Node *head = NULL;
    Node *currentNode = head;
    int done = 0, count = 1;
    for (int i = 0; i < n; i++){
        if ((i + 1) % m == 0){
            cout << i + 1 << " ";
            done++;
            count = 1;
        }
        else{
            if (i == 0){
                insertEnd(&head, i + 1);
                currentNode = head->next;
                count++;
            }
            else if (i == n - 1){
                insertBegin(&head, i + 1);
                currentNode = currentNode->next;
            }
            else {
                insertAfter(&head, i + 1, currentNode->data);
                currentNode = currentNode->next;
                count++;
            }
        }
    }

    while (done < n){
        if (count == m){
            count = 1;
            if (done == n - 1){
                cout << currentNode->data << "\n";    
            }
            else{
                cout << currentNode->data << " ";
            }
            currentNode = deleteNode(currentNode);
            done++;
        }
        else{
            currentNode = currentNode->next;
            count ++;
        }
    }
}