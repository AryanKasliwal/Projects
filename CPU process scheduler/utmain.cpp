//  Assignment 3
//  Created by Utkarsh and Aarnav.

#include <algorithm>
#include <deque>
#include <fstream>
#include <iostream>
#include <queue>
#include <sstream>
#include <stdlib.h>
#include <string>
#include <unordered_map>
#include <vector>

using std::atoi;
using std::cout;
using std::deque;
using std::endl;
using std::ifstream;
using std::ofstream;
using std::ios;
using std::queue;
using std::sort;
using std::string;
using std::stringstream;
using std::to_string;
using std::unordered_map;
using std::vector;

vector<string> break_input(string input);                   // breaking output on basis of spaces
void add_process(vector<string> process_info);              // adding processes to all processes
void populate_processes(string path);                       // extracting all processes from text file
bool FCFS();
bool RR();
bool FB();
int globalClock = 0;
string scheduling;
int start_tick =0;

class Command{
private:
    char command_type;
    int ticks;
    int ticks_completed;
    string mutex_name;
public:
    inline Command(char command_type, string cmd_parameter){            //constructor
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
    inline void tick(){
        ticks_completed++;
    }
    char get_command_type(){
        return command_type;
    }
    inline bool completed(){
        return ticks_completed >= ticks;
    }
};

class Process{
private:
    int process_id;
    int arrival_time;
    int service_time;
    int priority;
    string cpuTimes;
    queue<Command> *commands;
public:
    inline Process(int process_id, int arrival_time, int service_time){
        this->process_id = process_id;
        this->arrival_time = arrival_time;
        this->service_time = service_time;
        this->cpuTimes = "";
        priority = 0;
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
    void tick(){
        this->commands->front().tick();
    }
    
    bool is_a_command_left(){
        return this->commands->size() == 1;
    }
    
    bool operator ==(const Process &process) const {
        return this->process_id == process.process_id;
    }
    
    bool operator !=(const Process &process) const {
        return this->process_id != process.process_id;
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
    
    bool curr_cmd_completed(){                          // shows if the current running command is completed
        return commands->front().completed();
    }
    bool completed(){
        return commands->empty() or (commands->size()==1 and commands->front().completed());
    }
    
    void remove_completed_command(){
        if (not commands->empty())
            this->commands->pop();
    }
    
    int get_priority(){
        return this->priority;
    }
    
    void change_priority(int new_priority){
        this->priority = new_priority;
    }
};
bool compare_processes(Process p1, Process p2);
void output_CPU_times(string path, queue<Process> processes);

deque<Process> RQ0;
deque<Process> RQ1;
deque<Process> RQ2;

unordered_map<int, deque<Process>> RQs = {{0,RQ0},{1,RQ1},{2,RQ2}};     /*mapping all queues according to their priorities */

class Mutex{
private:
    int locked_pid;
    bool dispatch_process;
    queue<Process> processes;
public:
    inline Mutex(){
        this->locked_pid = -1;
        dispatch_process = false;
    }
    inline bool is_locked(){
        return locked_pid >= 0;
    }
    void lock(Process process){
        if (this->is_locked())
            processes.push(process);
        else {
            this->locked_pid = process.get_pid();
        }
    }
    void unlock(Process process){
        this->locked_pid = -1;
        if (not processes.empty()){
            this->locked_pid = this->processes.front().get_pid();
            this->dispatch_process = true;
        }
    }
    void check_blocked_queue(){                 /* checks if a process is ready to be removed from the mutex's queue */
        if (dispatch_process){
            RQs[this->processes.front().get_priority()].push_back(this->processes.front());
            this->processes.pop();
            this->dispatch_process = false;
        }
    }
} mtx;

vector<Process> allProcesses;
deque<Process> keyBoard_interrupt;
deque<Process> disk_interrupt;
queue<Process> completed_processes;

int main(int argc, const char * argv[]) {
    populate_processes(argv[2]);
    sort(allProcesses.begin(), allProcesses.end(), compare_processes);
    scheduling = argv[1];
    if (scheduling.compare("FCFS") == 0)
        FCFS();
    else if (scheduling.compare("RR") == 0)
        RR();
    else if (scheduling.compare("FB") == 0)
        FB();
    output_CPU_times(argv[3], completed_processes);
}

bool compare_processes(Process p1, Process p2){
    if (p1.get_arrival_time() != p2.get_arrival_time())
        return p1.get_arrival_time() < p2.get_arrival_time();
    else
        return p1.get_pid() < p2.get_pid();
}

void populate_processes(string path){
    ifstream inFile;
    inFile.open(path, ios::in);                 //opening a file for reading
    string input;
    while (not inFile.eof()){
        getline(inFile, input);
        vector<string> input_vector = break_input(input);
        if (input_vector[0] == "#")
            add_process(input_vector);
        else
            allProcesses.back().add_command(input_vector);
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
    allProcesses.push_back(process);
}

void check_blocked_queues(deque<Process> &queue_to_check){
    if (not queue_to_check.empty() and queue_to_check.front().curr_cmd_completed()){
        queue_to_check.front().remove_completed_command();
        RQs[queue_to_check.front().get_priority()].push_back(queue_to_check.front());
        queue_to_check.pop_front();
    }
}
void check_blocked_queues(Mutex &mtx){
    mtx.check_blocked_queue();
}

void prepare_ready_queue_for_next_tick(int priority=0){
    if (not RQs[priority].empty() and (RQs[priority].front().completed() or RQs[priority].front().curr_cmd_completed())){
    
        RQs[priority].front().remove_completed_command();
        if (RQs[priority].front().completed()){
            
            RQs[priority].front().add_to_output(start_tick);
            start_tick = globalClock + 1;
            RQs[priority].front().add_to_output(globalClock+1);
            
            if (RQs[priority].front().is_a_command_left())
                mtx.unlock(RQs[priority].front());
            
            completed_processes.push(RQs[priority].front());
            RQs[priority].pop_front();
            
        }
        else{
           
            char curr_cmd = RQs[priority].front().curr_command_type();
            
            if (curr_cmd == 'L'){
                RQs[priority].front().remove_completed_command();
                if (mtx.is_locked()){
                    RQs[priority].front().add_to_output(start_tick);
                    RQs[priority].front().add_to_output(globalClock+1);
                    start_tick = globalClock+1;
                    mtx.lock(RQs[priority].front());
                    RQs[priority].pop_front();
                    return;
                }
                mtx.lock(RQs[priority].front());
                curr_cmd = RQs[priority].front().curr_command_type();
            }
            
            else if (curr_cmd == 'U'){
                RQs[priority].front().remove_completed_command();
                mtx.unlock(RQs[priority].front());
                curr_cmd = RQs[priority].front().curr_command_type();
            }
            if (curr_cmd == 'K'){
                RQs[priority].front().add_to_output(start_tick);
                RQs[priority].front().add_to_output(globalClock+1);
                start_tick = globalClock+1;
                keyBoard_interrupt.push_back(RQs[priority].front());
            }
            else if (curr_cmd == 'D'){
                RQs[priority].front().add_to_output(start_tick);
                RQs[priority].front().add_to_output(globalClock+1);
                start_tick = globalClock+1;
                disk_interrupt.push_back(RQs[priority].front());
            }
            if (curr_cmd != 'C')
                RQs[priority].pop_front();
        }
    }
}
    
bool tick(deque<Process>& queue_to_tick){
    if (not queue_to_tick.empty()){
        queue_to_tick.front().tick();
        return true;
    }
    return false;
}

void add_new_arrived_proccesses(){
    while (not allProcesses.empty() and allProcesses.front().get_arrival_time() == globalClock){
        RQs[0].push_back(allProcesses.front());
        allProcesses.erase(allProcesses.begin());
    }
}

bool FCFS(){
    allProcesses.shrink_to_fit();
    int total_processes = (int) allProcesses.size(), curr_CPU_pid=-1;

    while (total_processes != completed_processes.size() or not allProcesses.empty()){
        add_new_arrived_proccesses();

        check_blocked_queues(disk_interrupt);
        check_blocked_queues(keyBoard_interrupt);
        check_blocked_queues(mtx);
        
        if (RQs[0].empty()){
            start_tick = globalClock + 1;
            curr_CPU_pid = -1;
        }
        
        tick(disk_interrupt);
        tick(keyBoard_interrupt);
        tick(RQs[0]);
        
        if (not RQs[0].empty() and curr_CPU_pid != RQs[0].front().get_pid()){
            start_tick = globalClock;
            curr_CPU_pid = RQs[0].front().get_pid();
        }
       
        prepare_ready_queue_for_next_tick();
        
        globalClock++;
    }
    return true;
}

void check_for_clock_interrupt(int queue_num, int &pid, int &runtime){
    if (not RQs[queue_num].empty()){
        if (pid == RQs[queue_num].front().get_pid()) {
            if (++runtime >= 5) {
                Process process = RQs[queue_num].front();
                
                process.add_to_output(start_tick);
                process.add_to_output(globalClock+1);
                
                RQs[queue_num].pop_front();
                
                start_tick = globalClock+1;
                if (scheduling.compare("FB") == 0){
                    int temp = queue_num < 2 ? queue_num+1 : queue_num;
                    process.change_priority(temp);
                    RQs[temp].push_back(process);
                }
                else RQs[queue_num].push_back(process);
                
                runtime = 0;
            }
        }
        else runtime = 0;
    }
    else runtime=0;
}

bool RR(){
    allProcesses.shrink_to_fit();
    int total_processes = (int) allProcesses.size(), curr_CPU_pid=-1, curr_runtime=0;
    
    while (total_processes != completed_processes.size() or not allProcesses.empty()){
        add_new_arrived_proccesses();
        
        check_blocked_queues(disk_interrupt);
        check_blocked_queues(keyBoard_interrupt);
        mtx.check_blocked_queue();
        
        if (RQs[0].empty()){
            curr_CPU_pid = -1;
            start_tick = globalClock + 1;
        }
        
        tick(disk_interrupt);
        tick(keyBoard_interrupt);
        tick(RQs[0]);
        
        if (not RQs[0].empty() and curr_CPU_pid != RQs[0].front().get_pid()){
            curr_CPU_pid = RQs[0].front().get_pid();
            curr_runtime = 0;
        }
        
        prepare_ready_queue_for_next_tick();
        check_for_clock_interrupt(0, curr_CPU_pid, curr_runtime);
        
        globalClock++;
    }
    return true;
}

bool FB(){
    allProcesses.shrink_to_fit();
    int total_processes = (int) allProcesses.size(), curr_CPU_pid[3]={-1}, curr_runtime[3]={0};
    int ticked = 0;
    
    while (total_processes != completed_processes.size() or not allProcesses.empty()){
        add_new_arrived_proccesses();
        
        check_blocked_queues(disk_interrupt);
        check_blocked_queues(keyBoard_interrupt);
        check_blocked_queues(mtx);
        
        tick(disk_interrupt);
        tick(keyBoard_interrupt);
        if (not RQs[ticked].empty() and curr_CPU_pid[ticked] == RQs[ticked].front().get_pid())
            tick(RQs[ticked]);
        else{
            ticked = 0;
            bool flag = false;
            for (int i=0; i <= 2; i++)
                if (tick(RQs[i])){
                    ticked = i;
                    curr_CPU_pid[ticked] = RQs[ticked].front().get_pid();
                    curr_runtime[ticked] = 0;
                    flag = true;
                    break;
                }
            if (not flag){
                start_tick = globalClock + 1;
                for (int i =0; i < 2; i++)
                    curr_CPU_pid[i] = -1;
            }
        }
        
        prepare_ready_queue_for_next_tick(ticked);
        check_for_clock_interrupt(ticked, curr_CPU_pid[ticked], curr_runtime[ticked]);
        
        globalClock++;
    }
    return true;
}

void output_CPU_times(string path, queue<Process> processes){
    ofstream outfile;
    outfile.open(path, ios::out);
    while (not processes.empty()){
        outfile << "Process " << processes.front().get_pid() << '\n';
        outfile << processes.front().show_output_times() << '\n';
        processes.pop();
    }
    outfile.close();
}
