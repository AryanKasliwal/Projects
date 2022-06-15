// CS3103 Assignment 3 by Aryan Girish Kasliwal
// SID: 55972222


#include <iostream>
#include <vector>
#include <string>
#include <deque>
#include <fstream>
#include <sstream>
#include <algorithm>

using namespace std;


// This function is only used to split the string that is read in from the given file.
vector <string> string_split(const string& str) {
	vector<string> result;
	istringstream iss(str);
	for (string s; iss >> s; )
		result.push_back(s);
	return result;
}

// Every service in a process is an instance of this class.
class Service{
public:

    // variable to store the command this service executes.
    string command;

    // variable to store the amount of time this service runs for.
    int ticks;

    Service(string input){
        vector <string> inputs = string_split(input);
        string command = inputs[0];
        int ticks;
        if (command == "L" || command == "U"){
            ticks = 0;
        }
        else{
            ticks = stoi(inputs[1]);
        }
        this->command = command;
        this->ticks = ticks;
    }

    // functuon used to run the current service.
    void run(){
        this->ticks--;
    }
};

// Every process is an instance of this class.
class Process{
public:

    // queuePriority is only used in feedback scheduling.
    int queuePriority = 0;

    // vector that stores the start and stop timings of this instance in the CPU.
    vector <int> timeStamps;
    int pid, arrivalTime, numServices;

    // Deque services stores the services required by this process.
    deque <Service> services;
    Process(string input, ifstream &in){
        vector <string> inputs = string_split(input);
        int pid = stoi(inputs[1]), arrivalTime = stoi(inputs[2]), numServices = stoi(inputs[3]);
        this->pid = pid;
        this->arrivalTime = arrivalTime;
        this->numServices = numServices;
        for (int i = 0; i < numServices; i++){
            getline(in, input);
            services.emplace_back(Service(input));
        }
    }

    // Runs the first service of the current process.
    void runProcess(){
        this->services.front().run();
    }

    // Function used to add a start or a stop number into the timeStamps vector to note the CPU runtime.
    void addTimeStamp(int num){
        timeStamps.emplace_back(num);
    }
};

// Can make a mutex class if required to make multiple mutexes. But since the assignment specified that one mutex will be given, did not make a class.
// The mutex structure is implemented below from line 93 - 100.
bool mutexStatus = false;
void mutexLock(){
    mutexStatus = true;
}
void mutexUnlock(){
    mutexStatus = false;
}

// Global counter for storing the starting time of a process.
int startTick = 0;

// Global tick counter.
int tick = 0;
deque <Process> mutexQueue;
deque <Process> readyQueue;
deque <Process> doneQueue;
deque <Process> keyboardBlocked;
deque <Process> diskBlocked;
deque <Process> processQueue;

// Global counter to note the runtime of a process. Only used in RR and FB.
int runtime = 0;


// Moves processes from one queue to another.
void moveProcess(deque <Process> &from, deque <Process> &to){
    to.emplace_back(from.front());
    from.pop_front();
}

// Checks if a process in keyboard blocked queue has finished its keyboard ticks.
void schedule_keyboard_blocked(){
    if (keyboardBlocked.front().services.front().ticks == 0){
        keyboardBlocked.front().services.pop_front();
        moveProcess(keyboardBlocked, readyQueue);
    }
}

// Checks if a process in disk blocked queue has finished its disk ticks.
void schedule_disk_blocked(){
    if (diskBlocked.front().services.front().ticks == 0){
        diskBlocked.front().services.pop_front();
        moveProcess(diskBlocked, readyQueue);
    }
}

bool lastCommandU = false;

// Checks if the runtime of a process hs exceeded 5 ticks. If so, moves it to the back of ready queue. Only used in RR.
void check_rutime(){
    if (runtime == 5){
        readyQueue.front().addTimeStamp(startTick);
        readyQueue.front().addTimeStamp(tick + 1);
        startTick = tick + 1;
        runtime = 0;
        moveProcess(readyQueue, readyQueue);
    }
}
bool checkRuntime = true;

