fun main() {
    println("Horizontal Occurrences check")
    check(part1(listOf("MMMSXXMASM")) == 1)
    println("Horizontal Occurrences Reversed check")
    check(part1(listOf("MSAMXMSMSA")) == 1)
    println("Vertical Occurrences check")
    check(part1(listOf("MSAMASMSMX", "XMASAMXAMM", "XXAMMXXAMA", "SMSMSASXSS")) == 4)
    println("Vertical Occurrences Reversed check")
    check(part1(listOf("SMSMSASXSS", "SAXAMASAAA", "MAMMMXMMMM", "MXMXAXMASX")) == 8)


    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

fun part1(input: List<String>): Int {
    val horizontalOccurrences = getHorizontalOccurrences(input)
    val reversedHorizontalOccurrences = getReverseHorizontalOccurrences(input.toMutableList())
    val verticalOccurrences = getVerticalOccurrences(input)
    val reversedVerticalOccurrences = getReverseVerticalOccurrences(input.toMutableList())

    val diagonalOccurrences = diagonalOccurrencesBottomLeftTopRight(input) + diagonalOccurrencesBottomRightTopLeft(input) + diagonalOccurrencesTopLeftBottomRight(input) + diagonalOccurrencesTopRightBottomLeft(input)

    return horizontalOccurrences + reversedHorizontalOccurrences + verticalOccurrences + reversedVerticalOccurrences + diagonalOccurrences
}

fun part2(input: List<String>): Int {
    return numberOfX_Masses(input)
}

fun numberOfX_Masses(input: List<String>): Int {
    val maxInputIndex = input.count() - 1
    val maxLineIndex = input[0].length - 1
    var wordCount = 0

    for (i in input.indices) {
        for (j in input[0].indices) {
            if (i < maxInputIndex && i > 0 && j < maxLineIndex && j > 0) {
                if (input[i][j] == 'A') {
                    //Here is a possible X-MAS! Lets check the corners!
                    if ((input[i-1][j-1] == 'M' && input[i+1][j+1] == 'S') || (input[i-1][j-1] == 'S' && input[i+1][j+1] == 'M')) {
                        //Jaay! We found one XMAS. Is there another one, though?
                        if ((input[i+1][j-1] == 'M' && input[i-1][j+1] == 'S') || (input[i+1][j-1] == 'S' && input[i-1][j+1] == 'M')) {
                            //We finally found it!!!!!!!
                            wordCount++
                        }
                    }
                }
            }
        }
    }

    return wordCount
}

fun getHorizontalOccurrences(input: List<String>): Int {
    val regex = Regex("(XMAS)")

    var wordCount = 0
    input.forEach { line ->
        wordCount += regex.findAll(line).count()
    }

    return wordCount
}

fun getReverseHorizontalOccurrences(input: MutableList<String>): Int {
    val reversedInput = reverse(input)

    return getHorizontalOccurrences(reversedInput)
}

fun getVerticalOccurrences(input: List<String>): Int {
    val transposedInput = transposeArray(input)

    return getHorizontalOccurrences(transposedInput)
}

fun getReverseVerticalOccurrences(input: List<String>): Int {
    val transposedInput = transposeArray(input)

    return getReverseHorizontalOccurrences(transposedInput.toMutableList())
}

fun transposeArray(input: List<String>): List<String> {
    val array = Array(input[0].length) { "" }

    for (i in input.indices) {
        for (j in input[i].indices) {
            array[j] = array[j] + input[i][j]
        }
    }

    return array.toList()
}

fun diagonalOccurrencesTopLeftBottomRight(input: List<String>): Int {
    val maxInputIndex = input.count() - 1
    val maxLineIndex = input[0].length - 1
    var wordCount = 0

    for (i in input.indices) {
        for (j in input[i].indices) {
            if (i+3 <= maxInputIndex &&
                j+3 <= maxLineIndex &&
                input[i][j] == 'X' &&
                input[i+1][j+1] == 'M' &&
                input[i+2][j+2] == 'A' &&
                input[i+3][j+3] == 'S') {
                wordCount++
            }
        }
    }

    return wordCount
}

fun diagonalOccurrencesTopRightBottomLeft(input: List<String>): Int {
    val reversedInput = reverse(input.toMutableList())

    return diagonalOccurrencesTopLeftBottomRight(reversedInput)
}

fun diagonalOccurrencesBottomLeftTopRight(input: List<String>): Int {
    val horizontallyMirroredInput = mirrorInputHorizontally(input)

    return diagonalOccurrencesTopLeftBottomRight(horizontallyMirroredInput)
}

fun diagonalOccurrencesBottomRightTopLeft(input: List<String>): Int {
    val reversedInput = reverse(input.toMutableList())
    val horizontallyMirroredInput = mirrorInputHorizontally(reversedInput)

    return diagonalOccurrencesTopLeftBottomRight(horizontallyMirroredInput)
}

fun reverse(input: MutableList<String>): List<String> {
    for (i in input.indices) {
        input[i] = input[i].reversed()
    }

    return input.toList()
}

fun mirrorInputHorizontally(input: List<String>): List<String> {
    val newList = input.toMutableList()

    for (i in newList.indices) {
        newList[i] = input[input.size - 1 - i]
    }

    return newList.toList()
}