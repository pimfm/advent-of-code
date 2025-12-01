package aoc2025

import Day
import kotlinx.coroutines.runBlocking
import mapLines
import print
import java.io.File
import kotlin.math.abs

typealias Rotations = List<Pair<Char, Int>>

fun main(): Unit = runBlocking {
    println("--- Part 1 ---")
    Day1.parse(File("src/aoc2025/input/test-day1.txt")).let { Day1.part1(it) }.print
    Day1.parse(File("src/aoc2025/input/day1.txt")).let { Day1.part1(it) }.print
    println()

    println("--- Part 2 ---")
    Day1.parse(File("src/aoc2025/input/test-day1.txt")).let { Day1.part2(it) }.print
    Day1.parse(File("src/aoc2025/input/day1.txt")).let { Day1.part2(it) }.print
    println()
}

object Day1 : Day<Rotations>(1, 2025) {
    override fun parse(input: File) = input.mapLines { line ->
        line.first() to line.drop(1).toInt()
    }

    override suspend fun part1(input: Rotations) = input.fold(50 to 0) { acc, rotation ->
        val (direction, amount) = rotation
        val (position, numberOfZeros) = acc

        val newPosition = rotateDial(direction, amount, position)

        newPosition to if (newPosition == 0) numberOfZeros + 1 else numberOfZeros
    }.second

    override suspend fun part2(input: Rotations) = input.fold(50 to 0) { acc, rotation ->
        val (position, numberOfZeros) = acc

        val fullLoops = (rotation.second / 100).also { if (it > 0) println(it) }
        val countPastZero = when {
            position == 0 -> false
            rotation.first == 'L' -> rotation.second % 100 >= position
            rotation.first == 'R' -> rotation.second % 100 >= 100 - position
            else -> false
        }.let { if (it) 1 else 0 }

        rotateDial(rotation.first, rotation.second, position) to numberOfZeros + fullLoops + countPastZero
    }.second

    private fun rotateDial(direction: Char, rotation: Int, position: Int): Int {
        return when (direction) {
            'L' -> position - rotation
            'R' -> position + rotation
            else -> throw Error()
        }
            .let { it % 100 }
            .let { if (it < 0) 100 - abs(it) else it }
    }
}