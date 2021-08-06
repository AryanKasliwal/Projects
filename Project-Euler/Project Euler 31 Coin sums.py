# Runtime = 0.0400s

import time
start_time = time.time()

target = 200
one_p = 1
two_p = 2
five_p = 5
ten_p = 10
twenty_p = 20
fifty_p = 50
one_pound = 100
two_pound = 200
counter = 1
for i in range(3):
    for j in range(1+int((target-i*one_pound)/50)):
        for k in range(1+int((target-i*one_pound-j*fifty_p)/20)):
            for l in range(1+int((target-i*one_pound-j*fifty_p-k*twenty_p)/10)):
                for m in range(1+int((target-i*one_pound-j*fifty_p-k*twenty_p-l*ten_p)/5)):
                    for n in range(1+int((target-i*one_pound-j*fifty_p-k*twenty_p-l*ten_p-m*five_p)/2)):
                        counter += 1

print(counter)

print("--- %s seconds ---" % (time.time() - start_time))