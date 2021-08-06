# Runtime = 0.6459s

import time
start_time = time.time()

for a in range(1000):
    for b in range(1000-a):
        c = 1000 - a - b
        if a**2 + b**2 == c**2:
            print(a, b, c)
            print(a*b*c)

print("--- %s seconds ---" % (time.time() - start_time))