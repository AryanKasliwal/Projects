# Runtime = 0.0s

from math import factorial as fac

import time
start_time = time.time()


def factorial_sum_calc(n):
    num_str = str(fac(n))
    sum_of_digits = 0
    for items in num_str:
        sum_of_digits += int(items)
    return sum_of_digits


print(factorial_sum_calc(100))

print("--- %s seconds ---" % (time.time() - start_time))
