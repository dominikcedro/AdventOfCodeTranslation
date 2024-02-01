import java.io.File
import java.util.regex.Pattern

fun readData(file: String): List<String> {
    return File(file).readLines()
}

fun extractNumbersFromLine(line: String): List<Int> {
    val numbers = Pattern.compile("\\d+").matcher(line).run {
        val nums = mutableListOf<Int>()
        while (find()) {
            nums.add(group().toInt())
        }
        nums
    }
    return numbers
}

fun readRows(data: List<String>, inputParam: Long): Long {
    var input = inputParam
    val improvisedMatrix = mutableListOf<List<Int>>()
    var assigned = false
    var output: Long = 0
    for (line in data) {
        if (data.indexOf(line) < 2) {
            continue
        }

        if (line.any { it.isLetter() }) {
            for (k in 0 until improvisedMatrix.size) {
                output = 0
                val sourceRange = listOf(improvisedMatrix[k][1], improvisedMatrix[k][1] + improvisedMatrix[k][2] - 1)
                if (sourceRange[0] <= input && input <= sourceRange[1]) {
                    if (assigned) {
                        continue
                    }
                    output = input + (improvisedMatrix[k][0] - improvisedMatrix[k][1])
                    assigned = true
                    input = output
                    if (line == "finish") {
                        return output
                    }
                    continue
                } else {
                    if (output == 0L) {
                        output = input
                        input = output
                        if (line == "finish") {
                            return output
                        }
                        continue
                    }
                }
            }
            improvisedMatrix.clear()
            assigned = false
            continue
        } else {
            val newRow = extractNumbersFromLine(line)
            improvisedMatrix.add(newRow)
        }
    }
    return output
}

fun execute() {
    val data = readData("../day5_data.txt")
    val listSeeds = listOf(3082872446L, 316680412L, 2769223903L, 74043323L, 4131958457L, 99539464L, 109726392L, 353536902L, 619902767L,
            648714498L, 3762874676L, 148318192L, 1545670780L, 343889780L, 4259893555L, 6139816L, 3980757676L, 20172062L, 2199623551L,
            196958359L)
    var compareResult = readRows(data, listSeeds[0])
    for (seed in listSeeds) {
        val result = readRows(data, seed)
        if (result < compareResult) {
            compareResult = result
        }
    }
    println("Day 5 task 1 solution: $compareResult")
}

fun main() {
    execute()
}
main()