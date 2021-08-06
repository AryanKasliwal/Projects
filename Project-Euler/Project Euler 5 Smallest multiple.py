# Runtime = 0.1121s

import time
start_time = time.time()

check_list = [11, 13, 14, 16, 17, 18, 19, 20]


def find_solution(step):
    for num in range(step, 999999999, step):
        if all(num % n == 0 for n in check_list):
            return num
    return None


if __name__ == '__main__':
    solution = find_solution(2520)
    if solution is None:
        print("No answer found")
    else:
        print(f"found an answer: {solution}")

print("--- %s seconds ---" % (time.time() - start_time))