import java.io.File

fun readData(file: String): MutableMap<Int, MutableList<List<String>>> {
    val data = File(file).readLines()
    val dictionaryGames = mutableMapOf<Int, MutableList<List<String>>>()
    var word = ""
    data.forEach { line ->
        word = ""
        var userNums = mutableListOf<String>()
        var lotteryNums = mutableListOf<String>()
        line.forEach { char ->
            word += char
            if (word == "Card   ${data.indexOf(line) + 1}: ") {
                dictionaryGames[data.indexOf(line) + 1] = mutableListOf(listOf((data.indexOf(line) + 1).toString()))
                word = ""
            }
            if (word == "Card  ${data.indexOf(line) + 1}: ") {
                dictionaryGames[data.indexOf(line) + 1] = mutableListOf(listOf((data.indexOf(line) + 1).toString()))
                word = ""
            }
            if (word == "Card ${data.indexOf(line) + 1}: ") {
                dictionaryGames[data.indexOf(line) + 1] = mutableListOf(listOf((data.indexOf(line) + 1).toString()))
                word = ""
            }
            if (char == '|') {
                word = word.dropLast(2)
                userNums = word.split(" ").toMutableList()
                dictionaryGames[data.indexOf(line) + 1]?.add(userNums)
                word = ""
            }
        }
        lotteryNums = word.split(" ").toMutableList()
        dictionaryGames[data.indexOf(line) + 1]?.add(lotteryNums)
    }
    return dictionaryGames
}

fun cleanDictionary(dictionaryGames: MutableMap<Int, MutableList<List<String>>>): MutableMap<Int, MutableList<List<String>>> {
    for (key in dictionaryGames.keys) {
        val userNums = dictionaryGames[key]!![1].toMutableList()
        val lotteryNums = dictionaryGames[key]!![2].toMutableList()
        userNums.removeAll { it == "" }
        lotteryNums.removeAll { it == "" }
    }
    return dictionaryGames
}

fun calculatePoints(dictionaryGames: MutableMap<Int, MutableList<List<String>>>): Int {
    var sumOfPoints = 0
    for (key in dictionaryGames.keys) {
        val userNums = dictionaryGames[key]!![1]
        val lotteryNums = dictionaryGames[key]!![2]
        var points = 0
        for (num in userNums) {
            if (num in lotteryNums) {
                points = if (points == 0) 1 else points * 2
            }
        }
        sumOfPoints += points
    }
    return sumOfPoints
}

fun main() {
    val dictionaryGames = readData("../day4_data.txt")
    cleanDictionary(dictionaryGames)
    val solution = calculatePoints(dictionaryGames)
    println("Day 4 task 1 solution: $solution")
}
main()