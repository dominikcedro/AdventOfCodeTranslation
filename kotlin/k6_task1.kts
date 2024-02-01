import java.io.File
import java.util.regex.Pattern

fun readFileGetInput(file: String): List<List<Int>> {
    val data = File(file).readLines()
    val matrix = mutableListOf<List<Int>>()

    for (line in data) {
        val numbers = Pattern.compile("\\d+").matcher(line).run {
            val nums = mutableListOf<Int>()
            while (find()) {
                nums.add(group().toInt())
            }
            nums
        }
        matrix.add(numbers)
    }
    return matrix
}

fun getPairs(matrix: List<List<Int>>): List<List<Int>> {
    val pairs = mutableListOf<List<Int>>()
    for (k in 0 until 4) {
        val column = matrix.map { it[k] }
        pairs.add(column)
    }
    return pairs
}

fun calculatePossible(pairs: List<List<Int>>) {
    val possibles = mutableListOf<Int>()
    for (pair in pairs) {
        val distance = pair[1]
        val time = pair[0]
        var buttonTime1 = 0
        var calculatedDistance = 0
        while (calculatedDistance <= distance) {
            calculatedDistance = buttonTime1 * (time - buttonTime1)
            buttonTime1 += 1
        }
        val rangeBegin = buttonTime1 - 1
        var buttonTime2 = time
        var calculatedDistance2 = 0
        while (calculatedDistance2 <= distance) {
            calculatedDistance2 = buttonTime2 * (time - buttonTime2)
            buttonTime2 -= 1
        }
        val rangeEnd = buttonTime2 + 1
        val possible = rangeEnd - rangeBegin + 1
        possibles.add(possible)
    }
    var result = 1
    for (k in possibles) {
        result *= k
    }
    println(result)
}

fun main() {
    val matrix = readFileGetInput("../day6_data.txt")
    val pairs = getPairs(matrix)
    calculatePossible(pairs)
}
main()