// Function used to look ahead for the next service.
void check_next_service(){
    if (readyQueue.empty()){
        return;
    }
    if (readyQueue.front().services.front().command == "D"){
        // notes the time stamps, sets runtime to 0, moves process from ready queue to disk blocked queue.
        readyQueue.front().addTimeStamp(startTick);
        readyQueue.front().addTimeStamp(tick + 1);
        startTick = tick + 1;
        runtime = 0;
        checkRuntime = false;
        moveProcess(readyQueue, diskBlocked);
    }
    else if (readyQueue.front().services.front().command == "K"){
        // notes the time stamps, sets runtime to 0, moves process from ready queue to keyboard blocked queue.
        readyQueue.front().addTimeStamp(startTick);
        readyQueue.front().addTimeStamp(tick + 1);
        startTick = tick + 1;
        runtime = 0;
        checkRuntime = false;
        moveProcess(readyQueue, keyboardBlocked);
    }
    else if (readyQueue.front().services.front().command == "L"){
        readyQueue.front().services.pop_front();
        if (mutexStatus){
            // If mutex is locked then it notes the time stamps, sets runtime to 0, moves process from ready queue to mutex blocked queue.
            readyQueue.front().addTimeStamp(startTick);
            readyQueue.front().addTimeStamp(tick + 1);
            startTick = tick + 1;
            runtime = 0;
            checkRuntime = false;
            moveProcess(readyQueue, mutexQueue);
        }
        else{
            // If mutex is not locked, it locks the mutex.
            mutexLock();
        }
        // Recursively calls the same function until a K, D or a C is encountered.
        check_next_service();
    }
    else if (readyQueue.front().services.front().command == "U"){
        lastCommandU = true;
        readyQueue.front().services.pop_front();
        if (readyQueue.front().services.size() == 0){
            // If it is the last service of the process then it notes the time stamps, sets runtime to 0 and puts the process in done queue.
            readyQueue.front().addTimeStamp(startTick);
            startTick = tick + 1;
            runtime = 0;
            readyQueue.front().addTimeStamp(tick + 1);
            moveProcess(readyQueue, doneQueue);
        }
        // Recursively calls the same function until a K, D or a C is encountered.
        check_next_service();
    }
    if (checkRuntime){
        // runtime is only checked for processes running the command C. For processes that have encountered a D or a K, checkRuntime has been set to false.
        check_rutime();
    }
}

// Handles the unlocking of mutex.
void remove_from_mutex(){
    if (mutexQueue.empty()){
        // If mutex queue is empty, the unlock the mutex.
        mutexUnlock();
    }
    else{
        // If mutex queue is not empty then keep the mutex locked and put the first process of the mutex in to the ready queue.
        moveProcess(mutexQueue, readyQueue);
    }
}

// Writes to the provided output file path.
int write(const char* output_path){
    ofstream outputfile;
    outputfile.open(output_path);
    int size = doneQueue.size();
    for (int i = 0; i < size; i++){
        outputfile << "process " << doneQueue.front().pid << endl;
        for (int j = 0; j < doneQueue.front().timeStamps.size(); j++){
            outputfile << doneQueue.front().timeStamps[j] << " ";
        }
        outputfile << endl;
        doneQueue.pop_front();
    }
    outputfile.close();
    return 0;
}

// Checks of a service has come to end, if not, checks its runtime.
void check_end_of_service(){
    if (readyQueue.front().services.front().ticks == 0){
        // If a service has come to end, pop the service and check if the process has completely finished.
        readyQueue.front().services.pop_front();
        if (readyQueue.front().services.size() == 0){
            // If the process has completely finished, record the time stamps and move it to the done queue.
            readyQueue.front().addTimeStamp(startTick);
            startTick = tick + 1;
            readyQueue.front().addTimeStamp(tick + 1);
            moveProcess(readyQueue, doneQueue);
        }
        else {
            // If the process has not finished, then check for the next service requested by the process.
            check_next_service();
        }
    }
    else{
        // If the service has not come to end, check for the runtime of the process.
        check_rutime();
    }
}

// Function is used to sort the processes based on their PIDs.
bool comparePid(Process p1, Process p2){
    return p1.pid < p2.pid;
}

// Function used to check for new processes at the beginning of each tick.
void check_for_new_process(){
    // Stores all the new processes in a vector.
    vector <Process> newProcesses;
    while (processQueue.front().arrivalTime == tick){
        newProcesses.emplace_back(processQueue.front());
        processQueue.pop_front();
        if (processQueue.empty()){
            break;
        }
    }
    // Sorts the processes in the order of their PID.
    sort(newProcesses.begin(), newProcesses.end(), comparePid);
    // Pushes the processes into the ready queue after sorting in ascending order of PID.
    for (int i = 0; i < newProcesses.size(); i++){
        readyQueue.emplace_back(newProcesses[i]);
    }
    newProcesses.clear();
}

