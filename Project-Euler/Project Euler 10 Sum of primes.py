# Runtime = 0.7201s

import time
start_time = time.time()


def sieve(n):
    prime = [True for i in range(n + 1)]
    p = 2
    prime_list = []
    while p * p <= n:
        if prime[p]:
            for i in range(p * p, n + 1, p):
                prime[i] = False
        p += 1
    for i in range(2, n+1):
        if prime[i]:
            prime_list.append(i)
    return prime_list


print(sum(sieve(2000000)))

print("--- %s seconds ---" % (time.time() - start_time))