/*
 * CS3103 Assignment 2
 *
 * Names: Avi Malhotra ; Aryan Kasliwal
 * Student IDs: 55773896 ; 55972222
 *
 * Command to generate executable:
 * "g++ 55773896_55972222.cpp -lpthread -o {filename}"
 *
 * Command to run the assembly file:
 * "./{filename} {Interval A} {Interval B}"
 */

#include <iostream>
#include <pthread.h>
#include <string>
#include <algorithm>
#include <fstream>
#include <iomanip>
#include <unistd.h>
#include <semaphore.h>

#include "generator.cpp"
char *str_generator();

// Global variables set to values given by the professor.
#define NUM_CRAWLERS 3
#define MAX_BUFFER_SIZE 12
#define MAX_ARTICLE_LEN 50
#define TOTAL_LABEL_CLASSES 13

int LABEL = -1;
int ARTICLES = 0;

unsigned int INTERVAL_A = 0, INTERVAL_B = 0;
bool QUIT = false;

int C[13] = {0};

static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER, IOmutex = PTHREAD_MUTEX_INITIALIZER;
static sem_t semBuffer;

static std::ofstream file;

// Outputs the result to the terminal. 
void printStatus(std::string str, int id) {
    // Mutex is used to ensure that only one thread prints to the terminal at one time.
    pthread_mutex_lock(&IOmutex);
    // Prints to the terminal according to the id. The setw function is used to space out the output properly in the terminal.
    switch(id) {
        case 0:
            std::cout << std::left << std::setfill(' ') << std::setw(90) << ' ' << str << std::endl;
            break;
        case 1:
            std::cout << std::left << str << std::endl;
            break;
        case 2:
            std::cout << std::left << std::setfill(' ') << std::setw(30) << ' ' << str << std::endl;
            break;
        case 3:
            std::cout << std::left << std::setfill(' ') << std::setw(60) << ' ' << str << std::endl;
            break;
    }
    pthread_mutex_unlock(&IOmutex);
}

// A Buffer class is defined as queue with the required queue functions.
class Buffer {
private:
    int size;
    char **articles;
public:
    Buffer() {
        articles = new char*[MAX_BUFFER_SIZE];
        for (int i = 0; i < MAX_BUFFER_SIZE; i++)
            articles[i] = new char[MAX_ARTICLE_LEN];
        size = 0;
    }

    ~Buffer() {
        for (int i = 0; i < MAX_BUFFER_SIZE; i++)
            delete articles[i];
        delete[] articles;
    }

    bool isFull() { return size == MAX_BUFFER_SIZE; }

    bool isEmpty() { return size == 0; }
 
    void addArticle(char* a) { articles[size++] = a; }

    char* getArticle(int num) { return articles[num]; }

    char* removeArticle() {
        char *firstArticle = articles[0];
        for (int i = 0; i < size - 1; i++)
            articles[i] = articles[i+1];
        articles[--size] = nullptr;
        return firstArticle;
    }
};

Buffer *buffer = new Buffer();

bool checkForTermination() { 
    int n = *std::min_element(C, C + TOTAL_LABEL_CLASSES);
    return n >= 5; 
    }

bool isLetter(char c) { return !std::isalpha(c); }

