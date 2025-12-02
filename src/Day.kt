import java.io.File
import kotlin.system.measureTimeMillis

abstract class Day<T>(val day: Int, val year: Int) {
    abstract fun parse(input: File): T
    abstract suspend fun part1(input: T): Number
    abstract suspend fun part2(input: T): Number

    suspend fun solve(example1: Number) {
        process(1, ::part1, example1)
    }

    suspend fun solve(example1: Number, example2: Number) {
        process(1, ::part1, example1)
        process(2, ::part2, example2)
    }

    suspend fun solve(example1: Number, example2: Number, answer1: Number) {
        process(1, ::part1, example1, answer1)
        process(2, ::part2, example2)
    }

    suspend fun solve(example1: Number, example2: Number, answer1: Number, answer2: Number) {
        process(1, ::part1, example1, answer1)
        process(2, ::part2, example2, answer2)
    }

    private suspend fun process(
        partNumber: Int,
        part: suspend (input: T) -> Number,
        example: Number,
        answer: Number? = null
    ) {
        try {
            part(parse(exampleInput)) validate example
            part(parse(actualInput)) validate answer

            measureTimeMillis { part(parse(actualInput)) then answer(partNumber) }.also { println(" (⏳ $it ms)") }
        }
        catch (e: NotImplementedError) { e.printNotImplemented(partNumber) }
        catch (e: IncorrectAlgorithmError) { e.printIncorrectAlgorithm(partNumber) }
    }
}