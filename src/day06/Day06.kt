package day06

import println

data class Race(val time: Long, val distance: Long)

fun main() {
    val testTime = "71530"
    val testDistance = "940200"
    val time = "51699878"
    val distance = "377117112241505"

    fun parseRaces(time: String, distance: String): List<Race> {
        val times = Regex("\\d+").findAll(time).map { it.value.toLong() }.toList()
        val distances = Regex("\\d+").findAll(distance).map { it.value.toLong() }.toList()

        return times.mapIndexed { idx, time ->
            Race(time, distances[idx])
        }
    }

    fun findNoOfWaysToWin(races: List<Race>): List<Long> {
        return races.map {
            var nw = 0L
            for (i in 1 until it.time) {
                val d = (it.time - i) * i
                if (d > it.distance) {
                    nw += 1
                }
            }
            nw
        }
    }

    fun part(time: String, distance: String): Long {
        val races = parseRaces(time, distance)
        return findNoOfWaysToWin(races).also(::println).reduce { acc, noOfWays -> acc * noOfWays }
    }
    check(part(testTime, testDistance) == 71503L)
    part(time, distance).println()

}


