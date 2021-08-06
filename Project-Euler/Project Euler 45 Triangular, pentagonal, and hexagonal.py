# Runtime = 0.1041ss

import time
start_time = time.time()


def is_pentagonal(num):
    if (1+(1+24*num)**0.5) % 6 == 0:
        return True
    return False


def is_hexagonal(num):
    if (1+(num*8 + 1)**0.5) % 4 == 0:
        return True
    return False


num = 286
while True:
    triangle_num = num*(num+1)/2
    if is_hexagonal(triangle_num) and is_pentagonal(triangle_num):
        print(triangle_num)
        break
    num += 1


print("--- %s seconds ---" % (time.time() - start_time))