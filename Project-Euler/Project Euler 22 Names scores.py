# Runtime = 0.0460s

import time
start_time = time.time()

f = open("Project Euler 22 names scores.txt")
alpha_dict = {"A":1,'B':2,'C':3,'D':4,'E':5,'F':6,'G':7,'H':8,'I':9,'J':10,'K':11,'L':12,'M':13,'N':14,'O':15,'P':16,'Q':17,'R':18,'S':19,'T':20,'U':21,'V':22,'W':23,'X':24,'Y':25,'z':26}
names = sorted(f.read().replace('"', '').split(','))
sum_list = []
answer_list = []
for name in names:
    ans_sum = 0
    for alphabet in name:
        if alphabet in alpha_dict:
            ans_sum += alpha_dict[alphabet]
    sum_list.append(ans_sum)

for item in range(1, len(names)+1):
    answer_list.append(item*sum_list[item-1])

print(sum(answer_list))

print("--- %s seconds ---" % (time.time() - start_time))
