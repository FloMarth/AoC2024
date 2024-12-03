fun main() {

    fun runPart1ForLine(input: String): Int {
        var sum = 0

        val regex = Regex("(mul\\(\\d+,\\d+\\))")
        val matches = regex.findAll(input)

        matches.forEach {
            val regexToGetNumbers = Regex("(\\d+)")
            val matchNumbersInMul = regexToGetNumbers.findAll(it.value).toList()
            sum += matchNumbersInMul[0].value.toInt() * matchNumbersInMul[1].value.toInt()
        }

        return sum
    }

    fun runPart2ForLine(input: String): Int {
        val newInput = input.replace(("(don't\\(\\).+?do\\(\\))").toRegex(), "")

        return runPart1ForLine(newInput)
    }

   fun part1(input: List<String>): Int = input.stream().map {runPart1ForLine(it)}.toList().sum()

    fun part2(input: List<String>): Int = runPart2ForLine(input.joinToString())


    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")) == 161)
    check(part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48)

//    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day02_test")
//    check(part1(testInput) == 2)
//    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
