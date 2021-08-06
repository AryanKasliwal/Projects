# Runtime = 0.6382s

import time
import math
start_time = time.time()


def most_occured(seq):
    max_count = 0
    repeated_number = 0
    for i in seq:
        cur_count = seq.count(i)
        if cur_count > max_count:
            max_count = cur_count
            repeated_number = i
    return repeated_number


p_list = []
for a in range(1000):
    for b in range(1000):
        c = math.sqrt((a*a)+(b*b))
        if c % 1 == 0:
            sides = [a, b, c]
            p = a+b+c
            if p <= 1000:
                print(p)
                p_list.append(p)

print(most_occured(p_list))

print("--- %s seconds ---" % (time.time() - start_time))
