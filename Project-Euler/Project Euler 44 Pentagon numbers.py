# Runtime = 2.8589s

import time

start_time = time.time()


def is_pentagonal(num):
    if (1 + (24*num + 1)**0.5) % 6 == 0:
        return True
    return False


cur_index = 1
loop = True
while loop:
    for nums in range(1, cur_index):
        a = cur_index * ((cur_index * 3) - 1) / 2
        b = nums*((nums*3) - 1)/2
        if is_pentagonal(a+b) and is_pentagonal(a-b):
            print(abs(a-b))
            loop = False
    cur_index += 1

print("--- %s seconds ---" % (time.time() - start_time))
