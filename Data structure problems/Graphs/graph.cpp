#include <bits/stdc++.h>

using namespace std;

//undirected graph
class graph
{
  vector<int>*adjacency_list; //array of vectors to store adjacency list
  int Vertices;
  public:
    //constructor
    graph(int n){
      Vertices = n + 1;
      adjacency_list=new vector<int>[Vertices + 1]; //dynamic allocation
    }
  
    void add_edge( int u,int v ){
        adjacency_list[u].push_back(v);
        adjacency_list[v].push_back(u);
    }

    void display_graph(){
        for(int i=0;i<Vertices;i++){
            cout<<"Adjacency list of vertex of vertex "<<i<<endl;
            for(auto it:adjacency_list[i]){
                cout<<it<<" ";
            }
            cout<<endl;
        }
    }
};

int main()
{
    graph g1(5);  //graph of 5 vertices indices- 0 to 4
    //adding edges
    g1.add_edge(0,1); //connect node number 0 to node number 1
    g1.add_edge(1,2); //connect node number 1 to node number 2
    g1.add_edge(1,3); //connect node number 1 to node number 3
    g1.add_edge(2,4); //connect node number 2 to node number 4
    g1.add_edge(2,3); //connect node number 2 to node number 3
    //displaying the graph
    cout<<"The entered Graph is "<<endl;
    g1.display_graph();
    return 0;
}

//function definitions

  