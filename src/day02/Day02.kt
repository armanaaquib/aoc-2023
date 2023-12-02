package day02

import println
import readInputAsLines

data class Cube(val color: String, val count: Int)
data class Game(val id: Int, val cubes: List<Cube>)

fun main() {

    fun parseCubes(line: String): List<Cube> {
        return line.split(": ")[1].split(Regex("(, |; )")).map {
            val countAndColour = it.split(" ")
            Cube(countAndColour[1], countAndColour[0].toInt())
        }
    }

    fun parseGames(input: List<String>): List<Game> {
        return input.mapIndexed { idx, line ->
            Game(idx + 1, parseCubes(line))
        }
    }

    fun isGamePossible(game: Game): Boolean {
        val bag = mapOf("red" to 12, "green" to 13, "blue" to 14)
        return !game.cubes.any { it.count > bag[it.color]!! }
    }

    fun part1(input: List<String>): Int {
        val games = parseGames(input)
        return games.fold(0) { sum, game ->
            if (isGamePossible(game)) sum + game.id else sum
        }
    }
    check(part1(readInputAsLines("day02_test")) == 8)
    part1(readInputAsLines("day02")).println()

    fun powerOfMinimumSetOfCubes(game: Game): Int {
        val groupedCubesByColor = game.cubes.groupBy { it.color }
        return groupedCubesByColor.values.fold(1) { power, cubes ->
            power * cubes.maxBy { it.count }.count
        }
    }

    fun part2(input: List<String>): Int {
        val games = parseGames(input)
        return games.fold(0) { sum, game ->
            sum + powerOfMinimumSetOfCubes(game)
        }
    }
    check(part2(readInputAsLines("day02_test")) == 2286)
    part2(readInputAsLines("day02")).println()
}
