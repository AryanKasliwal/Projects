# Runtime = 0.6150s

import time
import math
start_time = time.time()


def spiral_matrix(size):
    '''dir 0 = moving right
       dir 1 = moving down
       dir 2 = moving left
       dir 3 = moving up'''
    matrix = []
    for i in range(size):
        row = []
        for j in range(size):
            row.append(0)
        matrix.append(row)
    step_size = 1
    dir = 0
    cur_row = int(math.floor(size/2))
    cur_col = int(math.floor(size/2))
    max_row = int(math.floor(size/2))+1
    max_col = int(math.floor(size/2))+1
    min_row = int(math.floor(size/2))-1
    min_col = int(math.floor(size/2))-1
    matrix[cur_row][cur_col] = 1
    number = 2

    while min_row >= 0 and min_col >= 0 and max_row <= size and max_col <= size:
        if min_row == 0 or min_col == 0:
            if dir == 0:
                cur_col += 1
                for i in range(step_size):
                    matrix[cur_row][cur_col] = number
                    number += 1
                    if cur_col != max_col:
                        cur_col += 1
                max_col += 1
                dir += 1
            elif dir == 3:
                cur_row -= 1
                for i in range(step_size):
                    matrix[cur_row][cur_col] = number
                    number += 1
                    if cur_row != min_row:
                        cur_row -= 1
                min_row -= 1
                step_size += 1
                dir = 0

        if dir == 0:
            cur_col += 1
            for i in range(step_size):
                matrix[cur_row][cur_col] = number
                number += 1
                if cur_col != max_col:
                    cur_col += 1
            max_col += 1
            dir += 1

        elif dir == 1:
            cur_row += 1
            for i in range(step_size):
                matrix[cur_row][cur_col] = number
                number += 1
                if cur_row != max_row:
                    cur_row += 1
            max_row += 1
            dir += 1
            step_size += 1

        elif dir == 2:
            cur_col -= 1
            for i in range(step_size):
                matrix[cur_row][cur_col] = number
                number += 1
                if cur_col != min_col:
                    cur_col -= 1
            min_col -= 1
            dir += 1

        elif dir == 3:
            cur_row -= 1
            for i in range(step_size):
                matrix[cur_row][cur_col] = number
                number += 1
                if cur_row != min_row:
                    cur_row -= 1
            min_row -= 1
            step_size += 1
            dir = 0

    return matrix


size = 1001

answer = spiral_matrix(size)
sum = 0
for i in range(size):
    for j in range(size):
        if i == j:
            sum += answer[i][j]
        elif i == size - 1 - j:
            sum += answer[i][j]
sum += size**2
sum += (size**2)-(size - 1)
print(sum)

print("--- %s seconds ---" % (time.time() - start_time))
