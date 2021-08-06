# Runtime = 0.00106s

import time
start_time = time.time()

sum = 0
for element in range(1000):
    if element % 3 == 0 or element % 5 == 0:
        sum += element
print(sum)

print("--- %s seconds ---" % (time.time() - start_time))
