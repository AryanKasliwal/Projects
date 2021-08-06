# Runtime = 0.0387s

import time
start_time = time.time()

adder = 0
for i in range(1, 1001):
    adder += i**i

strs = str(adder)
print(strs[-10:])

print("--- %s seconds ---" % (time.time() - start_time))
