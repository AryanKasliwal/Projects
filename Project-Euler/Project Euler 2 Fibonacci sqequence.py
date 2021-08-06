# Runtime = 0.0s

import time
start_time = time.time()

a = 0
b = 1
num = 0
myList = []
for i in range(35):
    myList.append(a)
    num = a + b
    a = b
    b = num
Sum = 0
print(myList)

for item in myList:
    if item < 4000000:
        if item % 2 ==0:
            Sum += item
print(Sum)

print("--- %s seconds ---" % (time.time() - start_time))