package aoc2025

import Day
import toNextBase10
import toPreviousBase10
import isOdd
import jdk.vm.ci.code.CodeUtil.isEven
import kotlinx.coroutines.runBlocking
import sliceEqualParts
import java.io.File

typealias IDRanges = List<Pair<String, String>>

fun main(): Unit = runBlocking {
    Day2.solve(
        example1 = 1227775554,
        example2 = 4174379265,

        answer1 = 41294979841
    )
}

object Day2 : Day<IDRanges>(2, 2025) {
    override fun parse(input: File) = input
        .readText()
        .split(",")
        .map {
            it.substringBefore("-") to it.substringAfter("-")
        }

    override suspend fun part1(input: IDRanges) = input
        .flatMap { findInvalids(it.first, it.second, listOf(2)) }
        .sum()

    override suspend fun part2(input: IDRanges) = input
        .flatMap { findInvalids(it.first, it.second) }
        .sum()

    private tailrec fun findInvalids(
        from: String,
        to: String,
        patterns: List<Int> = emptyList()
    ): List<Long> {
        /**
         * Plan:
         * Have equal parts in a list of parts (say 3 slices with pattern length 3)
         * Check if all 3 are equal, add to return if so. I think we can't find the lowest anymore.
         * Instead, we start at lowest number of range and increment all the way through. Drop the two start cases
         * last case: (patterns).any { in range }() + findInvalids(glue(from++) * patternlength) + findInvalids(glue(from++) * nextpatternlength)
         * Do the calls only for the patterns and match them to the full LongRange (are in the range). Increment by 1 for all values and increment all patterns.
         * (or just lowest pattern? -> Yes only most digit pattern, as it will calculate equal parts inside. Although:
         * You could also just pass it's own pattern. Interesting.)
         */
        val equalParts =

        return when {
            from > to -> emptyList()
            patterns.all(::isEven) && from.length.isOdd() -> findInvalids(from.toNextBase10(), to, patterns)
            patterns.all(::isEven) && to.length.isOdd() -> findInvalids(from, to.toPreviousBase10(), patterns)
            else -> from
                // Consider returning a map of patternLength -> set -> Good to only have repeating patterns in map
                .sliceEqualParts(patterns) // Check case for from being "8" which can result in a set of length 1
                .filter { it.toSet().length == 1 } // All parts are equal
                .filter {} emptyList() // (fromStart.toLong() .. toStart.toLong()).map { it + it }
        }
    }
}