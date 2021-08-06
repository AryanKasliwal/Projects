# Runtime = 0.3530s

import time
start_time = time.time()

dividend = 1
divisor = 1
longest_reciprocal_cycle = 0
divisor_answer = 0
while divisor < 1001:
    count = 0
    remainder_list = []
    remainder = (dividend % divisor) * 10
    while remainder not in remainder_list and remainder != 0:
        remainder_list.append(remainder)
        remainder = (remainder % divisor) * 10
        count += 1
    if count > longest_reciprocal_cycle:
        longest_reciprocal_cycle = count
        divisor_answer = divisor
    divisor += 1
print(divisor_answer)
print(longest_reciprocal_cycle)

print("--- %s seconds ---" % (time.time() - start_time))