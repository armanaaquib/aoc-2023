package day07

import println
import readInputAsLines

data class Hand(val cards: List<Char>) : Comparable<Hand> {
    private val strengths = mapOf(
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'J' to 1,
        'Q' to 11,
        'K' to 12,
        'A' to 13
    )

    private fun rank(): Int {
        val jCount = this.cards.count { it == 'J' }
        if (jCount == 5) return 1

        val counts = getCounts()
        return when (counts[0] + jCount) {
            5 -> 1
            4 -> 2
            3 -> if (counts[1] == 2) 3 else 4
            2 -> if (counts[1] == 2) 5 else 6
            else -> 7
        }
    }

    private fun getCounts(): List<Int> {
        return cards.groupBy { it }.filter { it.key != 'J' }.map { it.value.size }.sortedDescending()
    }

    override fun compareTo(other: Hand): Int {
        val rankDiff = this.rank() - other.rank()
        if (rankDiff != 0) return rankDiff

        var idx = 0
        while (idx < 5 && strengths[this.cards[idx]] == strengths[other.cards[idx]]) {
            idx += 1
        }

        return strengths[other.cards[idx]]!! - strengths[this.cards[idx]]!!
    }
}

fun main() {

    fun parseHandsBid(input: List<String>): List<Pair<Hand, Int>> {
        return input.map {
            val handsString = it.split(" ")[0]
            val bidString = it.split(" ")[1]
            Pair(Hand(handsString.toList()), bidString.toInt())
        }
    }

    fun sortedHandsBidByRank(handsBid: List<Pair<Hand, Int>>): List<Pair<Hand, Int>> {
        return handsBid.sortedBy { it.first }.reversed()
    }

    fun part1(input: List<String>): Int {
        val handsBid = parseHandsBid(input).also(::println)
        return sortedHandsBidByRank(handsBid).also(::println)
            .foldIndexed(0) { idx, sum, hb -> sum + (hb.second * (idx + 1)) }
    }
//    check(part1(readInputAsLines("day07_test")) == 6440)
//    part1(readInputAsLines("day07")).println()


    fun part2(input: List<String>): Int {
        val handsBid = parseHandsBid(input).also(::println)
        return sortedHandsBidByRank(handsBid).also(::println)
            .foldIndexed(0) { idx, sum, hb -> sum + (hb.second * (idx + 1)) }
    }
    check(part2(readInputAsLines("day07_test")) == 5905)
    part2(readInputAsLines("day07")).println()
}