// First Come First Serve scheduler
void FCFSscheduler(const char* output_path){

    int totalProcesses = processQueue.size();
    bool firstProcess = true;
    bool previouslyEmpty = false;

    int curProcessPID = -1, prevProcessPID = -1;

    for (int i = 0; i < 1000; i++){
        
        // Step 1: Checks if any new processes have arrived.
        check_for_new_process();
        if (lastCommandU){
            lastCommandU = false;
            remove_from_mutex();
        }

        // Step 2: Executes the blocked queues.
        if (!keyboardBlocked.empty()){
            keyboardBlocked.front().runProcess();
        }

        if (!diskBlocked.empty()){
            diskBlocked.front().runProcess();
        }

        if (readyQueue.empty()){
            curProcessPID = -1;
            startTick = tick + 1;
            previouslyEmpty = true;
        }
        else{
            // Runs the process in CPU.
            curProcessPID = readyQueue.front().pid;
            if (firstProcess){
                prevProcessPID = curProcessPID;
                firstProcess = false;
            }
            if (curProcessPID != prevProcessPID){
                startTick = tick;
                runtime = 0;
            }
            readyQueue.front().runProcess();

            // Step 3: Looks ahead for the next service as well as for the end of this service.
            check_end_of_service();
            prevProcessPID = curProcessPID;
        }
        if (!diskBlocked.empty()){
            schedule_disk_blocked();
        }
        if (!keyboardBlocked.empty()){
            schedule_keyboard_blocked();
        }
        if (doneQueue.size() == totalProcesses){
            break;
        }
        tick++;
    }
    write(output_path);
}

// Round robin scheduler.
void RRscheduler(const char* output_path){

    int totalProcesses = processQueue.size();
    bool firstProcess = true;
    bool previouslyEmpty = false;

    int curProcessPID = -1, prevProcessPID = -1;

    for (int i = 0; i < 1000; i++){
        
        // Step 1: Checks if any new processes have arrived.
        check_for_new_process();
        if (lastCommandU){
            lastCommandU = false;
            remove_from_mutex();
        }

        // Step 2: Executes the blocked queues.
        if (!keyboardBlocked.empty()){
            keyboardBlocked.front().runProcess();
        }

        if (!diskBlocked.empty()){
            diskBlocked.front().runProcess();
        }

        if (readyQueue.empty()){
            curProcessPID = -1;
            startTick = tick + 1;
            previouslyEmpty = true;
        }
        else{
            // Runs the process in CPU.
            curProcessPID = readyQueue.front().pid;
            if (firstProcess){
                prevProcessPID = curProcessPID;
                firstProcess = false;
            }
            if (curProcessPID != prevProcessPID){
                startTick = tick;
                runtime = 0;
            }
            runtime++;
            readyQueue.front().runProcess();

            // Step 3: Looks ahead for the next service as well as for the end of this service.
            check_end_of_service();
            prevProcessPID = curProcessPID;
        }
        if (!diskBlocked.empty()){
            schedule_disk_blocked();
        }
        if (!keyboardBlocked.empty()){
            schedule_keyboard_blocked();
        }
        if (doneQueue.size() == totalProcesses){
            break;
        }
        tick++;
    }
    write(output_path);
}

// The three priority queues as mentioned in the assignment.
deque <Process> readyQueue0;
deque <Process> readyQueue1;
deque <Process> readyQueue2;
// The execution queue that is used to run the processes. A process is only added to this queue when another process ends.
// Everytime a process is added to this queue, it is added based on the priority of the queues.
deque <Process> runningProcesses;

// Function used to check for new processes at the beginning of each tick.
void FB_check_for_new_processes(){
    // Store all the new processes in a vector.
    vector <Process> newProcesses;
    while (processQueue.front().arrivalTime == tick){
        newProcesses.emplace_back(processQueue.front());
        processQueue.pop_front();
        if (processQueue.empty()){
            break;
        }
    }
    // Sorts the processes in the order of their PID.
    sort(newProcesses.begin(), newProcesses.end(), comparePid);
    // Pushes the processes into the ready queue after sorting in ascending order of PID.
    for (int i = 0; i < newProcesses.size(); i++){
        readyQueue0.emplace_back(newProcesses[i]);
    }
    newProcesses.clear();
}

