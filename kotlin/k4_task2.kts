import java.io.File
import java.util.regex.Pattern

fun main() {
    val data = File("../day4_data.txt").readLines()
    val counterDict = mutableMapOf<Int, Int>()

    for (line in data) {
        val lineIndex = Pattern.compile("\\d+").matcher(line).run {
            find()
            group().toInt()
        }
        counterDict[lineIndex] = 1
    }

    for (line in data) {
        val lineIndex = Pattern.compile("\\d+").matcher(line).run {
            find()
            group().toInt()
        }

        val splitLine = line.split(":")[1].split("|")
        val winningNumbers = splitLine[0].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val myNumbers = splitLine[1].split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

        val mySet = myNumbers.filter { it in winningNumbers }

        for (i in mySet.indices) {
            val currentIndexCount = counterDict[lineIndex]!!
            val indexOfNewLine = lineIndex + (i + 1)

            counterDict[indexOfNewLine] = counterDict.getOrDefault(indexOfNewLine, 0) + 1 * currentIndexCount
        }
    }

    val finalSum = counterDict.values.sum()

    println("Day 4 task 2 solution: $finalSum")
}
main()