# Runtime = 1.4448s

import time
start_time = time.time()


def pandigital_list_checker(list, n):
    panlist = []
    for i in range(1, n+1):
        panlist.append(i)
    for numbers in list:
        if numbers not in panlist:
            return False
        elif numbers in panlist:
            panlist.remove(numbers)
    if not panlist:
        return True
    else:
        return False


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


pandigital_products = []
for multiplicand in range(100):
    for multiplier in range(100, 10000):
        product = multiplicand * multiplier
        str_multiplicand = str(multiplicand)
        str_multiplier = str(multiplier)
        str_product = str(product)
        panlist = []
        if len(str_multiplicand) + len(str_multiplier) + len(str_product) == 9:
            if product not in pandigital_products:
                for i in str_multiplicand:
                    panlist.append(int(i))
                for i in str_multiplier:
                    panlist.append(int(i))
                for i in str_product:
                    panlist.append(int(i))
                if pandigital_list_checker(panlist, 9):
                    pandigital_products.append(product)

print(sum(pandigital_products))
print("--- %s seconds ---" % (time.time() - start_time))
