# Runtime = 68.3350s

import time
start_time = time.time()


def collatz_sequence(n):
    longest_sequence = 0
    seq_causer = 0
    for j in range(2, n):
        lengths = 1
        numlist = [j]
        while numlist[-1] != 1:
            if numlist[-1] % 2 == 0:
                numlist.append((numlist[-1])/2)
                lengths += 1
            elif numlist[-1] % 2 == 1:
                numlist.append((numlist[-1]*3)+1)
                lengths += 1
        if lengths > longest_sequence:
            longest_sequence = lengths
            seq_causer = j
    return seq_causer


print(collatz_sequence(1000000))

print("--- %s seconds ---" % (time.time() - start_time))

