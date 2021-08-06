# Runtime = 8.0293s

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


def has_4_prime_divisors(num):
    divisors_list = []
    for i in range(1, math.ceil(math.sqrt(num))):
        if i * i != num:
            if num % i == 0:
                divisors_list.append(i)
                divisors_list.append(num/i)
        elif i * i == num:
            divisors_list.append(i)
    prime_counter = 0
    for i in divisors_list:
        if is_prime(i):
            prime_counter += 1
    return prime_counter >= 4


answer = 0
i = 647
while True:
    if has_4_prime_divisors(i):
        i += 1
        if has_4_prime_divisors(i):
            i += 1
            if has_4_prime_divisors(i):
                i += 1
                if has_4_prime_divisors(i):
                    answer = i-3
                    break
    i += 1
'''
num_set = [644, 645, 646, 647]
while loop:
    cur_loop = [has_4_prime_divisors(x) for x in num_set]
    if all(cur_loop):
        answer = num_set[0]
        loop = False
        break
    elif not all(cur_loop):
        num_set[0] = num_set[1]
        num_set[1] = num_set[2]
        num_set[2] = num_set[3]
        num_set[3] += 1
'''

print(answer)


print("--- %s seconds ---" % (time.time() - start_time))
