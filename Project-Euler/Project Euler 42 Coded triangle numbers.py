# Runtime = 0.0329s

import time

start_time = time.time()

file = open("Project Euler 42.txt")
alpha_dict = {"A":1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'I':9,'J':10,'K':11,'L':12,'M':13,'N':14,'O':15,'P':16,'Q':17,'R':18,'S':19,'T':20,'U':21,'V':22,'W':23,'X':24,'Y':25,'z':26}
words = file.read().replace('"', '').split(',')

triangle_nums = []
for i in range(100):
    triangle_nums.append((i*(i+1))/2)

print(triangle_nums)

count = 0
for word in words:
    cur_num = 0
    for alphabet in word:
        if alphabet in alpha_dict:
            cur_num += alpha_dict[alphabet]
    if cur_num in triangle_nums:
        count += 1

print(count)

print("--- %s seconds ---" % (time.time() - start_time))