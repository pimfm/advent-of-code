package aoc2025

import Day
import ceilToNextBase10
import component1
import component2
import cutInTwo
import digitCount
import floorToPreviousBase10
import glue
import isOdd
import kotlinx.coroutines.runBlocking
import java.io.File

typealias IDRanges = List<LongRange>

fun main(): Unit = runBlocking {
    Day2.solve(
        example1 = 1227775554,
    )
}

object Day2 : Day<IDRanges>(2, 2025) {
    override fun parse(input: File) = input
        .readText()
        .split(",")
        .map {
            it.substringBefore("-").toLong() .. it.substringAfter("-").toLong()
        }

    override suspend fun part1(input: IDRanges) = input
        .flatMap(::findInvalids)
        .sum()

    override suspend fun part2(input: IDRanges): Number {
        TODO("Not yet implemented")
    }

    private fun findInvalids(range: LongRange): List<Long> {
        val (from, to) = range
        val (fromStart, fromEnd) = from.cutInTwo()
        val (toStart, toEnd) = to.cutInTwo()

        return when {
            from > to -> emptyList()
            from.digitCount.isOdd() -> findInvalids(from.ceilToNextBase10() .. to)
            to.digitCount.isOdd() -> findInvalids(from .. to.floorToPreviousBase10())
            fromStart < fromEnd -> findInvalids(glue(fromStart + 1, fromStart + 1) .. to)
            toStart > toEnd -> findInvalids(from .. glue(toStart - 1, toEnd - 1))
            else -> (fromStart .. toStart).map { glue(it, it) }
        }
    }
}