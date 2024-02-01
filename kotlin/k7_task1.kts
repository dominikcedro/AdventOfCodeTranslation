import java.io.File
import java.util.Collections.frequency

fun readInput(file: String): List<String> {
    return File(file).readLines()
}

fun main() {
    val data = readInput("../day7_data.txt")

    val pairsDict = mutableMapOf<String, String>()
    for (i in data) {
        val split = i.split(' ')
        pairsDict[split[0]] = split[1]
    }

    val pokerHands = pairsDict.keys

    val handTypes = mutableMapOf(
            "Five of a kind" to mutableListOf<String>(),
            "Four of a kind" to mutableListOf(),
            "Full house" to mutableListOf(),
            "Three of a kind" to mutableListOf(),
            "Two pair" to mutableListOf(),
            "One pair" to mutableListOf(),
            "High card" to mutableListOf()
    )

    for (hand in pokerHands) {
        val cardCounts = hand.groupingBy { it }.eachCount()
        val sortedCards = cardCounts.entries.sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenByDescending { it.key })
        val length = sortedCards.size

        when (length) {
            5 -> handTypes["High card"]?.add(hand)
            4 -> handTypes["One pair"]?.add(hand)
            3 -> {
                if (sortedCards[0].value == 3) {
                    handTypes["Three of a kind"]?.add(hand)
                } else {
                    handTypes["Two pair"]?.add(hand)
                }
            }
            2 -> {
                if (sortedCards[0].value == 3) {
                    handTypes["Full house"]?.add(hand)
                } else {
                    handTypes["Four of a kind"]?.add(hand)
                }
            }
            else -> handTypes["Five of a kind"]?.add(hand)
        }
    }

    val strength = "AKQJT98765432"
    for ((key, value) in handTypes) {
        handTypes[key] = value.sortedBy { hand -> hand.map { strength.indexOf(it) }.joinToString("") }.toMutableList()
    }

    val finalList = handTypes.values.flatten().reversed()

    var total = 0
    for ((i, pair) in finalList.withIndex()) {
        total += (i + 1) * pairsDict[pair]!!.toInt()
    }

    println("Answer to Part 1: $total")
}
main()