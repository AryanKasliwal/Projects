# Runtime = 57.6180s

import time
start_time = time.time()

abundant_numbers = []
positive_integers = [x for x in range(28123)]
for number in range(1, 28124):
    sum_of_divisors = 0
    for item in range(1, int((number/2)+1)):
        if number % item == 0:
            sum_of_divisors += item
    if sum_of_divisors > number:
        abundant_numbers.append(number)

for i in abundant_numbers:
    for j in abundant_numbers:
        if i + j < 28123:
            positive_integers[i+j] = 0
        else:
            break
print(sum(positive_integers))

print("--- %s seconds ---" % (time.time() - start_time))