void* classifier(void *arg) {
    bool checker = false;

    // Start is printed to the terminal to indicate that the classifier has started processing an article.
    printStatus("start", 0);
    
    // The classifier keeps classifying articles until the buffer is not empty or until the exit condition is not met. 
    while (!QUIT || !buffer->isEmpty()) {
        if (buffer->isEmpty() && !QUIT) 
            continue;
        // Mutex is used to ensure that only one thread prints to the terminal at once.
        pthread_mutex_lock(&mutex);
        printStatus("clfy", 0);
        char *originalText = buffer->removeArticle();
        pthread_mutex_unlock(&mutex);

        std::string copyStr = originalText;

        // Converts all upper case alphabets to lower case.
        std::transform(copyStr.begin(), copyStr.end(), copyStr.begin(), ::tolower);
        // Erases non-alphabetic characters from the article.
        copyStr.erase(std::remove_if(copyStr.begin(), copyStr.end(), isLetter) , copyStr.end());

        // Given formula to classify various articles.
        LABEL = int(copyStr[0] - 'a') % 13 + 1;
        C[LABEL - 1]++;
        // Writes to the file "text-corpus".
        file << ++ARTICLES << " " << LABEL << " " << originalText << "\n";

        QUIT = checkForTermination();
        if (QUIT) {
            if (!checker) {
                // k articles have been classified. All threads will quit at this point.
                printStatus(std::to_string(ARTICLES) + "-enough", 0);
                checker = true;
            }
        }

        usleep(INTERVAL_B);
        printStatus("f-clfy", 0);
        sem_post(&semBuffer);
    }

    printStatus(std::to_string(ARTICLES) + "-stored", 0);
    printStatus("quit", 0);
    file.close();
    return NULL;
}

void* crawler(void *arg) {
    int id = *(int*)arg;
    // Start is printed to the terminal to indicate that the crawler has started reading an article.
    printStatus("start", id);

    while(!QUIT) {
        // If the buffer is not empty, the crawler has to wait for the buffer to be empty. 
        if (sem_trywait(&semBuffer)) {
            // The crawler starts waiting for the buffer to be empty.
            printStatus("wait", id);
            sem_wait(&semBuffer);
            // The crawler stops waiting for the buffer to be empty.
            printStatus("s-wait", id);

            if (QUIT)
                break;
        }
        // Crawler starts to grab an article.
        printStatus("grab", id);
        usleep(INTERVAL_A);

        // Mutex is used to ensure that only one thread can write to the article at once.
        pthread_mutex_lock(&mutex);
        buffer->addArticle(str_generator());
        pthread_mutex_unlock(&mutex);

        // f-grab is printed to the terminal to show that the crawler has added the article to the buffer.
        printStatus("f-grab", id);
    }

    // quit is printed to the terminal to indicate that the crawler has finished its work and will now stop running.
    printStatus("quit", id);

    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
    INTERVAL_A = strtoul(argv[1], nullptr, 10);
    INTERVAL_B = strtoul(argv[2], nullptr, 10);

    pthread_t crawlersThreads[NUM_CRAWLERS], classifierThread;
    int flag, arg = 0;
    
    file.open("text_corpus.txt");

    std::cout << std::setw(30) << std::setfill(' ') << std::left << "Crawler-1"
              << std::setw(30) << std::setfill(' ') << std::left << "Crawler-2"
              << std::setw(30) << std::setfill(' ') << std::left << "Crawler-3"
              << std::setw(30) << std::setfill(' ') << std::left << "Classifier\n";

    sem_init(&semBuffer, 0, 12);

    // Creates the thread for classifier.
    flag = pthread_create(&classifierThread, NULL, classifier, NULL);
    if (flag) {
        // Incase there is an error while creating the thread for classifier.
        std::cout << "Error while creating thread. Exiting...\n";
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < NUM_CRAWLERS; i++) {
        arg = i + 1;
        // Creates a thread for each crawler.
        flag = pthread_create(&crawlersThreads[i], NULL, crawler, (void *)&arg);
        if (flag) {
            // Incase there is an error while creating a thread for crawlers.
            std::cout << "Error while creating thread. Exiting...\n";
            exit(EXIT_FAILURE);
        }
    }


    for (int i = 0; i < NUM_CRAWLERS; i++) {
        arg = i + 1;
        // Used to exit the thread for each crawler.
        flag = pthread_join(crawlersThreads[i], NULL);
        if (flag) {
            // Incase a crawler fails to exit properly.
            std::cout << "Error while joining thread. Exiting...\n";
            exit(EXIT_FAILURE);
        }
    }
    // Used to exit the thread for classifier.
    flag = pthread_join(classifierThread, NULL);
    if (flag) {
        // Incase the classifier fails o exit properly.
        std::cout << "Error while joining thread. Exiting...\n";
        exit(EXIT_FAILURE);
    }

    return 0;
}