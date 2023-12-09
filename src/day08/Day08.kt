package day08

import println
import readInputAsLines

fun main() {
    fun parseInstructions(input: List<String>): List<Int> {
        return input[0].toList().map { if (it == 'L') 0 else 1 }
    }

    fun parseNetwork(input: List<String>): Map<String, List<String>> {
        return input.drop(2).fold(mutableMapOf()) { network, line ->
            val keyValueString = line.split(" = ")
            val key = keyValueString[0]
            val value = listOf(keyValueString[1].substring(1..3), keyValueString[1].substring(6..8))
            network[key] = value
            network
        }
    }

    fun findSteps(network: Map<String, List<String>>, instructions: List<Int>, element: String = "AAA"): Int {
        element.println()
        var idx = 0
        var el = element
        var steps = 0
        while (el[2] != 'Z') {
            el = network[el]!![instructions[idx]]
            idx = (idx + 1) % instructions.size
            steps += 1
            el.println()
        }
        "-------------------".println()
        return steps
    }

    fun part1(input: List<String>): Int {
        val instructions = parseInstructions(input).also(::println)
        val network = parseNetwork(input).also(::println)
        return findSteps(network, instructions).also(::println)
    }
//    check(part1(readInputAsLines("day08_test")) == 6)
//    part1(readInputAsLines("day08")).println()

    fun findStartingElements(elements: Set<String>): List<String> {
       return elements.filter { it[2] == 'A' }
    }

    fun isAllEndingElements(elements: List<String>): Boolean {
        return elements.all { it[2] == 'Z' }
    }

    fun findGhostSteps(network: Map<String, List<String>>, instructions: List<Int>): Int {
        var elements = findStartingElements(network.keys)
        var idx = 0
        var steps = 0
        while (!isAllEndingElements(elements)) {
            elements = elements.map { network[it]!![instructions[idx]]  }
            idx = (idx + 1) % instructions.size
            steps += 1
        }

        return steps
    }


    fun part2(input: List<String>): Int {
        val instructions = parseInstructions(input)
        val network = parseNetwork(input)
        val elements = findStartingElements(network.keys)
//        return findGhostSteps(network, instructions)
        return elements.map { findSteps(network, instructions, it) }.fold(1) { acc, s -> acc * s }
    }
//    check(part2(readInputAsLines("day08_test")) == 6)
    part2(readInputAsLines("day08")).println()
}