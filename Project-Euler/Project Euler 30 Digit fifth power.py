# Runtime = 1.2429s

import time
start_time = time.time()


def number_contents(num):
    str_num = str(num)
    num_contents = []
    for i in str_num:
        num_contents.append(int(i))
    return num_contents


def fifth_power(num_list):
    sum_of_powers = 0
    for i in num_list:
        sum_of_powers += i**5
    return sum_of_powers


answer_list = []
for i in range(2, 295246):
    list_of_nums = number_contents(i)
    sum_of_digits = fifth_power(list_of_nums)
    if sum_of_digits == i:
        answer_list.append(i)

print(sum(answer_list))


print("--- %s seconds ---" % (time.time() - start_time))
