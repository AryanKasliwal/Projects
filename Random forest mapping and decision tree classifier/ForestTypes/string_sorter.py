from itertools import permutations

str = "ABCD"
ans_arr = []
possibilities = permutations(str, 4)
for i in possibilities:
    ans_str = ""
    for j in i:
        ans_str += j
    ans_arr.append(ans_str)

print(ans_arr)

print(len(ans_arr))

a = ["AAAA", "AAAB", "AAAC", "AAAD", "AABA", "ABAA", "AACA", "ACAA", "AADA", "ADAA", "AABB", "ABAB", "AACC", "ACAC", "AADD", "ADAD", "ABBB", "ACCC", "ADDD"]
b = ["BAAA", "BABA", "BBAA", "BABB", "BBAB", "BBBA", "BBBB", "BBBC", "BBCB", "BCBB", "BBBD", "BBDB", "BDBB", "BBCC", "BCBC", "BBDD", "BDBD"]
c = ["CAAA", "CACA", "CCAA", "CACC", "CCAC", "CCCA", "CBBB", "CBCB", "CCBB", "CCCD", "CCDC", "CDCC", "CCDD", "CDCD", "CDDD"]
d = ["DAAA", "DADA", "DDAA", "DADD", "DDAD", "DDDA", "DBBB", "DBDB", "DDBB", "DCCC", "DCDC", "DDCC", "DCDD", "DDCD", "DDDC", "DDDD"]

print(len(a) + len(b) + len(c) + len(d))
a.sort()
b.sort()
c.sort()
d.sort()
print(a)
print(b)
print(c)
print(d)