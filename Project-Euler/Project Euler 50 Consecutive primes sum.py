

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


def sieve(limit):
    prime_list = []
    primes = [True for i in range(limit+1)]
    p = 2
    while p * p <= limit:
        if primes[p]:
            for i in range(2*p, limit+1, p):
                primes[i] = False
        p += 1
    primes[0] = False
    primes[1] = False
    primes[2] = False
    for i in range(limit+1):
        if primes[i]:
            prime_list.append(i)
    return prime_list


def next_prime(num):
    num += 1
    while not is_prime(num):
        num += 1
    return num


print("--- %s seconds ---" % (time.time() - start_time))
