# Runtime = 0.0057s

import time
start_time = time.time()


def productOfFourDiagonal(List):
    nums = List.split()
    productList1 = []
    print(len(List))
    for element in range(len(nums) -63):
        productList1.append(int(nums[element])*int(nums[element +21])*int(nums[element + 42])*int(nums[element + 63]))
    return productList1


def productOfFourStraight(List):
    nums = List.split()
    productList2 = []
    for elements in range(len(nums) - 60):
        productList2.append(int(nums[elements])*int(nums[elements+20])*int(nums[elements+40])*int(nums[elements+60]))
    return productList2


def productOfFourHorizontal(List):
    nums = List.split()
    productList3 = []
    for item in range(len(nums) - 3):
        productList3.append(int(nums[item])*int(nums[item +1])*int(nums[item+2])*int(nums[item+3]))
    return productList3


def productOfFourRevDiagonal(List):
    nums = List.split()
    productList4 = []
    for items in range(3, len(nums) - 60):
        productList4.append(int(nums[items])*int(nums[items+19])*int(nums[items+38])*int(nums[items+57]))
    return productList4


#myList = '1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100'


row1 = '08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08 '
row2 = '49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00 '
row3 = '81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65 '
row4 = '52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91 '
row5 = '22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80 '
row6 = '24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50 '
row7 = '32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70 '
row8 = '67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21 '
row9 = '24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72 '
row10 = '21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95 '
row11 = '78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92 '
row12 = '16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57 '
row13 = '86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58 '
row14 = '19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40 '
row15 = '04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66 '
row16 = '88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69 '
row17 = '04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36 '
row18 = '20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16 '
row19 = '20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54 '
row20 = '01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48'

myList = row1+row2+row3+row4+row5+row6+row7+row8+row9+row10+row11+row12+row13+row14+row15+row16+row17+row18+row19+row20
product = 0

#print(productOfFourDiagonal(myList))
#print(productOfFourStraight(myList))
#print(productOfFourHorizontal(myList))
#print(productOfFourRevDiagonal(myList))

finalList = productOfFourDiagonal(myList)+productOfFourStraight(myList)+productOfFourHorizontal(myList)+productOfFourRevDiagonal(myList)
print(max(finalList))


print("--- %s seconds ---" % (time.time() - start_time))