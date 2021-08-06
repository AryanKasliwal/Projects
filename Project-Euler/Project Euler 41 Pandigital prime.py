# Runtime = 2.1481s

import time
from itertools import permutations
start_time = time.time()


def pandigital_number_checker(num, n):
    str_num = str(num)
    pan_list = []
    for a in range(1, n+1):
        pan_list.append(a)
    for j in str_num:
        if int(j) not in pan_list:
            return False
        elif int(j) in pan_list:
            pan_list.remove(int(j))
    if not pan_list:
        return True
    else:
        return False


def is_prime(num):
    for k in range(2, int(num**0.5)+1):
        if num % k == 0:
            return False
    return True


largest_prime_pandigital = 0
perms = []
nums = []
for i in range(1, 10):
    perms.append(i)
    permutations_possible = permutations(perms)
    for j in permutations_possible:
        count = i-1
        cur_num = 0
        for k in j:
            cur_num += k * (10 ** count)
            count -= 1
        nums.append(cur_num)

for i in nums:
    if is_prime(i):
        largest_prime_pandigital = i

print(largest_prime_pandigital)
print("--- %s seconds ---" % (time.time() - start_time))
