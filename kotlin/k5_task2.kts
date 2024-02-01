import java.io.File

fun star2(): Int {
    val lines = File("../day5_data.txt").readLines()
    val line = lines[0].removePrefix("seeds: ").split(" ").map { it.toInt() }
    var seedRanges = mutableListOf<Pair<Int, Int>>()
    for (i in line.indices step 2) {
        seedRanges.add(Pair(line[i], line[i + 1]))
    }

    val maps = mutableListOf<MutableList<List<Int>>>()
    maps.add(mutableListOf())
    var i = 3
    var mapNum = 0
    while (i < lines.size) {
        if (lines[i] == "\n") {
            i += 2
            mapNum += 1
            maps.add(mutableListOf())
        } else {
            maps[mapNum].add(lines[i].trim().split(" ").map { it.toInt() })
            i += 1
        }
    }

    for (category in maps) {
        val newSeedRanges = mutableListOf<Pair<Int, Int>>()
        for (seedRange in seedRanges) {
            var (seedStart, seedLen) = seedRange
            val seedEnd = seedStart + seedLen

            for (mapRange in category) {
                val (destStart, srcStart, rangeLen) = mapRange
                val srcEnd = srcStart + rangeLen

                if (seedStart < srcEnd && seedEnd > srcStart) {
                    if (seedStart < srcStart) {
                        newSeedRanges.add(Pair(seedStart, srcStart - seedStart))
                    }

                    val overlapStart = maxOf(seedStart, srcStart)
                    val overlapEnd = minOf(seedEnd, srcEnd)
                    val offset = destStart - srcStart
                    val newStart = overlapStart + offset
                    val newLen = overlapEnd - overlapStart
                    newSeedRanges.add(Pair(newStart, newLen))

                    if (seedEnd > srcEnd) {
                        seedStart = srcEnd
                        seedLen = seedEnd - srcEnd
                    } else {
                        break
                    }
                }
            }

            if (seedStart < seedEnd) {
                newSeedRanges.add(Pair(seedStart, seedLen))
            }
        }

        seedRanges = newSeedRanges
    }

    val minLocation = seedRanges.minOf { it.first }
    return minLocation
}

fun main() {
    println(star2())
}
main()