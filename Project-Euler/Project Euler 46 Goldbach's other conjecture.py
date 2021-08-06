# Runtime = 1.5809s

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


def conjecture(num):
    cur_primes = sieve(num)
    for i in cur_primes:
        if (num - i) % 2 == 0:
            if math.sqrt((num - i)/2) % 1 == 0:
                return True
    return False


def next_odd_composite(num):
    num += 2
    while is_prime(num):
        num += 2
    return num


def sieve(n):
    primes = [True for i in range(n+1)]
    num = 2
    prime_list = []
    primes[0] = False
    primes[1] = False
    while num*num <= n:
        if primes[num]:
            for i in range(num*2, n + 1, num):
                primes[i] = False
        num += 1
    for i in range(n+1):
        if primes[i]:
            prime_list.append(i)
    return prime_list


number = 3
while conjecture(number):
    number = next_odd_composite(number)


print(number)
print("--- %s seconds ---" % (time.time() - start_time))