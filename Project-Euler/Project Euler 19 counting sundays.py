# Runtime = 0.0239s

import time
start_time = time.time()

sunday_count = 0
day = 1

for month in range(1, 13):
    if month <= 7:
        if month % 2 == 1:
            for date in range(1, 32):
                day += 1
        elif month == 2:
            for date in range(1, 29):
                day += 1
        elif month % 2 == 0:
            for date in range(1, 31):
                day += 1
    elif month > 7:
        if month % 2 == 1:
            for date in range(1, 31):
                day += 1
        else:
            for date in range(1, 32):
                day += 1
print(day)


for year in range(1901, 2001):
    if year % 4 != 0:
        for month in range(1, 13):
            if month <= 7:
                if month % 2 == 1:
                    for date in range(1, 32):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
                elif month == 2:
                    for date in range(1, 29):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
                elif month % 2 == 0:
                    for date in range(1, 31):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
            elif month > 7:
                if month % 2 == 1:
                    for date in range(1, 31):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
                elif month % 2 == 0:
                    for date in range(1, 32):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
    elif year % 4 == 0:
        for month in range(1, 13):
            if month <= 7:
                if month % 2 == 1:
                    for date in range(1, 32):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
                elif month == 2:
                    for date in range(1, 30):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
                elif month % 2 == 0:
                    for date in range(1, 31):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
            elif month > 7:
                if month % 2 == 1:
                    for date in range(1, 31):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1
                elif month % 2 == 0:
                    for date in range(1, 32):
                        day += 1
                        if day % 7 == 0 and date == 1:
                            sunday_count += 1

print(day)
print(f"Number of sundays in 20th century on the first of a month = {sunday_count}")

print("--- %s seconds ---" % (time.time() - start_time))
