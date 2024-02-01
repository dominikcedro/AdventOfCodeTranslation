import java.io.File
import java.util.regex.Pattern

fun readInput(file: String): List<String> {
    return File(file).readLines()
}

fun assignType(length: Int, hand: String, highestCardScore: Int, handTypes: MutableMap<String, MutableList<String>>) {
    when (length) {
        5 -> handTypes.getOrPut("High card") { mutableListOf() }.add(hand)
        4 -> handTypes.getOrPut("One pair") { mutableListOf() }.add(hand)
        3 -> {
            if (highestCardScore == 3) {
                handTypes.getOrPut("Three of a kind") { mutableListOf() }.add(hand)
            } else {
                handTypes.getOrPut("Two pair") { mutableListOf() }.add(hand)
            }
        }
        2 -> {
            if (highestCardScore == 3) {
                handTypes.getOrPut("Full house") { mutableListOf() }.add(hand)
            } else {
                handTypes.getOrPut("Four of a kind") { mutableListOf() }.add(hand)
            }
        }
        else -> handTypes.getOrPut("Five of a kind") { mutableListOf() }.add(hand)
    }
}

fun main() {
    val data = readInput("../day7_data.txt")

    val pairsDict = mutableMapOf<String, String>()
    for (i in data) {
        val split = i.split(' ')
        pairsDict[split[0]] = split[1]
    }

    val pokerHands = pairsDict.keys

    val handTypes = mutableMapOf<String, MutableList<String>>()

    for (hand in pokerHands) {
        val cardCounts = hand.groupingBy { it }.eachCount()
        val sortedCards = cardCounts.entries.sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenByDescending { it.key })
        var highestCardScore = sortedCards[0].value
        var length = sortedCards.size

        if ('J' in hand) {
            length = maxOf(1, length - 1)
            if (sortedCards[0].key != 'J') {
                highestCardScore += hand.count { it == 'J' }
            } else {
                try {
                    highestCardScore = highestCardScore + sortedCards[1].value
                } catch (e: IndexOutOfBoundsException) {
                    // Do nothing
                }
            }
        }

        assignType(length, hand, highestCardScore, handTypes)
    }

    val strength = "AKQT98765432J"
    for ((key, value) in handTypes) {
        handTypes[key] = value.sortedBy { hand -> hand.map { strength.indexOf(it) }.joinToString("") }.toMutableList()
    }

    val finalList = handTypes.values.flatten().reversed()

    var total = 0
    for ((i, pair) in finalList.withIndex()) {
        total += (i + 1) * pairsDict[pair]!!.toInt()
    }

    println("Answer to Part 2: $total")
}
main()