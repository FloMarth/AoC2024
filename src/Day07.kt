import kotlin.math.floor
import kotlin.math.pow

fun main() {
    fun getResultFromInput(input: String): Long {
        return input.substringBefore(':').toLong()
    }

    fun getLineInputFromInput(input: String): MutableList<Long> {
        val numbers = input.substringAfter(": ")
        return numbers.split(' ').map { it.toLong() }.toMutableList()
    }

    fun recursiveCalculation(currentValue:Long, lineInput: List<Long>): Boolean {
        var divisionBranchSucceeded = false

        for (i in lineInput.indices) {
            if (currentValue % lineInput[i] == 0L && currentValue != lineInput[i]) {
                divisionBranchSucceeded = recursiveCalculation(currentValue / lineInput[i], lineInput.subList(i + 1, lineInput.size).toList())
            }

            return divisionBranchSucceeded || recursiveCalculation(currentValue - lineInput[i], lineInput.subList(i + 1, lineInput.size).toList())
        }

        return currentValue == 0L
    }

    fun recursiveCalculationPart2(currentValue:Long, lineInput: List<Long>): Boolean {
            if (currentValue == 0L) {
                return true
            } else if (lineInput.isEmpty()) {
                return false
            }

            var divisionBranchSucceeded = false
            var concatenationBranchSucceeded = false
            if (currentValue % lineInput[0] == 0L && currentValue != lineInput[0]) {
                divisionBranchSucceeded = recursiveCalculationPart2(currentValue / lineInput[0], lineInput.subList(1, lineInput.size).toList())
            }
            val subtractionBranchSucceeded = recursiveCalculationPart2(currentValue - lineInput[0], lineInput.subList(1, lineInput.size).toList())
            if (lineInput.size > 1) {
                val currentValueChange = (currentValue - lineInput[0]) / 10.toDouble().pow(lineInput[0].toString().length.toDouble())
                val currentValueFloored = floor((currentValue - lineInput[0]) / 10.toDouble().pow(lineInput[0].toString().length.toDouble()))
                if (currentValueChange == currentValueFloored) {
                    concatenationBranchSucceeded = recursiveCalculationPart2(currentValueChange.toLong(), lineInput.subList(1, lineInput.size))
                }
            }

        return divisionBranchSucceeded
                || subtractionBranchSucceeded
                || concatenationBranchSucceeded
    }

    fun part1(input: List<String>): Long {
        var totalSum: Long = 0

        input.forEach { line ->
            val lineResult = getResultFromInput(line)
            val lineInput = getLineInputFromInput(line)

            lineInput.reverse()
            val isSucceeded = recursiveCalculation(lineResult, lineInput)

            totalSum += if (isSucceeded) lineResult else 0L
        }

        return totalSum
    }

    fun part2(input: List<String>): Long {
        var totalSum: Long = 0

        input.forEach { line ->
            val lineResult = getResultFromInput(line)
            val lineInput = getLineInputFromInput(line)

            lineInput.reverse()
            val isSucceeded = recursiveCalculationPart2(lineResult, lineInput)

            totalSum += if (isSucceeded) lineResult else 0L
        }

        return totalSum
    }

    val testinput = readInput("Day07_test")

    check(part1(testinput) == 3749L)
    check(part2(testinput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}