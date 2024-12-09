fun main() {

    fun part1(input: String): Long {
        val diskMap: MutableList<String> = mutableListOf()
        var id = 0
        for (i in input.indices) {
            if (i % 2 == 0) {
                val listToAdd = List(input[i].code - '0'.code){id.toString()}
                diskMap.addAll(listToAdd)
                id++
            } else {
                val listToAdd = List(input[i].code - '0'.code) {"."}
                diskMap.addAll(listToAdd)
            }
        }

        val reversedDiskMap = diskMap.reversed()

        for (i in reversedDiskMap.indices) {
            if (reversedDiskMap[i] != ".") {
                val firstFreeSpace = diskMap.indexOfFirst { it == "." }
                if (firstFreeSpace < diskMap.size - i - 1) {
                    diskMap[firstFreeSpace] = reversedDiskMap[i]
                    diskMap[diskMap.size - i - 1] = "."
                }
            }
        }

        var sum = 0L

        for (i in diskMap.indices) {
            if (diskMap[i] != ".") {
                sum += i * diskMap[i].toInt()

            }
        }

        return sum
    }

    fun part2(input: String): Long {
        val diskMap: MutableList<String> = mutableListOf()
        var id = 0
        for (i in input.indices) {
            if (i % 2 == 0) {
                val listToAdd = List(input[i].code - '0'.code){id.toString()}
                diskMap.addAll(listToAdd)
                id++
            } else {
                val listToAdd = List(input[i].code - '0'.code) {"."}
                diskMap.addAll(listToAdd)
            }
        }

        val reversedDiskMap = diskMap.reversed()

        for (i in reversedDiskMap.indices) {
            if (reversedDiskMap[i] != ".") {
                val firstFreeSpace = diskMap.indexOfFirst { it == "." }
                if (firstFreeSpace < diskMap.size - i - 1) {
                    diskMap[firstFreeSpace] = reversedDiskMap[i]
                    diskMap[diskMap.size - i - 1] = "."
                }
            }
        }

        var sum = 0L

        for (i in diskMap.indices) {
            if (diskMap[i] != ".") {
                sum += i * diskMap[i].toInt()
            }
        }

        return sum
    }

    val testInput = "2333133121414131402"

    check(part1(testInput) == 1928L)
    check(part1(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input[0]).println()
    part2(input[0]).println()
}