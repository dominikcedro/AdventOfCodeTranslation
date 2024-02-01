import re

# open the text file inputs/1.txt
def read_input(puzzle):
    with open("../day4_data.txt") as f:
        data = f.read().splitlines()
    return data



counter_dict = {}

for line in read_input('4'):
    # get the line index
    line_index = int(re.findall(r'\d+', line)[0])
    # initialize the counter for the line
    counter_dict[line_index] = 1

for line in read_input('4'):
    # get the line index
    line_index = int(re.findall(r'\d+', line)[0])

    # split the line into two lists
    winning_numbers = [int(x) for x in line.split(':')[1].split('|')[0].split(' ') if x != '']
    my_numbers = [int(x) for x in line.split(':')[1].split('|')[1].split(' ') if x != '']

    # find the intersection of two lists
    my_set = [x for x in my_numbers if x in winning_numbers]

    for i in range(len(my_set)):
        # current index count
        current_index_count = counter_dict[line_index]
        index_of_new_line = line_index + (i + 1)

        counter_dict[index_of_new_line] += 1 * current_index_count

# sum the values of the dictionary
final_sum = sum(counter_dict.values())

print("Day 4 task 2 solution", final_sum)