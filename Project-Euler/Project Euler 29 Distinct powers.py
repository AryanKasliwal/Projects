# Runtime = 0.6566s

import time
start_time = time.time()

power_list = []
for i in range(2, 101):
    for j in range(2, 101):
        if i**j not in power_list:
            power_list.append(i**j)

print(len(power_list))

print("--- %s seconds ---" % (time.time() - start_time))
