# Runtime = 0.0029s

import time
start_time = time.time()


def single_numbers(num):
    single_sum = 0
    if num == 1:
        single_sum = 3
    elif num == 2:
        single_sum = 3
    elif num == 3:
        single_sum = 5
    elif num == 4:
        single_sum = 4
    elif num == 5:
        single_sum = 4
    elif num == 6:
        single_sum = 3
    elif num == 7:
        single_sum = 5
    elif num == 8:
        single_sum = 5
    elif num == 9:
        single_sum = 4
    return single_sum


def double_numbers(num):
    str_num = str(num)
    double_sum = 0
    if num < 10:
        double_sum = single_numbers(num)
    elif num == 10:
        double_sum = 3
    elif num == 11:
        double_sum = 6
    elif num == 12:
        double_sum = 6
    elif num == 13:
        double_sum = 8
    elif num == 14:
        double_sum = 8
    elif num == 15:
        double_sum = 7
    elif num == 16:
        double_sum = 7
    elif num == 17:
        double_sum = 9
    elif num == 18:
        double_sum = 8
    elif num == 19:
        double_sum = 8
    elif num == 20:
        double_sum = 6
    elif int(str_num[-2]) == 0:
        double_sum = single_numbers(int(str_num[-1]))
    elif int(str_num[-2]) == 2 or int(str_num[-2]) == 3 or int(str_num[-2]) == 4 or int(str_num[-2]) == 8 or int(str_num[-2]) == 9:
        double_sum = 6 + single_numbers(int(str_num[-1]))
    elif int(str_num[-2]) == 5 or int(str_num[-2]) == 6:
        double_sum = 5 + single_numbers(int(str_num[-1]))
    elif int(str_num[-2]) == 7:
        double_sum = 7 + single_numbers(int(str_num[-1]))
    return double_sum


def tripple_numbers(num):
    str_num = str(num)
    triple_sum = 0
    if num < 100:
        triple_sum = double_numbers(num)
    elif num == 100:
        triple_sum = 10
    elif num == 200:
        triple_sum = 10
    elif num == 300:
        triple_sum = 12
    elif num == 400:
        triple_sum = 11
    elif num == 500:
        triple_sum = 11
    elif num == 600:
        triple_sum = 10
    elif num == 700:
        triple_sum = 12
    elif num == 800:
        triple_sum = 12
    elif num == 900:
        triple_sum = 11
    elif num == 1000:
        triple_sum = 11
    elif int(str_num[-3]) == 1 or int(str_num[-3]) == 2 or int(str_num[-3]) == 6:
        triple_sum = 13 + double_numbers(int(str_num[-2:]))
    elif int(str_num[-3]) == 4 or int(str_num[-3]) == 5 or int(str_num[-3]) == 9:
        triple_sum = 14 + double_numbers(int(str_num[-2:]))
    elif int(str_num[-3]) == 3 or int(str_num[-3]) == 7 or int(str_num[-3]) == 8:
        triple_sum = 15 + double_numbers(int(str_num[-2:]))
    return triple_sum


answer = 0
for i in range(1, 101):
    print(f'{i} : {tripple_numbers(i)}')
    answer += tripple_numbers(i)

print(answer)

print("--- %s seconds ---" % (time.time() - start_time))
