//  Assignment 3
//  Created by Utkarsh and Aarnav on 04/04/21.

#include <iostream>
#include <queue>
#include <stdlib.h>
#include <unordered_map>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <deque>
#include <algorithm>

using std::queue;
using std::cout;
using std::string;
using std::unordered_map;
using std::make_pair;
using std::atoi;
using std::ifstream;
using std::ios;
using std::stringstream;
using std::vector;
using std::endl;
using std::sort;
using std::to_string;
using std::deque;
//using std::auto_ptr;

vector<string> break_input(string input);
void add_process(vector<string> process_info);
void populate_processes();
bool FCFS();
long globalClock = 0;

class Command{
private:
    char command_type;
    int ticks;
    int ticks_completed;
    string mutex_name;
public:
    inline Command(char command_type, string cmd_parameter){
        this->command_type = command_type;
        this->ticks_completed = 0;
        if (command_type != 'L' and command_type != 'U'){
            this->ticks = atoi(cmd_parameter.c_str());
            this->mutex_name = "";
        }
        else{
            this->ticks = 0;
            this->mutex_name = cmd_parameter;
        }
    }
    inline bool tick(){
        if (ticks_completed != ticks)
            this->ticks_completed++;
        else return false;
        return true;
    }
    char get_command_type(){
        return command_type;
    }
    int get_ticks_completed(){
        return this->ticks_completed;
    }
};

class Process{
private:
    int process_id;
    int arrival_time;
    int service_time;
    bool blocked;
    string cpuTimes;
    queue<Command> *commands;
public:
    bool completed;
    inline Process(int process_id, int arrival_time, int service_time){
        this->process_id = process_id;
        this->arrival_time = arrival_time;
        this->service_time = service_time;
        this->completed = false;
        this->blocked = true;
        commands = new queue<Command>;
    }
    inline int get_pid(){
        return this->process_id;
    }
    inline int get_arrival_time(){
        return this->arrival_time;
    }
    void add_command(vector<string> cmd_info){
        this->commands->push(Command(cmd_info[0][0], cmd_info[1]));
    }
    bool tick(){
        bool cmd_tick = this->commands->front().tick();
        if (this->commands->front().get_command_type() == 'C' and this->commands->front().get_ticks_completed() == 1)
            this->add_to_output(globalClock);
        if (not cmd_tick){
            if (this->commands->front().get_command_type() == 'C')
                this->add_to_output(globalClock);
            this->commands->pop();
            if (this->commands->empty())
                this->completed = true;
        }
        return cmd_tick;
    }
    
    bool operator ==(const Process &process) const {
        return this->process_id == process.process_id;
    }
    
    char curr_command_type(){
        return this->commands->front().get_command_type();
    }
    
    void add_to_output(long int num){
        cpuTimes += to_string(num) + " ";
    }
    
    string show_output_times(){
        return this->cpuTimes;
    }
    
    void block(){
        this->blocked = true;
    }
    
    void unblock(){
        this->blocked = false;
    }
    
    bool is_blocked(){
        return this->blocked;
    }
};
bool compare_processes(Process p1, Process p2);
void direct_process(Process process);
void show_process_cpu_time(queue<Process> *processes);

class Mutex{
private:
    int locked_pid;
    queue<Process> *processes;
public:
    inline Mutex(){
        this->processes = new queue<Process>;
        this->locked_pid = -1;
    }
    inline bool is_locked(){
        return locked_pid >= 0;
    }
    void lock(Process process){
        if (this->is_locked())
            processes->push(process);
        else{
            process.tick();
            direct_process(process);
            this->locked_pid = process.get_pid();
        }
    }
    void unlock(Process process){
        if (process.get_pid() == this->locked_pid){
            process.tick();
            direct_process(process);
            if (not processes->empty()){
                this->locked_pid = this->processes->front().get_pid();
                this->processes->front().tick();
                direct_process(this->processes->front());
                this->processes->pop();
            }
        }
    }
} *mtx = new Mutex;

