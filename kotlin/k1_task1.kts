import java.io.File

val numbersDict = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
)

fun checkDigits(string: String): Any {
    // check if the string contains just one digit
    var count = 0
    var number = ""
    for (i in string.indices) {
        if (string[i].isDigit()) {
            count += 1
            number = string[i].toString()
        }
    }
    return if (count == 1) number else false
}

fun takeDigits(string: String, numbersDict: Map<String, Int>): Int {
    val digitList = mutableListOf<Int>()
    var word = ""
    for (i in string) {
        if (i.isDigit()) {
            digitList.add(i.toString().toInt())
        } else {
            word += i
            for (k in numbersDict.keys) {
                if (word.contains(k)) {
                    digitList.add(numbersDict[k]!!)
                    // leave last letter of the word only
                    word = i.toString()
                }
            }
        }
    }
    val digit1 = digitList[0]
    val digit2 = digitList[digitList.size - 1]
    return digit1 * 10 + digit2
}

fun readFile() {
    val file = "../day_1_data.txt"
    var sum = 0
    File(file).forEachLine {
        sum += takeDigits(it, numbersDict)
    }
    println("Day 1 task 1 solution: $sum")
}

fun main() {
    readFile()
}
main()