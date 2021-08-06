# Runtime = 4.4740s

import time
import math
start_time = time.time()


def get_triangle_num():
    i = 1
    divisors = 0
    triangleNum = 0
    while divisors < 500:
        triangleNum += i
        divisors = get_divisors(triangleNum)
        i += 1
    return triangleNum


def get_divisors(num):
    divisors = 0
    for i in range(1, math.ceil(math.sqrt(num))+1):
        if num % i == 0:
            if num / i == i:
                divisors += 1
            else:
                divisors += 2
    return divisors


print(get_triangle_num())


print("--- %s seconds ---" % (time.time() - start_time))
