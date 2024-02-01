import java.io.File

fun readFileGetInput(file: String): List<Int> {
    val data = File(file).readLines()
    val timeAndDist = mutableListOf<Int>()
    data.forEach { line ->
        var word = ""
        line.forEach { char ->
            if (char.isDigit()) {
                word += char
            }
        }
        timeAndDist.add(word.toInt())
    }
    return timeAndDist
}

fun calculatePossible(pairs: List<Int>) {
    val possibles = mutableListOf<Int>()
    val distance = pairs[1]
    val time = pairs[0]
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
    var result = 1
    for (k in possibles) {
        result *= k
    }
    println(result)
}

fun execute(file: String) {
    val timeAndDist = readFileGetInput(file)
    calculatePossible(timeAndDist)
}

fun main() {
    execute("../day6_data.txt")
}
main()