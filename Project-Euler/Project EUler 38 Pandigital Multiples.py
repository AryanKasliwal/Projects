# Runtime = 0.0810s

import time
start_time = time.time()


def pandigital_number_checker(num, n):
    str_num = str(num)
    pan_list = []
    for i in range(1, n+1):
        pan_list.append(i)
    for i in str_num:
        if int(i) not in pan_list:
            return False
        elif int(i) in pan_list:
            pan_list.remove(int(i))
    if not pan_list:
        return True
    else:
        return False


pandigital = []
for cur_num in range(10000):
    multiplier = 1
    product = str(cur_num * multiplier)
    while len(product) < 9:
        multiplier += 1
        product += str(cur_num * multiplier)
    if len(product) == 9:
        if pandigital_number_checker(int(product), 9):
            pandigital.append(int(product))

print(max(pandigital))
print("--- %s seconds ---" % (time.time() - start_time))