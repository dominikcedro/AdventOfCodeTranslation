with open('../day3_data.txt') as f:
    data = f.read().splitlines()

symbols = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-', '=', '{', '}', '[', ']', '|', '\\', ':',
           ';', '"', "'", '<', '>', ',', '?', '/', '`', '~'}
dict_symbols = {
    '*': []
}
count = 0
for line in data:
    coordinates = [data.index(line), 0]
    for char in line:
        count += 1
        if char == '*':
            coordinates = [data.index(line), count - 1]
            dict_symbols[char].append(coordinates)
            dict_symbols[char] = sorted(dict_symbols[char])  # is it really necessary
    count = 0

count_num = 0
dict_numbers = {}
for line in data:
    count_num = 0
    line_nr = data.index(line)
    for char in line:
        if char.isdigit():
            if line[count_num-1].isdigit():
                count_num += 1
                continue
            if not count_num+2 == len(line) and not count_num+1 == len(line):
                if line[count_num].isdigit() and line[count_num + 1].isdigit() and line[count_num + 2].isdigit():
                    number = int(line[count_num:count_num + 3])
                    dict_numbers[number] = [line_nr, count_num, count_num + 1, count_num + 2]
                elif line[count_num].isdigit() and line[count_num+1].isdigit():
                    number = int(line[count_num :count_num + 2])
                    dict_numbers[number] = [line_nr, count_num, count_num+1]
                elif line[count_num].isdigit():
                    number = int(line[count_num])
                    dict_numbers[number] = [line_nr, count_num]
        count_num += 1

gear_numbers = []
count_num = 0

sum_of_gears = 0
for nr in range(len(data)-1):
    if nr == 0:
        continue
    for symbol in dict_symbols['*']:
        if symbol[0] == nr:
            for number in dict_numbers:
                if dict_numbers[number][0] == symbol[0]:
                    if symbol[1] in dict_numbers[number] or symbol[1]+1 in dict_numbers[number] or symbol[1]-1 in dict_numbers[number]:
                        if not len(gear_numbers) == 0:
                            for coordinates in dict_numbers[number]:
                                if coordinates in dict_numbers[gear_numbers[0]]:
                                    continue
                                if coordinates in dict_numbers[gear_numbers[0]]:
                                    gear_numbers.append(number)
                                    if len(gear_numbers) == 2:
                                        sum_of_gears += (gear_numbers[0]*gear_numbers[1])
                                        gear_numbers = []
                                        break
                                else:
                                    gear_numbers = []
                                    break
                        else:
                            gear_numbers.append(number)
                            if len(gear_numbers) == 2:
                                sum_of_gears += (gear_numbers[0]*gear_numbers[1])
                                gear_numbers = []
                                break
                if dict_numbers[number][0]+1 == symbol[0] or dict_numbers[number][0]-1 == symbol[0]:
                    if (symbol[1] in dict_numbers[number] or symbol[1]+1 in dict_numbers[number] or symbol[1]-1 in dict_numbers[number]):
                        gear_numbers.append(number)
                    if len(gear_numbers) == 2:
                        sum_of_gears += (gear_numbers[0]*gear_numbers[1])
                        gear_numbers = []
                        break
                else:
                    continue
        else:
            continue


print(sum_of_gears)