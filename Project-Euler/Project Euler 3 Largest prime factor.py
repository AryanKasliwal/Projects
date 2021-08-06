# Runtime = 0.0009s

import time
start_time = time.time()


def Largest_Prime_Factor(n):
    prime_factor = 1
    i = 2

    while i <= n / i:
        if n % i == 0:
            prime_factor = i
            n /= i
        else:
            i += 1

    if prime_factor < n:
        prime_factor = n

    return prime_factor


print(Largest_Prime_Factor(600851475143))

print("--- %s seconds ---" % (time.time() - start_time))