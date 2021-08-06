# Runtime = 0.2159s

import time
start_time = time.time()


def fibonacci_sequence(n):
    a = 1
    b = 1
    fibonacci_list = [a, b]
    length_of_fibonacci_number = 0
    while length_of_fibonacci_number != n:
        next_term = a+b
        fibonacci_list.append(a+b)
        length_of_fibonacci_number = len(str(next_term))
        a = b
        b = next_term
    print(fibonacci_list)
    for i in range(len(fibonacci_list)):
        print(i+1)


print(fibonacci_sequence(1000))

print("--- %s seconds ---" % (time.time() - start_time))
