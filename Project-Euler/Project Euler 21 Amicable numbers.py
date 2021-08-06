# Runtime = 7.8045s

import time
import math
start_time = time.time()


def divisors_of(n):
    divisors_of_n = [1]
    for i in range(2, math.floor(math.sqrt(n))+1):
        if n % i == 0:
            divisors_of_n.append(i)
            if i != n/i:
                divisors_of_n.append(n/i)
    return divisors_of_n


def is_amicable(n):
    sum_divisors_ofN = sum(divisors_of(n))
    final_sum = sum(divisors_of(sum_divisors_ofN))
    if final_sum == n and sum_divisors_ofN != n:
        return n
    else:
        return 0


amicable_list = []
for i in range(10000):
    if i not in amicable_list:
        if is_amicable(i):
            amicable_list.append(i)

print(amicable_list)
print(sum(amicable_list))

print("--- %s seconds ---" % (time.time() - start_time))
