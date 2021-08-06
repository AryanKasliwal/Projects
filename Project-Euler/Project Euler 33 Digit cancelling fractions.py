# Runtime = 0.0199s

import time
start_time = time.time()


def digit_cancelling_fraction(numerator, denominator):
    answer = numerator/denominator
    str_numerator = str(numerator)
    str_denominator = str(denominator)
    numerator_list = []
    denominator_list = []
    for i in str_numerator:
        numerator_list.append(i)
    for j in str_denominator:
        denominator_list.append(j)
    for i in numerator_list:
        for j in denominator_list:
            if i == j:
                numerator_list.remove(i)
                denominator_list.remove(j)
                str_numerator = ''
                str_denominator = ''
                for k in numerator_list:
                    str_numerator += k
                for l in denominator_list:
                    str_denominator += l
                if int(str_numerator) != 0 and int(str_denominator) != 0:
                    if int(str_numerator)/int(str_denominator) == answer:
                        return int(str_numerator), int(str_denominator)
    return False


numerator_list = []
denominator_list = []
for numerator in range(11, 100):
    for denominator in range(numerator+1, 100):
        if numerator % 10 != 0 and denominator % 10 != 0:
            if digit_cancelling_fraction(numerator, denominator):
                numerator_list.append(numerator)
                denominator_list.append(denominator)
num_product = 1
den_product = 1
for i in numerator_list:
    num_product *= i
for i in denominator_list:
    den_product *= i

print(num_product/den_product)

print("--- %s seconds ---" % (time.time() - start_time))
