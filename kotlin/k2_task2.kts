import java.io.File

/**
 * @author Dominik Cedro
 * 02.12.2023
 * Advent of Code 2023
 */

fun task2(): Int {
    var number = 0
    var gameNum = 0
    var dictColors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
    var sumNumbers = 0
    File("../day_2_data.txt").readLines().forEach { line ->
        line.split(" ").forEach { word ->
            when {
                word == "Game" -> {
                    dictColors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
                    gameNum += 1
                }
                word.toIntOrNull() != null -> {
                    number = word.toInt()
                }
                word.all { it.isLetter() } || word.dropLast(1).all { it.isLetter() } -> {
                    var color = word
                    if (color in dictColors.keys) {
                        if (dictColors[color]!! < number) {
                            dictColors[color] = number
                        }
                    } else if (color.dropLast(1) in dictColors.keys) {
                        if (dictColors[color.dropLast(1)]!! < number) {
                            dictColors[color.dropLast(1)] = number
                        }
                    }
                }
            }
        }
        sumNumbers += (dictColors["red"]!! * dictColors["green"]!! * dictColors["blue"]!!)
    }
    return sumNumbers
}

fun main() {
    println("Day 2 task 2 solution: ${task2()}")
}
main()