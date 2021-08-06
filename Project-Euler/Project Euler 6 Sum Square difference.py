# Runtime = 0.0s

import time
start_time = time.time()


def Sum_Square_Difference():
    sumSquare = 0
    squareSum = 0
    for item in range(1, 101):
        square = item ** 2
        sumSquare += square
        squareSum += item
    print(squareSum**2 - sumSquare)


Sum_Square_Difference()

print("--- %s seconds ---" % (time.time() - start_time))