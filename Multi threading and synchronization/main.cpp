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

#define NUM_CRAWLERS 3
#define MAX_BUFFER_SIZE 12
#define MAX_ARTICLE_LEN 50
#define TOTAL_LABEL_CLASSES 13

int LABEL = -1;
int ARTICLES = 0;

unsigned int INTERVAL_A = 0, INTERVAL_B = 0, INTERVAL_C = 0;
bool QUIT = false;

int C[13] = {0};

static pthread_mutex_t mutex, IOmutex = PTHREAD_MUTEX_INITIALIZER;
static sem_t semBuffer;

static std::ofstream file;

void printStatus(std::string str, int id) {
    pthread_mutex_lock(&IOmutex);
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

class Buffer {
private:
    int frontIdx, size;
    char **articles;
public:
    Buffer() {
        articles = new char*[MAX_BUFFER_SIZE];
        for (int i = 0; i < MAX_BUFFER_SIZE; i++)
            articles[i] = new char[MAX_ARTICLE_LEN];
        frontIdx = size = 0;
    }

    ~Buffer() {
        for (int i = 0; i < MAX_BUFFER_SIZE; i++)
            delete articles[i];
        delete[] articles;
    }

    bool isFull() { return size == MAX_BUFFER_SIZE; }

    bool isEmpty() { return size == 0; }
 
    void addArticle(char* a) { articles[size++] = a; }

    int getFrontIdx() const { return frontIdx; }

    int getSize() const { return size; }

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

bool checkForTermination() { return *std::min_element(C, C + TOTAL_LABEL_CLASSES) >= 5; }

bool isLetter(char c) { return !std::isalpha(c); }

void* classifier(void *arg) {
    bool checker = false;
    printStatus("start", 0);
    
    while (!QUIT || !buffer->isEmpty()) {
        if (buffer->isEmpty() && !QUIT) 
            continue;
        pthread_mutex_lock(&mutex);
        char *originalText = buffer->removeArticle();
        printStatus("clfy", 0);
        pthread_mutex_unlock(&mutex);

        std::string copyStr = originalText;

        std::transform(copyStr.begin(), copyStr.end(), copyStr.begin(), ::tolower);
        copyStr.erase(std::remove_if(copyStr.begin(), copyStr.end(), isLetter) , copyStr.end());

        LABEL = int(copyStr[0] - 'a') % 13 + 1;
        C[LABEL - 1]++;
        file << ++ARTICLES << " " << LABEL << " " << originalText << "\n";

        QUIT = checkForTermination();
        if (QUIT) {
            if (!checker) {
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
    printStatus("start", id);

    while(!QUIT) {
        if (sem_trywait(&semBuffer)) {
            printStatus("wait", id);
            sem_wait(&semBuffer);
            printStatus("s-wait", id);

            if (QUIT)
                break;
        }

        printStatus("grab", id);
        usleep(INTERVAL_A);

        pthread_mutex_lock(&mutex);
        buffer->addArticle(str_generator());
        pthread_mutex_unlock(&mutex);

        printStatus("f-grab", id);
    }

    printStatus("quit", id);

    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
    INTERVAL_A = strtoul(argv[1], nullptr, 10);
    INTERVAL_B = strtoul(argv[2], nullptr, 10);

    pthread_t crawlersThreads[NUM_CRAWLERS], classifierThread;
    int classifierID = 0, flag = 0, arg = 0;
    
    file.open("./text_corpus.txt");

    std::cout << std::setw(30) << std::setfill(' ') << std::left << "Crawler-1"
              << std::setw(30) << std::setfill(' ') << std::left << "Crawler-2"
              << std::setw(30) << std::setfill(' ') << std::left << "Crawler-3"
              << std::setw(30) << std::setfill(' ') << std::left << "Classifier\n";

    sem_init(&semBuffer, 0, 12);

    flag = pthread_create(&classifierThread, NULL, classifier, NULL);
    if (flag) {
        std::cout << "Error while creating thread. Exiting...\n";
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < NUM_CRAWLERS; i++) {
        arg = i + 1;
        flag = pthread_create(&crawlersThreads[i], NULL, crawler, (void *)&arg);
        if (flag) {
            std::cout << "Error while creating thread. Exiting...\n";
            exit(EXIT_FAILURE);
        }
    }


    for (int i = 0; i < NUM_CRAWLERS; i++) {
        arg = i + 1;
        flag = pthread_join(crawlersThreads[i], NULL);
        if (flag) {
            std::cout << "Error while joining thread. Exiting...\n";
            exit(EXIT_FAILURE);
        }
    }

    flag = pthread_join(classifierThread, NULL);
    if (flag) {
        std::cout << "Error while joining thread. Exiting...\n";
        exit(EXIT_FAILURE);
    }

    return 0;
}
