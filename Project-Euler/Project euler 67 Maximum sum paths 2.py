# Runtime = 0.150s

import time
start_time = time.time()

passed_triangle = [list(map(int, s.split())) for s in open('project Euler 67_triangle.txt').readlines()]
for row in range(len(passed_triangle)-1, 0, -1):
    for element in range(0, row):
        passed_triangle[row-1][element] += max(passed_triangle[row][element], passed_triangle[row][element+1])

print(passed_triangle[0][0])


print("--- %s seconds ---" % (time.time() - start_time))