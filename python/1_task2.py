

def find_digits_create_list(string):
    digit_list = []
    for i in string:
        if i.isdigit():
            digit_list.append(int(i))
    return digit_list

def get_first_and_last_digit(digit_list):
    digit1 = int(digit_list[0])
    digit2 = int(digit_list[len(digit_list)-1])
    number = digit1 * 10 + digit2
    return number

def read_file():
    file = "../day_1_data.txt"
    sum = 0
    with open(file) as f:
        for line in f:
            sum += get_first_and_last_digit(find_digits_create_list(line))
    print(f"Day 1 task 2 solution: {sum}")
read_file()