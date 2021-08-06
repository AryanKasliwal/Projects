# Runtime = 189.1160s

import time
start_time = time.time()


def is_prime(num):
    for i in range(2, int(num**0.5)+1):
        if num % i == 0:
            return False
    return True


def circular_numbers(num):
    str_num = str(num)
    circular_list = []
    for alpha in str_num:
        str_num += str_num[0]
        str_num = str_num[1:]
        if int(str_num) not in circular_list:
            circular_list.append(int(str_num))
    return circular_list


def is_list_prime(numList):
    for nums in numList:
        if not is_prime(nums):
            return False
    return True


numbers_done = []
primes_below_one_mil = []
count = 0

for i in range(2, 1000000):
    if is_prime(i):
        primes_below_one_mil.append(i)

for number in primes_below_one_mil:
    if number not in numbers_done:
        numbers_done.extend(circular_numbers(number))
        if is_list_prime(circular_numbers(number)):
            count += len(circular_numbers(number))
            print(circular_numbers(number))

print(count)


print("--- %s seconds ---" % (time.time() - start_time))
