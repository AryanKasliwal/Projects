# Runtime = 0.0009s

import time
start_time = time.time()

num = 2**1000
print(num)
myNums = list(map(int, str(num)))
print(myNums)
print(sum(myNums))

print("--- %s seconds ---" % (time.time() - start_time))
