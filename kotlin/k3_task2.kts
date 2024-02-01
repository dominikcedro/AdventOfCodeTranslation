import java.io.File

fun main() {
    val data = File("../day3_data.txt").readLines()
    val symbols = setOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '-', '=', '{', '}', '[', ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '?', '/', '`', '~')
    var dictSymbols = mutableMapOf<Char, MutableList<List<Int>>>()
    dictSymbols['*'] = mutableListOf()
    var count = 0
    data.forEach { line ->
        val coordinates = mutableListOf<Int>()
        line.forEach { char ->
            count++
            if (char == '*') {
                coordinates.clear()
                coordinates.add(data.indexOf(line))
                coordinates.add(count - 1)
                dictSymbols[char]?.add(coordinates.toList())
                dictSymbols[char]?.sortWith(compareBy({ it[0] }, { it[1] }))
            }
        }
        count = 0
    }

    var countNum = 0
    var dictNumbers = mutableMapOf<Int, MutableList<Int>>()
    data.forEach { line ->
        countNum = 0
        val lineNr = data.indexOf(line)
        line.forEach { char ->
            if (char.isDigit()) {
                if (line.getOrNull(countNum - 1)?.isDigit() == true) {
                    countNum++
                    return@forEach
                }
                if (countNum + 2 != line.length && countNum + 1 != line.length) {
                    val number = line.substring(countNum, countNum + 3).toIntOrNull()
                    if (number != null) {
                        dictNumbers[number] = mutableListOf(lineNr, countNum, countNum + 1, countNum + 2)
                    } else if (line.substring(countNum, countNum + 2).toIntOrNull() != null) {
                        val number = line.substring(countNum, countNum + 2).toInt()
                        dictNumbers[number] = mutableListOf(lineNr, countNum, countNum + 1)
                    } else if (line[countNum].isDigit()) {
                        val number = char.toString().toInt()
                        dictNumbers[number] = mutableListOf(lineNr, countNum)
                    }
                }
            }
            countNum++
        }
    }

    val gearNumbers = mutableListOf<Int>()
    var sumOfGears = 0
    for (nr in 1 until data.size) {
        dictSymbols['*']?.filter { it[0] == nr }?.forEach { symbol ->
            dictNumbers.keys.forEach { number ->
                if (dictNumbers[number]!![0] == symbol[0]) {
                    if (symbol[1] in dictNumbers[number]!! || symbol[1] + 1 in dictNumbers[number]!! || symbol[1] - 1 in dictNumbers[number]!!) {
                        if (gearNumbers.isNotEmpty()) {
                            dictNumbers[number]!!.forEach { coordinates ->
                                if (coordinates in dictNumbers[gearNumbers[0]]!!) {
                                    return@forEach
                                }
                                gearNumbers.add(number)
                                if (gearNumbers.size == 2) {
                                    sumOfGears += (gearNumbers[0] * gearNumbers[1])
                                    gearNumbers.clear()
                                }
                            }
                        } else {
                            gearNumbers.add(number)
                            if (gearNumbers.size == 2) {
                                sumOfGears += (gearNumbers[0] * gearNumbers[1])
                                gearNumbers.clear()
                            }
                        }
                    }
                }
                if (dictNumbers[number]!![0] + 1 == symbol[0] || dictNumbers[number]!![0] - 1 == symbol[0]) {
                    if (symbol[1] in dictNumbers[number]!! || symbol[1] + 1 in dictNumbers[number]!! || symbol[1] - 1 in dictNumbers[number]!!) {
                        gearNumbers.add(number)
                    }
                    if (gearNumbers.size == 2) {
                        sumOfGears += (gearNumbers[0] * gearNumbers[1])
                        gearNumbers.clear()
                    }
                }
            }
        }
    }

    println(sumOfGears)
}
main()