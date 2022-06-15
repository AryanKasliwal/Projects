def remove_jpg(input):
    inputs = input.split(".")
    return inputs[0] + " "

def process_query_image(input_str):
    ans_str = input_str[14:]
    anss = ans_str.split(".")
    return anss[0] + ": "


with open('method1/M1results.txt') as file:
    write_file = open('method1/M1results_final.txt', 'w')
    data = file.read().splitlines()
    for i in data:
        if i == '':
            pass
        elif i[0] == 'Q':
            write_file.write("\n")
            i = process_query_image(i)
            write_file.write(i)
        else:
            for img in i.split(" "):
                write_file.write(remove_jpg(img))