import java.io.File

fun main() {
    val data = File("../day3_data.txt").readLines()
    var sumOfParts = 0
    val symbols = setOf('-', '@', '+', '%', '*', '/', '&', '$', '=', '#')
    var dictSymbols = mutableMapOf<Char, MutableList<Int>>()
    symbols.forEach { dictSymbols[it] = mutableListOf() }
    val listOfDictionariesSymbols = mutableListOf<Map<Char, List<Int>>>()
    var count = 0
    data.forEach { line ->
        line.forEach { char ->
            count++
            if (char in symbols) {
                dictSymbols[char]?.let {
                    it.add(count - 1)
                    it.sort()
                }
            }
        }
        count = 0
        listOfDictionariesSymbols.add(dictSymbols)
        dictSymbols = mutableMapOf()
        symbols.forEach { dictSymbols[it] = mutableListOf() }
    }

    var countNum = 0
    val listOfDictionariesNumbers = mutableListOf<Map<Int, List<Int>>>()
    var dictNumbers = mutableMapOf<Int, MutableList<Int>>()
    data.forEach { line ->
        countNum = 0
        val lineNr = data.indexOf(line)
        line.forEach { char ->
            if (char.isDigit()) {
                if (line.getOrNull(countNum - 2)?.isDigit() == true) {
                    countNum++
                    return@forEach
                }
                if (countNum + 1 == line.length) {
                    dictNumbers[char.toString().toInt()] = mutableListOf(countNum - 1)
                    countNum++
                    return@forEach
                }
                if (countNum > 0 && line.substring(countNum - 1, countNum + 2).toIntOrNull() != null) {
                    val number = line.substring(countNum - 1, countNum + 2).toInt()
                    dictNumbers[number] = mutableListOf(countNum - 1, countNum, countNum + 1)
                } else if (countNum > 0 && line.substring(countNum - 1, countNum + 1).toIntOrNull() != null) {
                    val number = line.substring(countNum - 1, countNum + 1).toInt()
                    dictNumbers[number] = mutableListOf(countNum - 1, countNum)
                } else if (countNum > 0 && line[countNum - 1].isDigit() && !line[countNum].isDigit()) {
                    val number = line[countNum - 1].toString().toInt()
                    dictNumbers[number] = mutableListOf(countNum - 1)
                } else if (line[countNum].isDigit() && !line.getOrNull(countNum - 1)?.isDigit()!!) {
                    val number = line[countNum].toString().toInt()
                    dictNumbers[number] = mutableListOf(countNum)
                }
            }
            countNum++
        }
        listOfDictionariesNumbers.add(dictNumbers)
        dictNumbers = mutableMapOf()
    }

    val onLineParts = mutableListOf<Int>()
    for (k in listOfDictionariesNumbers.indices) {
        for (number in listOfDictionariesNumbers[k].keys) {
            for (symbol in listOfDictionariesSymbols[k].keys) {
                if (symbol.toString().toIntOrNull() in listOfDictionariesNumbers[k][number]!!) {
                    continue
                }
                for (position in listOfDictionariesNumbers[k][number]!!) {
                    if (position - 1 in listOfDictionariesSymbols[k][symbol]!! || position + 1 in listOfDictionariesSymbols[k][symbol]!!) {
                        onLineParts.add(number)
                        break
                    }
                }
            }
        }
    }

    val offLineParts = mutableListOf<Int>()
    data.forEachIndexed { index, line ->
        for (number in listOfDictionariesNumbers[index].keys) {
            for (symbol in listOfDictionariesSymbols.getOrNull(index - 1)?.keys ?: emptySet()) {
                for (position in listOfDictionariesNumbers[index][number]!!) {
                    if ((position - 1 in (listOfDictionariesSymbols.getOrNull(index - 1)?.get(symbol)
                                    ?: emptyList()) && position + 1 in listOfDictionariesSymbols[index][symbol]!!) ||
                            (position + 1 in (listOfDictionariesSymbols.getOrNull(index + 1)?.get(symbol)
                                    ?: emptyList()) && position - 1 in listOfDictionariesSymbols[index][symbol]!!)) {
                        offLineParts.add(number)
                        break
                    }
                }
            }
        }
    }

    sumOfParts = onLineParts.sum() + offLineParts.sum()
    println("sum of parts is $sumOfParts")
}
main()