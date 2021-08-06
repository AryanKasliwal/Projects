# Runtime = 0.1540s

import time
start_time = time.time()

str_num = ''
cur_num = 1
while len(str_num) < 1000001:
    str_num += str(cur_num)
    cur_num += 1

product = 1
counter = 1
while counter < 2000000:
    product *= int(str_num[counter-1])
    counter *= 10

print(product)
print("--- %s seconds ---" % (time.time() - start_time))