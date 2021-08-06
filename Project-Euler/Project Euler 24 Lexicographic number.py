# Runtime = 0.7039s

from itertools import permutations
import time
start_time = time.time()

permutation_list = list(permutations('0123456789'))

print(permutation_list[999999])

print("--- %s seconds ---" % (time.time() - start_time))
