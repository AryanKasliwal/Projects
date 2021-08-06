# Runtime = 0.5450s

import math
import time
start_time = time.time()


def is_prime(num):
    if num == 0:
        return False
    elif num == 1:
        return False
    elif num == 2:
        return True
    else:
        for i in range(2, math.floor(math.sqrt(num))+1):
            if num % i == 0:
                return False
    return True


a_list = []
b_list = []
for i in range(1000):
    if is_prime(i):
        a_list.append(i)
        a_list.append(-i)
for i in range(-1000, 1001):
    if i < 0:
        b_list.append(i)
    else:
        if is_prime(i):
            b_list.append(i)

longest_count = 0
aXb = 1
for a in a_list:
    for b in b_list:
        n = 0
        answer = (n*n) + (n*a) + b
        if answer > 0:
            while is_prime(answer):
                n += 1
                answer = (n * n) + (n * a) + b
                if answer < 0:
                    break
            if n > longest_count:
                longest_count = n
                aXb = a*b
print(longest_count)
print(aXb)

print("--- %s seconds ---" % (time.time() - start_time))
