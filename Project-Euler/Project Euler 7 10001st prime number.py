# Runtime = 0.6208s

import time
import math
start_time = time.time()


def is_prime(num):
    if num == 0 or num == 1:
        return False
    elif num == 2:
        return True
    else:
        for i in range(2, math.floor(math.sqrt(num))+1):
            if num % i == 0:
                return False
    return True


count = 0
cur_num = 1
while count < 10001:
    cur_num += 1
    if is_prime(cur_num):
        count += 1

print(cur_num)
print("--- %s seconds ---" % (time.time() - start_time))
