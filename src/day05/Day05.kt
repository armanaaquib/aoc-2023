package day05

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import println
import readInputAsText
import kotlin.math.min

data class Range(val start: Long, val destination: Long, val length: Long)

fun main() {
    fun parseRanges(input: String): List<List<Range>> {
        val split = input.split("\n\n")
        return split.subList(1, split.size).map { rangeMap ->
            val split1 = rangeMap.split("\n")
            split1.subList(1, split1.size).map {
                val range = it.split(" ")
                Range(range[1].toLong(), range[0].toLong(), range[2].toLong())
            }
        }
    }

    fun parseSeeds(input: String): List<Long> {
        return input.split("\n\n").first().split(": ").last().split(" ").map { it.toLong() }
    }

    fun getLocation(allRanges: List<List<Range>>, seed: Long): Long {
        return allRanges.fold(seed) { id, ranges ->
            for (range in ranges) {
                if (id in range.start until range.start + range.length) {
                    return@fold range.destination + (id - range.start)
                }
            }
            id
        }
    }

    fun getLocations(allRanges: List<List<Range>>, ids: List<Long>): List<Long> {
        return ids.map { getLocation(allRanges, it) }
    }

    fun part1(input: String): Long {
        val seeds = parseSeeds(input)
        val ranges = parseRanges(input)
        return getLocations(ranges, seeds).min()
    }

    check(part1(readInputAsText("day05_test")) == 35L)
    part1(readInputAsText("day05")).println()

//    fun generateSeedRanges(seeds: List<Long>): List<LongRange> {
//        val seedRanges = mutableListOf<LongRange>()
//
//        for (idx in 0 until seeds.lastIndex step 2) {
//            seedRanges.add(seeds[idx] until seeds[idx] + seeds[idx + 1])
//        }
//
//        return seedRanges
//    }

    fun printMinLocation(allRanges: List<List<Range>>, seeds: List<Long>) {
        runBlocking(Dispatchers.Default) {
            val jobs = mutableListOf<Deferred<Long>>()
            for (idx in 0 until seeds.lastIndex step 2) {
                val job = async {
                    var minLocation = Long.MAX_VALUE
                    for (i in 0 until seeds[idx + 1]) {
                        minLocation = min(minLocation, getLocation(allRanges, seeds[idx] + i))
                    }
                    minLocation
                }
                jobs.add(job)
            }
            jobs.awaitAll().min().println()
        }
    }

    fun part2(input: String) {
        val seeds = parseSeeds(input)
        val ranges = parseRanges(input)
        printMinLocation(ranges, seeds)
    }

    part2(readInputAsText("day05_test"))
    part2(readInputAsText("day05"))
}
