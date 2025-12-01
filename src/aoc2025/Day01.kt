package aoc2025

import Day
import kotlinx.coroutines.runBlocking
import mapLines
import toInt
import java.io.File

typealias Rotations = List<Pair<Char, Int>>

fun main(): Unit = runBlocking {
    Day1.solve(
        example1 = 3,
        example2 = 6,

        answer1 = 992,
        answer2 = 6133
    )
}

object Day1 : Day<Rotations>(1, 2025) {
    override fun parse(input: File) = input.mapLines { it.first() to it.drop(1).toInt() }

    override suspend fun part1(input: Rotations) = input
        .runningFold(50, ::rotateDial)
        .count { it == 0 }

    override suspend fun part2(input: Rotations) = input.fold(50 to 0) { (position, zeroCount), rotation ->
        val (direction, amount) = rotation
        val fullLoops = amount / 100
        val countZeros = when {
            position == 0 -> false // Avoid duplicate counts; zero is counted when ending on this number
            direction == 'L' -> amount.mod(100) >= position
            direction == 'R' -> amount.mod(100) >= 100 - position
            else -> false
        }.toInt()

        rotateDial(position, rotation) to zeroCount + fullLoops + countZeros
    }.second

    private fun rotateDial(position: Int, rotation: Pair<Char, Int>): Int {
        val (direction, amount) = rotation

        return when (direction) {
            'L' -> position - amount
            'R' -> position + amount
            else -> throw Error()
        }.mod(100)
    }
}