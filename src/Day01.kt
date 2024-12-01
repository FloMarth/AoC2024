import kotlin.math.abs

fun main() {
    fun extractList(input:List<String>, index: Int): List<Int> {
        val splitList = input.stream().map { it.split("\\s+".toRegex()) }.toList()

        return splitList.stream().map { it[index].toInt() }.sorted().toList()
    }

    fun part1(input: List<String>): Int {
        val leftList = extractList(input, 0)
        val rightList = extractList(input, 1)

        var sum = 0
        for (i in leftList.indices) {
            sum += abs(leftList[i] - rightList[i])
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val leftList = extractList(input, 0)
        val rightList = extractList(input, 1)

        var sum = 0
        for (i in leftList.indices) {
            val occurrencesRightList = rightList.stream().filter { it == leftList[i] }.count().toInt()

            sum += leftList[i] * occurrencesRightList
        }

        return sum
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
