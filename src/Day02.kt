fun main() {
    fun extractList(input:List<String>): List<List<Int>> {
        val splitList = input.stream()
            .map { it.split("\\s+".toRegex()) }
            .map { itOuter -> itOuter.map { itInner -> itInner.toInt() } }
            .toList()

        return splitList
    }

    fun createAllSubLists (input: List<List<Int>>): List<List<List<Int>>> {
        val resultList: MutableList<MutableList<List<Int>>> = mutableListOf()

        for (i in input.indices) {
            resultList.add(i, mutableListOf())
            for (j in input[i].indices) {
                val tempList = mutableListOf<Int>()
                tempList.addAll(input[i])
                tempList.removeAt(j)
                resultList[i].add(j, tempList)
            }
        }

        return resultList
    }

    fun doSafeReportsConditionsMet(report: List<Int>): Boolean {
        var isSafeReport = true
        var isAscending = true
        if (report.count() == 1) {
            return true
        }

        //If the first element is larger than the second element, the list is descending
        if (report[0] > report[1]) {
            isAscending = false
        }

        for (i in report.indices) {
            //Do not check for first index
            if (i == 0) {
                continue
            }

            if (isAscending && (report[i-1] >= report[i] || report[i] - 3 > report[i-1])) {
                isSafeReport = false
                break
            }

            if (!isAscending && (report[i-1] <= report[i] || report[i] + 3 < report[i-1])) {
                isSafeReport = false
                break
            }
        }
        return isSafeReport
    }

   fun part1(input: List<String>): Int {
        val parsedInputList = extractList(input)

        var safeReports = 0
        parsedInputList.forEach {
            if (doSafeReportsConditionsMet(it)) {
                safeReports++
            }
        }
        return safeReports
    }

    fun part2(input: List<String>): Int {
        val parsedInputList = extractList(input)

        val parsedInputSubLists = createAllSubLists(parsedInputList)

        var safeReports = 0
        parsedInputSubLists.forEach {
            for (i in it.indices) {
                if (doSafeReportsConditionsMet(it[i])) {
                    safeReports++
                    break
                }
            }
        }

        return safeReports
    }

    // Test if implementation meets criteria from the description, like:
    check(part2(listOf("75 78 80 81 79")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
