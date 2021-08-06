# Runtime = 0.0008s

from math import factorial
'''
def lattice_paths_of_n(n):
    list2 = []
    my_list = []
    for i in range(1, n+2):
        list2.append(i)
    for i in range(1, n+2):
        my_list.append(list2)
    for i in range(0,n+1):
        for f in range(0,n+1):
            if f == 0 or i == 0:
                my_list[i][f] = 1
            else:
                my_list[i][f] = my_list[i-1][f]+my_list[i][f-1]
    return my_list[n][n]

print(lattice_paths_of_n(20))
'''

import time
start_time = time.time()

print(factorial(40)/(factorial(20) * factorial(20)))

print("--- %s seconds ---" % (time.time() - start_time))