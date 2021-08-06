# Runtime = 0.6159s

import time
import math
start_time = time.time()


def is_palindrome(num):
    return str(num) == str(num)[::-1]


def binary_converter(num):
    binary = ''
    while num != 0:
        binary += str(num % 2)
        num = math.floor(num/2)
    return int(binary)


total = 0
for number in range(1, 1000000):
    if number % 2 == 1:
        if is_palindrome(number) and is_palindrome(binary_converter(number)):
            total += number

print(total)

print("--- %s seconds ---" % (time.time() - start_time))