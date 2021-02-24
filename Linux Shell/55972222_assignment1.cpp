// Name: Aryan Girish Kasliwal
// SID: 55972222
// EID: akasliwal2

#include <iostream>
#include <cstring>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <vector>
#include <string>
#include <iterator>
#include <sstream>

using namespace std;

// Each process is created as an instance of this class. 
class Process{
    public:

    string arguments;
    char * fileName;
    int pid;
    char * command;
    string status = "running";
    Process(int pid, string input){
        stringstream ss(input);
        istream_iterator<string> begin(ss);
        istream_iterator<string> end;
        vector<string> vstrings(begin, end);
        string answer = "";
        for (int j = 2; j < vstrings.size(); j++){
            answer += " ";
            answer += vstrings[j];
        }
        char * command = strtok(strdup(input.c_str()), " \t");
        char * fileName = strtok(NULL, " \t");
        this->pid = pid;
        this->command = command;
        this->fileName = fileName;
        this->arguments = answer;
    }
    void changeStatus(string status){
        this->status = status;
    }
};

int status = 0;
int pid;

// Stores all the currently running and current stopped processes. 
vector <Process> processList;

// Checks for the validity of the input by the user.
bool inputValidity(string input){
    stringstream ss(input);
    istream_iterator<string> begin(ss);
    istream_iterator<string> end;
    vector<string> vstrings(begin, end);
    if (vstrings[0] != "fg" && vstrings[0] != "bg" && vstrings[0] != "list"){
        cout << "Invalid command. Possible commands:\n";
        cout << "fg - Foreground process execution.\n";
        cout << "bg - Background process execution.\n";
        cout << "list - list of all non terminated processes.\n";
        return false;
    }
    else if (vstrings.size() > 20){
        cout << "Too many arguments.\n";
        return false;
    }
    else {
        return true;
    }
}

// Executes the foreground process.
void execute_fg(string input){

    // char * args[5] stores all the arguments to be passed to execvp.
    char * args[5];
    char * ignore = strtok(strdup(input.c_str()), " \t");
    args[0] = strtok(NULL, " \t");
    int i = 0;
    while (args[i] != NULL){
        args[++i] = strtok(NULL, " \t");
    }

    pid = fork();
    setpgid(0, 0);
    processList.push_back(Process(pid, input));
    if (pid > 0){
        // Parent process waits until the child process has finished running.
        waitpid(-1, &status, WUNTRACED);
    }
    else if (pid == 0){
        if (execvp(args[0], args) < 0){
            // All arguments have passed validity and yet execvp has faild. 
            // Only possibility is the file path is wrong.
            cout << "No such file found: "<< args[0] <<".\n";
        }
    }
    else {
        cout << "Fork failed.\n";
    }
}

// Executes the background processes.
void execute_bg(string input){

    // char * args[5] stores all the arguments to be passed to execvp.
    char * args[5];
    char * ignore = strtok(strdup(input.c_str()), " \t");
    args[0] = strtok(NULL, " \t");
    int i = 0;
    while (args[i] != NULL){
        args[++i] = strtok(NULL, " \t");
    }

    pid = fork();
    setpgid(0, 0);
    processList.push_back(Process(pid, input));
    if (pid > 0){
        // Since the process has to run in background, parent process does not wait until the child process has finished running.
    }
    else if (pid == 0){
        if (execvp(args[0], args) < 0){
            // All arguments have passed validity and yet execvp has faild. 
            // Only possibility is the file path is wrong.
            cout << "No such file found: " << args[0] << ".\n";
            return;
        }
    }
    else {
        cout << "Fork failed.\n";
    }
}

// Executes  the list command.
// Lists all currently running or stopped processes.
void execute_list(){
    for (Process p : processList){
        cout << p.pid << ": " << p.status << " " << p.fileName << p.arguments << "\n";
    }
}

// Executes the exit command.
// Kills all the non terminated processes and then exits.
void execute_exit(){
    for (int i = 0; i < processList.size(); i++){
        cout << "Process " << processList[i].pid << " terminated.\n";
        kill(processList[i].pid, SIGTERM);
    }
    cout << "Exited successfully.\n";
}

// Reaps the stopped child processes (Removes all zombir processes).
// Only run to reap the child when SIGCHILD is received.
void reapChild(int sig){
    int i = 0; 
    while (processList[i].pid != pid){
        i++;
    }
    if (processList[i].status != "stopped" && processList[i].status != "terminated"){
        processList.erase(processList.begin() + i);
    }
    if (strcmp(processList[i].command, "fg") == 0){
        if (processList[i].status == "running"){
            cout << "Process " << pid << " completed.\n";
        }
        return;
    }
    else{
        int p = waitpid(-1, &status, WUNTRACED | WNOHANG);
        if (processList[i].status == "running"){
            cout << "Process " << p << " completed.\n";
        }
        return;
    }
}

// Executes the cntrl C command.
// Terminates the last ran process immediately. All other processes continue running.
void execute_terminate(int sig){
    if (processList.back().status == "running" && strcmp(processList.back().command, "fg") == 0){
        cout << "Process " << processList.back().pid << " terminated.\n";
        processList.back().status = "terminated";
        kill(processList.back().pid, SIGTERM);
        processList.erase(processList.end());
    }
}

// Executes the cntrl Z command.
// Stops the last process immediately. All other processes continue running.
void execute_stop(int sig){
    if (processList.back().status == "running" && strcmp(processList.back().command, "fg") == 0){
        cout << "Process " << processList.back().pid << " stopped.\n";
        kill(processList.back().pid, SIGTERM);
        processList.back().status = "stopped";
    }
}

int main(){

    signal(SIGINT, execute_terminate); // Signal for cntrl C command.
    signal(SIGTSTP, execute_stop); // Signal for cntrl Z command.
    signal(SIGCHLD, reapChild); // Signal for a stopped child process.

    while (true){
        string input;
        cout << "sh >";
        getline(cin, input);

        // args contains all input words broken down based on spaces.
        char * args[20]; // Size 20 as mentioned by TA.
        args[0] = strtok(strdup(input.c_str()), " \t");
        int i = 0;
        while (args[i] != NULL){
            args[++i] = strtok(NULL, " \t");
        }
        if (!args[0]){
            // If the user does not enter anything, the terminal just prints a blank and asks for users input again.
            // Note: Could prompt the user to enter a command here but did not to keep the sheel behaviour like a real shell.
            continue;
        }
        else if (strcmp(args[0], "exit") == 0){
            execute_exit();
            return 0;
        }
        else {
            if (inputValidity(input)){
                if (strcmp(args[0], "fg") == 0){
                    execute_fg(input);
                }
                else if (strcmp(args[0], "bg") == 0){
                    execute_bg(input);
                }
                else if (strcmp(args[0], "list") == 0){
                    execute_list();
                }
            }
        }
    }
}