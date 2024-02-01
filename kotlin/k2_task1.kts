import java.io.File

/**
 * @author Dominik Cedro
 * 02.12.2023
 * Advent of Code 2023
 */

fun task1(): Int {
    var number = 0
    var gameNum = 0
    var possibleGame = 0
    var dictColors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
    File("../day_2_data.txt").readLines().forEach { line ->
        var validGame = true
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
                        if (dictColors[color]!! > 0) {
                            dictColors[color] = 0
                        }
                        dictColors[color] = dictColors[color]!! + number
                    } else if (color.dropLast(1) in dictColors.keys) {
                        if (dictColors[color.dropLast(1)]!! > 0) {
                            dictColors[color.dropLast(1)] = 0
                        }
                        color = color.dropLast(1)
                        dictColors[color] = dictColors[color]!! + number
                    }
                    if (dictColors["red"]!! > 12 || dictColors["green"]!! > 13 || dictColors["blue"]!! > 14) {
                        dictColors = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
                        validGame = false
                        return@forEach
                    }
                }
            }
        }
        if (validGame) {
            possibleGame += gameNum
        }
    }
    return possibleGame
}

fun main() {
    println("Day 2 task 1 solution: ${task1()}")
}
main()