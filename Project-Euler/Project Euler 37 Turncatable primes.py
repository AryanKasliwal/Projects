# Runtime = 36.9999s

import time
start_time = time.time()


def is_prime(num):
    if num == 1:
        return False
    for i in range(2, num):
        if num % i == 0:
            return False
    return True


def number_checker(num):
    str_num = str(num)
    if not is_prime(int(str_num[0])) or not is_prime(int(str_num[-1])):
        return False
    str_num = str_num[1:-1]
    for nums in str_num:
        if int(nums) % 2 == 0:
            return False
    return True


def is_turncatable_LTR(num):
    left_to_right = True
    str_num_LTR = str(num)
    if not is_prime(num):
        left_to_right = False
    else:
        while len(str_num_LTR) != 1:
            str_num_LTR = str_num_LTR[1:]
            if not is_prime(int(str_num_LTR)):
                left_to_right = False
                break
    return left_to_right


def is_turncatable_RTL(num):
    right_to_left = True
    str_num_RTL = str(num)
    if not is_prime(num):
        right_to_left = False
    else:
        while len(str_num_RTL) != 1:
            str_num_RTL = str_num_RTL[:-1]
            if not is_prime(int(str_num_RTL)):
                right_to_left = False
                break
    return right_to_left


cur_num = 11
count = 0
turncatable_sum = 0
while count < 11:
    if number_checker(cur_num):
        if is_turncatable_LTR(cur_num) and is_turncatable_RTL(cur_num):
            turncatable_sum += cur_num
            count += 1
    cur_num += 1

print(turncatable_sum)
print("--- %s seconds ---" % (time.time() - start_time))
