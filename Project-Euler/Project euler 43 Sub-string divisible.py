# Runtime = 23.3505s

import time
from itertools import permutations
start_time = time.time()


def subStringDivisible(str_num):
    divisible = False
    if (int(str_num[1]) * (10**2) + int(str_num[2]) * (10) + int(str_num[3])) % 2 == 0:
        if (int(str_num[2]) * (10**2) + int(str_num[3]) * (10) + int(str_num[4])) % 3 == 0:
            if (int(str_num[3]) * (10 ** 2) + int(str_num[4]) * (10) + int(str_num[5])) % 5 == 0:
                if (int(str_num[4]) * (10 ** 2) + int(str_num[5]) * (10) + int(str_num[6])) % 7 == 0:
                    if (int(str_num[5]) * (10 ** 2) + int(str_num[6]) * (10) + int(str_num[7])) % 11 == 0:
                        if (int(str_num[6]) * (10 ** 2) + int(str_num[7]) * (10) + int(str_num[8])) % 13 == 0:
                            if (int(str_num[7]) * (10 ** 2) + int(str_num[8]) * (10) + int(str_num[9])) % 17 == 0:
                                divisible = True
    return divisible


perms = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

cur_sum = 0
permutations_possible = permutations(perms)
for i in permutations_possible:
    num = ''
    for j in i:
        num += str(j)
    if subStringDivisible(num):
        cur_sum += int(num)

print(cur_sum)


print("--- %s seconds ---" % (time.time() - start_time))