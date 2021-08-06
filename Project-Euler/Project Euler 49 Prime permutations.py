# Runtime = 0.0508s

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


def find_ap(list):
    ap_list = []
    for i in range(len(list)):
        for j in range(i+1, len(list)):
            k = list[j] + (list[j] - list[i])
            if k in list:
                ap_list.append([list[i], list[j], k])
    return ap_list


def sieve(limit):
    primes = [True for i in range(limit+1)]
    prime_list = []
    p = 2
    while p * p <= limit:
        if primes[p]:
            for i in range(2*p, limit+1, p):
                primes[i] = False
        p += 1
    primes[0] = False
    primes[1] = False
    for i in range(limit):
        if primes[i]:
            prime_list.append(i)
    return prime_list


def are_pandigital(nums):
    contains_1 = []
    contains_2 = []
    contains_3 = []
    str_3 = str(nums[2])
    str_2 = str(nums[1])
    str_1 = str(nums[0])
    for i in str_1:
        contains_1.append(int(i))
    contains_1.sort()
    for i in str_2:
        contains_2.append(int(i))
    contains_2.sort()
    for i in str_3:
        contains_3.append(int(i))
    contains_3.sort()
    if contains_1 == contains_2 and contains_1 == contains_3:
        return True
    else:
        return False


primes = sieve(10000)
for i in primes:
    if i > 1000:
        if i + 3330 in primes:
            if i + 3330 + 3330 in primes:
                if are_pandigital([i, i + 3330, i + 3330 + 3330]):
                    print(i)
                    print(i + 3330)
                    print(i + 3330 + 3330)


print("--- %s seconds ---" % (time.time() - start_time))