package day09

import println
import readInputAsLines

fun main() {
    fun parseSequences(input: List<String>): List<List<Int>> {
        return input.map { line -> line.split(" ").map { it.toInt() } }
    }

    fun isAllZero(nums: List<Int>): Boolean {
        return nums.all { it == 0 }
    }

    fun getNextSeq(seq: List<Int>): List<Int> {
        val nextSeq = mutableListOf<Int>()

        for (i in 0 until seq.lastIndex) {
            nextSeq.add(seq[i + 1] - seq[i])
        }

        return nextSeq
    }

    fun generateSequences(sequence: List<Int>): MutableList<List<Int>> {
        val sequences = mutableListOf(sequence)

        while (!isAllZero(sequences.last())) {
            sequences.add(getNextSeq(sequences.last()))
        }
        return sequences
    }

    fun findNextValue(sequence: List<Int>): Int {
        val sequences = generateSequences(sequence)

        return sequences.reversed().drop(1).fold(0) { sum, seq -> sum + seq.last() }
    }

    fun part1(input: List<String>): Int {
        val sequences = parseSequences(input)
        return sequences.sumOf { findNextValue(it) }
    }
    check(part1(readInputAsLines("day09_test")) == 114)
    part1(readInputAsLines("day09")).println()


    fun findPreviousValue(sequence: List<Int>): Int {
        val sequences = generateSequences(sequence)

        return sequences.reversed().drop(1).fold(0) { sum, seq -> seq.first() - sum }
    }

    fun part2(input: List<String>): Int {
        val sequences = parseSequences(input)
        return sequences.sumOf { findPreviousValue(it) }
    }
    check(part2(readInputAsLines("day09_test")) == 2)
    part2(readInputAsLines("day09")).println()
}