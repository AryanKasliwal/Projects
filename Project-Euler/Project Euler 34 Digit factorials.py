# Runtime = 1.0208s

import time
start_time = time.time()


def factorial_calc(num):
    factorial = 1
    for i in range(1, num+1):
        factorial *= i
    return factorial


sum_of_answers = 0
for i in range(3, 100000):
    sum_for_factorial = 0
    str_num = str(i)
    for j in str_num:
        sum_for_factorial += factorial_calc(int(j))
    if sum_for_factorial == i:
        sum_of_answers += i

print(sum_of_answers)

print("--- %s seconds ---" % (time.time() - start_time))