vector<Process> *allProcesses = new vector<Process>;
queue<Process> *low_priority_processes = new queue<Process>;
queue<Process> *medium_priority_processes = new queue<Process>;
deque<Process> *high_priority_processes = new deque<Process>;
deque<Process> *keyBoard_interrupt = new deque<Process>;
deque<Process> *disk_interrupt = new deque<Process>;
queue<Process> *completed_processes = new queue<Process>;

int main(int argc, const char * argv[]) {
    populate_processes();
    sort(allProcesses->begin(), allProcesses->end(), compare_processes);
//    if (strcmp("FCFS", argv[0]))
        FCFS();
    show_process_cpu_time(completed_processes);
}

bool compare_processes(Process p1, Process p2){
    if (p1.get_arrival_time() != p2.get_arrival_time())
        return p1.get_arrival_time() < p2.get_arrival_time();
    else
        return p1.get_pid() < p2.get_pid();
}

void populate_processes(){
    ifstream inFile;
    inFile.open("sample_0.txt", ios::in);
    string input;
    while (not inFile.eof()){
        getline(inFile, input);
        vector<string> input_vector = break_input(input);
        if (input_vector[0] == "#")
            add_process(input_vector);
        else
            allProcesses->back().add_command(input_vector);
    }
    inFile.close();
}

vector<string> break_input(string input){
    vector<string> broken_input;
    stringstream ss(input);
    while (not ss.eof()){
        string word;
        ss >> word;
        broken_input.push_back(word);
    }
    broken_input.shrink_to_fit();
    return broken_input;
}

void add_process(vector<string> process_info){
    Process process(atoi(process_info[1].c_str()), atoi(process_info[2].c_str()), atoi(process_info[3].c_str()));
    allProcesses->push_back(process);
}

void showDq(const deque<Process>* dq){
    int cnt=0;
    for(Process p: *dq)
        cnt++;
    cout << cnt;
}

void direct_process(Process process){
    if (process.completed){
        completed_processes->push(process);
        return;
    }
    char next_command_type = process.curr_command_type();
    if ((int)next_command_type == (int)'C')
        high_priority_processes->push_back(process);
    else if ((int)next_command_type == (int)'K')
        keyBoard_interrupt->push_back(process);
    else if ((int)next_command_type == (int)'D')
        disk_interrupt->push_back(process);
    else if ((int)next_command_type == (int)'L')
        mtx->lock(process);
    else if ((int)next_command_type == (int)'U')
        mtx->unlock(process);
}

void tick(deque<Process> *queue_to_tick){
    if (not queue_to_tick->empty() and not queue_to_tick->front().tick()){
        direct_process(queue_to_tick->front());
        if (queue_to_tick->front() == queue_to_tick->back()){
            queue_to_tick->pop_back();
            queue_to_tick->front().tick();
        }
        else
            queue_to_tick->pop_front();
    }
}

void add_new_arrived_proccesses(){
    while (not allProcesses->empty() and allProcesses->front().get_arrival_time() == globalClock){
        direct_process(allProcesses->front());
        allProcesses->erase(allProcesses->begin());
    }
}

int max(const vector<deque<Process>*> Qs){
    int max_pos=0;
    for (int i=0; i < Qs.size(); i++)
        if (Qs[i]->size() > Qs[max_pos]->size())
            max_pos = i;
    return max_pos;
}

bool FCFS(){
    vector<deque<Process>*> queues;
    allProcesses->shrink_to_fit();
    int total_processes = (int) allProcesses->size();
    
    while (total_processes != completed_processes->size() or not allProcesses->empty()){
        add_new_arrived_proccesses();
//        showDq(high_priority_processes);
        queues.push_back(high_priority_processes);
        queues.push_back(disk_interrupt);
        queues.push_back(keyBoard_interrupt);
        while(not queues.empty()){
            int pos = max(queues);
            tick(queues[pos]);
            queues.erase(queues.begin()+pos);
        }
        globalClock++;
    }
    
    return true;
}

void show_process_cpu_time(queue<Process> *processes){
    while (not processes->empty()){
        printf("Process %d\n",processes->front().get_pid());
        cout << processes->front().show_output_times() << endl;
        processes->pop();
    }
}