// Function to add process to the execution queue based on its priority.
void add_process_to_queue(){
    if (!readyQueue0.empty()){
        moveProcess(readyQueue0, runningProcesses);
    }
    else if (!readyQueue1.empty()){
        moveProcess(readyQueue1, runningProcesses);
    }
    else if (!readyQueue2.empty()){
        moveProcess(readyQueue2, runningProcesses);
    }
}

// Function used to move a process back to the same queue as it was in before getting blocked.
void FB_move_process(deque <Process> &queue, int num){
    if (num == 0){
        moveProcess(queue, readyQueue0);
    }
    else if (num == 1){
        moveProcess(queue, readyQueue1);
    }
    else{
        moveProcess(queue, readyQueue2);
    }
}

// Function used to demote a process to a lower priority when it finishes its time quantum.
void demoteProcess(){
    if (runningProcesses.front().queuePriority == 0){
        runningProcesses.front().queuePriority++;
        moveProcess(runningProcesses, readyQueue1);
    }
    else if (runningProcesses.front().queuePriority == 1){
        runningProcesses.front().queuePriority++;
        moveProcess(runningProcesses, readyQueue2);
    }
    else{
        moveProcess(runningProcesses, readyQueue2);
    }
}

// Checks if the runtime of a process hs exceeded 5 ticks. If so, the process gets demoted.
void FB_check_runtime(){
    if (runtime == 5){
        runningProcesses.front().addTimeStamp(startTick);
        runningProcesses.front().addTimeStamp(tick + 1);
        startTick = tick + 1;
        runtime = 0;
        demoteProcess();
    }
}

// Function used to look ahead for the next service.
void FB_check_next_service(){
    if (runningProcesses.empty()){
        return;
    }
    if (runningProcesses.front().services.front().command == "D"){
        // Notes the time stamps, sets runtime to 0, moves process from execution queue to disk blocked queue.
        runningProcesses.front().addTimeStamp(startTick);
        runningProcesses.front().addTimeStamp(tick + 1);
        startTick = tick + 1;
        runtime = 0;
        checkRuntime = false;
        moveProcess(runningProcesses, diskBlocked);
    }
    else if (runningProcesses.front().services.front().command == "K"){
        // Notes the time stamps, sets runtime to 0, moves process from execution queue to keyboard blocked queue.
        runningProcesses.front().addTimeStamp(startTick);
        runningProcesses.front().addTimeStamp(tick + 1);
        startTick = tick + 1;
        runtime = 0;
        checkRuntime = false;
        moveProcess(runningProcesses, keyboardBlocked);
    }
    else if (runningProcesses.front().services.front().command == "L"){
        runningProcesses.front().services.pop_front();
        if (mutexStatus){
            // If mutex is locked then it notes the time stamps, sets runtime to 0, moves process from execution queue to mutex blocked queue.
            runningProcesses.front().addTimeStamp(startTick);
            runningProcesses.front().addTimeStamp(tick + 1);
            startTick = tick + 1;
            runtime = 0;
            checkRuntime = false;
            moveProcess(runningProcesses, mutexQueue);
        }
        else{
            // If mutex is not locked, it locks the mutex.
            mutexLock();
        }
        // Recursively calls the same function until a K, D or a C is encountered.
        FB_check_next_service();
    }
    else if (runningProcesses.front().services.front().command == "U"){
        mutexUnlock();
        lastCommandU = true;
        runningProcesses.front().services.pop_front();
        if (runningProcesses.front().services.size() == 0){
            // If it is the last service of the process then it notes the time stamps, sets runtime to 0 and puts the process in done queue.
            runningProcesses.front().addTimeStamp(startTick);
            runningProcesses.front().addTimeStamp(tick + 1);
            startTick = tick + 1;
            runtime = 0;
            moveProcess(runningProcesses, doneQueue);
            //add_process_to_queue();
        }
        // Recursively calls the same function until a K, D or a C is encountered.
        FB_check_next_service();
    }
    if (checkRuntime){
        // runtime is only checked for processes running the command C. For processes that have encountered a D or a K, checkRuntime has been set to false.
        FB_check_runtime();
    }
}

