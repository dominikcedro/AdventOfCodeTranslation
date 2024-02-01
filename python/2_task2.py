"""
@author: Dominik Cedro
02.12.2023
Advent of Code 2023
"""


def task2():
    ''' This is my solution for the second task for Advent of Code 2023
    This function is given a file with data and returns the sum of all the game IDs, that were possible
    with 12 red, 13 green and 14 blue marbles.

    Returns:
        int: sum of all the game IDs, that were possible with 12 red, 13 green and 14 blue marbles.

    '''
    number = 0
    game_num = 0
    dict_colors = {'red': 0, 'green': 0, 'blue': 0}
    sum_numbers = 0
    with open('../day_2_data.txt', 'r') as f:
        data = f.readlines()
    for line in data:
        for word in line.split():
            if word == 'Game':
                dict_colors = {'red': 0, 'green': 0, 'blue': 0}
                game_num += 1
                continue
            if word.isdigit():
                number = int(word)
            if word.isalpha() or word[:-1].isalpha():
                color = word
                if color in dict_colors:
                    if dict_colors[color] < number != 0:
                        dict_colors[color] = number
                elif color[:-1] in dict_colors:
                    if dict_colors[color[:-1]] < number != 0:
                        dict_colors[color[:-1]] = number
        sum_numbers += (dict_colors['red'] * dict_colors['green'] * dict_colors['blue'])
    return sum_numbers
print(f"Day 2 task 2 solution: {task2()}")