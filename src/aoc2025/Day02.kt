package aoc2025

import Day
import toNextBase10
import cutInTwo
import decrement
import toPreviousBase10
import increment
import isEven
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
        .flatMap { findInvalids(it.first, it.second, listOf(it.second.length / 2)) }
        .sum()

    override suspend fun part2(input: IDRanges) = input
        .flatMap { findInvalids(it.first, it.second, (1 .. it.second.length / 2).toList()) }
        .sum()

    private tailrec fun findInvalids(
        from: String,
        to: String,
        patterns: List<Int>
    ): List<Long> {
        val equalParts = from.sliceEqualParts(patterns)

        val (fromStart, fromEnd) = from.cutInTwo()
        val (toStart, toEnd) = to.cutInTwo()

        return when {
            from > to -> emptyList()
            patterns.all(::isEven) && from.length.isOdd() -> findInvalids(from.toNextBase10(), to, patterns)
            patterns.all(::isEven) && to.length.isOdd() -> findInvalids(from, to.toPreviousBase10(), patterns)
            fromStart < fromEnd -> findInvalids(fromStart.increment() + fromStart.increment(), to, patterns)
            toStart > toEnd -> findInvalids(from, toStart.decrement() + toStart.decrement(), patterns)
            else -> (fromStart.toLong() .. toStart.toLong()).map { it + it }
        }
    }
}