// Checks of a service has come to end, if not, checks its runtime.
void FB_check_end_of_service(){
    if (runningProcesses.front().services.front().ticks == 0){
        // If a service has come to end, pop the service and check if the process has completely finished.
        runningProcesses.front().services.pop_front();
        if (runningProcesses.front().services.size() == 0){
            // If the process has completely finished, record the time stamps and move it to the done queue.
            runningProcesses.front().addTimeStamp(startTick);
            startTick = tick + 1;
            runningProcesses.front().addTimeStamp(tick + 1);
            moveProcess(runningProcesses, doneQueue);
            add_process_to_queue();
        }
        else{
            // If the process has not finished, then check for the next service requested by the process.
            FB_check_next_service();
        }
    }
    else{
        // If the service has not come to end, check for the runtime of the process.
        FB_check_runtime();
    }
}

// Checks if a process in keyboard blocked queue has finished its keyboard ticks.
void FB_schedule_keyboard_blocked(){
    if (keyboardBlocked.front().services.front().ticks == 0){
        keyboardBlocked.front().services.pop_front();
        // If a process has finished, it adds it back to the same queue as earlier.
        FB_move_process(keyboardBlocked, keyboardBlocked.front().queuePriority);
    }
}

// Checks if a process in disk blocked queue has finished its disk ticks.
void FB_schedule_disk_blocked(){
    if (diskBlocked.front().services.front().ticks == 0){
        diskBlocked.front().services.pop_front();
        // If a process has finished, it adds it back to the same queue as earlier.
        FB_move_process(diskBlocked, diskBlocked.front().queuePriority);
    }
}

void FBscheduler(const char* output_path){

    int totalProcesses = processQueue.size();
    bool previouslyEmpty = false;
    bool firstProcess = true;

    int curProcessPID = -1, prevProcessPID = -1;

    for (int i = 0; i < 1000; i++){

        // Step 1: Checks if any new processes need to be added.
        FB_check_for_new_processes();
        if (firstProcess){
            add_process_to_queue();
        }
        if (runningProcesses.empty()){
            curProcessPID = -1;
            previouslyEmpty = true;
            add_process_to_queue();
        }

        // Step 2: Executes the blocked queues.
        if (!keyboardBlocked.empty()){
            keyboardBlocked.front().runProcess();
            FB_schedule_keyboard_blocked();
        }
        if (!diskBlocked.empty()){
            diskBlocked.front().runProcess();
            FB_schedule_disk_blocked();
        }
        if (!mutexStatus){
            if (!mutexQueue.empty()){
                mutexLock();
                FB_move_process(mutexQueue, mutexQueue.front().queuePriority);
                if (runningProcesses.empty()){
                    add_process_to_queue();
                }
            }
        }

        if (runningProcesses.empty()){
            curProcessPID = -1;
            startTick = tick + 1;
            previouslyEmpty = true;
        }
        else{
            // Run the process in CPU.
            curProcessPID = runningProcesses.front().pid;
            if (firstProcess){
                prevProcessPID = curProcessPID;
                firstProcess = false;
            }
            if (curProcessPID != prevProcessPID){
                startTick = tick;
                runtime = 0;
            }
            runtime ++;
            runningProcesses.front().runProcess();
            
            // Step 3: Looks ahead for the next service as well as for the end of this service.
            FB_check_end_of_service();
            prevProcessPID = curProcessPID;
        }
        if (doneQueue.size() == totalProcesses){
            break;
        }
        tick++;
    }
    write(output_path);
}

int main(int argc, char * argv[]){
    string line;
    if(argc != 4){
		cout << "Incorrect inputs: too few arugments" << endl;
		return 0;
	}
    const char* schedulingMethod = argv[1];
    const char* process_path = argv[2];
    const char* output_path = argv[3];
    ifstream in (process_path);
    while (getline(in, line)){
        processQueue.emplace_back(Process(line, in));
    }
    string schedulingType = schedulingMethod;
    if (schedulingType == "FCFS"){
        FCFSscheduler(output_path);
    }
    else if (schedulingType == "RR"){
        RRscheduler(output_path);
    }
    else if (schedulingType == "FB"){
        FBscheduler(output_path);
    }
}
