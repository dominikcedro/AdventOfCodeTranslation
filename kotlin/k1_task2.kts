import java.io.File

fun findDigitsCreateList(string: String): List<Int> {
    val digitList = mutableListOf<Int>()
    for (i in string) {
        if (i.isDigit()) {
            digitList.add(i.toString().toInt())
        }
    }
    return digitList
}

fun getFirstAndLastDigit(digitList: List<Int>): Int {
    val digit1 = digitList[0]
    val digit2 = digitList[digitList.size - 1]
    return digit1 * 10 + digit2
}

fun readFile() {
    val file = "../day_1_data.txt"
    var sum = 0
    File(file).forEachLine {
        sum += getFirstAndLastDigit(findDigitsCreateList(it))
    }
    println("Day 1 task 2 solution: $sum")
}

fun main() {
    readFile()
}
main()