package day03

import println
import readInputAsLines

data class Pos(val row: Int, val col: Int)

fun findMatchedNumbers(input: List<String>): List<List<MatchResult>> {
    return input.fold(mutableListOf()) { numbers, line ->
        numbers.add(Regex("\\d+").findAll(line).toList())
        numbers
    }
}

fun valid(r: Int, d: Int, maxLen: Int): Boolean {
    return r + d in 0 until maxLen
}

fun adjacentPositions(row: Int, col: Int, maxRow: Int, maxCol: Int): List<Pos> {
    return listOf(
        listOf(-1, 0),
        listOf(1, 0),
        listOf(0, -1),
        listOf(0, 1),
        listOf(-1, 1),
        listOf(-1, -1),
        listOf(1, -1),
        listOf(1, 1)
    ).fold(mutableListOf()) { positions, delta ->
        if (valid(row, delta[0], maxRow) && valid(col, delta[1], maxCol)) {
            positions.add(Pos(row + delta[0], col + delta[1]))
        }
        positions
    }
}

fun isSymbol(pos: Pos, input: List<String>): Boolean {
    return input[pos.row][pos.col] != '.' && !input[pos.row][pos.col].isDigit()
}

fun findPartNumbers(matchedNumbers: List<List<MatchResult>>, input: List<String>): List<Int> {
    return matchedNumbers.foldIndexed(mutableListOf()) { idx, partNumbers, matchedNumbersInARow ->
        val matchedPartNumbersInARow = matchedNumbersInARow.filter { matchedNumber ->
            for (col in matchedNumber.range) {
                val positions = adjacentPositions(idx, col, input.size, input[idx].length)
                if (positions.any { isSymbol(it, input) })
                    return@filter true
            }
            false
        }
        partNumbers.addAll(matchedPartNumbersInARow.map { it.value.toInt() })
        partNumbers
    }
}

fun isGear(pos: Pos, input: List<String>): Boolean {
    return input[pos.row][pos.col] == '*'
}

fun findGearRatios(matchedNumbers: List<List<MatchResult>>, input: List<String>): List<Int> {
    val gearAdjacentNumbers = mutableMapOf<Pos, MutableSet<Int>>()
    matchedNumbers.forEachIndexed { idx, numbers ->
        numbers.forEach { matchResult ->
            for (col in matchResult.range) {
                val positions = adjacentPositions(idx, col, input.size, input[idx].length)
                for (p in positions) {
                    if (isGear(p, input)) {
                        val adjacentNumbers = gearAdjacentNumbers.getOrDefault(p, mutableSetOf())
                        gearAdjacentNumbers[p] = (adjacentNumbers + matchResult.value.toInt()) as MutableSet<Int>
                    }
                }
            }
        }
    }

    return gearAdjacentNumbers.map { if (it.value.size == 2) it.value.fold(1) { ratio, num -> ratio * num } else 0 }
}

fun main() {
    fun part1(input: List<String>): Int {
        return findPartNumbers(findMatchedNumbers(input), input).sum()
    }
    check(part1(readInputAsLines("day03_test")) == 4361)
    part1(readInputAsLines("day03")).println()


    fun part2(input: List<String>): Int {
        return findGearRatios(findMatchedNumbers(input), input).sum()
    }
    check(part2(readInputAsLines("day03_test")) == 467835)
    part2(readInputAsLines("day03")).println()
}
