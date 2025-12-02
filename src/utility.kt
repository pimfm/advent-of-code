import java.io.File
import kotlin.system.measureTimeMillis

/**
 * Code style functions to prettify the day template
 */
val Day<*>.actualInput
    get() = File("src/aoc$year/input/day$day.txt")

val Day<*>.exampleInput
    get() = File("src/aoc$year/input/test-day$day.txt")

context(Day<T>)
fun <T> NotImplementedError.printNotImplemented(part: Int) =
    println("💻Year $year, Day $day.$part is not yet implemented")

context(Day<T>)
fun <T> IncorrectAlgorithmError.printIncorrectAlgorithm(part: Int) =
    println("🚧Year $year, Day $day.$part is incorrect. Expected $expected, got $actual")

infix fun <T> T.then(block: (input: T) -> Answer) = block(this).also { (year, day, part, result) ->
    print("✅Year $year, Day $day.$part: $result")
}

fun <T> Day<T>.answer(part: Int) =
    fun(result: Number) =
        Answer(year, day, part, result)

data class Answer(
    val year: Int,
    val day: Int,
    val part: Int,
    val answer: Number
)

data class IncorrectAlgorithmError(val expected: String, val actual: String) :
    Exception("Expected $expected to equal $actual.")

infix fun <T : Number> T.validate(expected: T?) {
    if (expected == null) return

    if (this.toLong() != expected.toLong()) {
        throw IncorrectAlgorithmError(expected.toString(), this.toString())
    }
}