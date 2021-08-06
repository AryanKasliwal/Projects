# Runtime = 0.7630s

import time
start_time = time.time()


def palindrome_number():
    palindrome = 0
    for i in range(100, 1000):
        for j in range(100, 1000):
            prod = i * j
            if str(prod) == str(prod)[::-1]:
                if prod > palindrome:
                    palindrome = prod
    return palindrome

print(palindrome_number())


print("--- %s seconds ---" % (time.time() - start_time))