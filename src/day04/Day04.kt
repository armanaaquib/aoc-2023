package day04

import println
import readInputAsLines

data class Card(val availableNumbers: List<Int>, val winningNumbers: List<Int>, var copy: Int = 1)

fun parseCards(input: List<String>): List<Card> {
    return input.map { line ->
        val numbers = line.split(": ")[1].split(" | ")
        val winningNumbers = numbers[0].trim().split(Regex(" +")).map { it.toInt() }
        val availableNumbers = numbers[1].trim().split(Regex(" +")).map { it.toInt() }
        Card(availableNumbers, winningNumbers)
    }
}

fun main() {
    fun findPoints(cards: List<Card>): List<Int> {
        return cards.map {
            var point = 0
            for (num in it.availableNumbers) {
                if (num in it.winningNumbers) {
                    point = if (point == 0) 1 else point * 2
                }
            }
            point
        }
    }

    fun part1(input: List<String>): Int {
        val cards = parseCards(input)
        return findPoints(cards).sum()
    }
    check(part1(readInputAsLines("day04_test")) == 13)
    part1(readInputAsLines("day04")).println()

    fun findNoOfMatches(card: Card): Int {
        return card.availableNumbers.intersect(card.winningNumbers.toSet()).size
    }

    fun getCopyUpdatedCards(cards: List<Card>): List<Card> {
        cards.forEachIndexed { idx, card ->
            val noOfMatches = findNoOfMatches(card)
            val incr = card.copy
            for (i in 1..noOfMatches) {
                cards[idx + i].copy += incr
            }
        }
        return cards
    }

    fun part2(input: List<String>): Int {
        val cards = parseCards(input)
        return getCopyUpdatedCards(cards).fold(0) { sum, card -> sum + card.copy }
    }
    check(part2(readInputAsLines("day04_test")) == 30)
    part2(readInputAsLines("day04")).println()
}
