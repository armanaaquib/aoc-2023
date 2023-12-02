package day01

import println
import readInputAsLines

fun main() {
    fun findCalibrationValue(text: String): Int {
        val fistDigit = text.first { it.isDigit() }
        val lastDigit = text.last { it.isDigit() }
        return (fistDigit.toString() + lastDigit).toInt()
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { sum, text ->
            sum + findCalibrationValue(text)
        }
    }
    check(part1(readInputAsLines("day01p1_test")) == 142)
    part1(readInputAsLines("day01")).println()

    /*Do not work for "twone"
    fun findCalibrationValue(text: String): Int {
        val regex = "(one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9)".toRegex()
        val digits = mapOf(
            "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5",
            "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9"
        )
        val matches = regex.findAll(text)
        val firstDigit = matches.first().value
        val secondDigit = matches.last().value
        return ((digits[firstDigit] ?: firstDigit) + (digits[secondDigit] ?: secondDigit)).toInt()
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { sum, text -> sum + findCalibrationValue(text)}
    }
     */

    fun replaceWithDigit(text: String): String {
        var newText = text
        listOf(
            "one" to "o1e", "two" to "t2o", "three" to "t3e", "four" to "f4r", "five" to "f5e",
            "six" to "s6x", "seven" to "s7n", "eight" to "e8t", "nine" to "n9e"
        ).forEach {
            newText = newText.replace(it.first, it.second)
        }
        return newText
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { sum, text ->
            sum + findCalibrationValue(replaceWithDigit(text))
        }
    }
    check(part2(readInputAsLines("day01p2_test")) == 281)
    part2(readInputAsLines("day01")).println()
}
