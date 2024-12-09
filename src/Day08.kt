import java.lang.Math.max
import kotlin.math.abs
import kotlin.math.min

fun main() {

    fun getFrequencies(input: List<String>): Set<Frequency> {
        val frequencies: MutableSet<Frequency> = mutableSetOf()
        for (y in input.indices) {
            for (x in input.indices) {
                val currentPosition = input[y][x]
                if (currentPosition != '.') {
                    if (!frequencies.contains(Frequency(currentPosition))) {
                        frequencies.add(Frequency(currentPosition))
                    }
                    frequencies.first { it == Frequency(currentPosition) }.antennas.add(Antenna(Coordinate(x,y)))
                }
            }
        }
        return frequencies
    }

    fun sumUpAllAntinodes(frequencies: Set<Frequency>, upperBoundX: Int, upperBoundY: Int): Int {
        val antinodesSet: MutableSet<Antinode> = mutableSetOf()
        frequencies.forEach { frequency ->
            frequency.antinodes.forEach { antinode ->
                if (antinode.coordinate.x >= 0 && antinode.coordinate.y >= 0 &&
                    antinode.coordinate.x < upperBoundX && antinode.coordinate.y < upperBoundY) {
                    antinodesSet.add(antinode)
                }
            }
        }

        return antinodesSet.size
    }


    fun part1(input: List<String>): Int {
        var frequencies = getFrequencies(input)

        frequencies.forEach() { frequency ->
            frequency.antennas.forEach { outerAntenna ->
                frequency.antennas.forEach { innerAntenna ->
                    if (outerAntenna != innerAntenna && outerAntenna.coordinate.x > innerAntenna.coordinate.x) {
                        val xDiff = outerAntenna.coordinate.x - innerAntenna.coordinate.x
                        val yDiff = outerAntenna.coordinate.y - innerAntenna.coordinate.y
                        frequency.antinodes.add(Antinode(Coordinate(outerAntenna.coordinate.x + xDiff, outerAntenna.coordinate.y + yDiff)))
                        frequency.antinodes.add(Antinode(Coordinate(innerAntenna.coordinate.x - xDiff, innerAntenna.coordinate.y - yDiff)))
                    }
                }
            }
        }
        return sumUpAllAntinodes(frequencies, input[0].length, input.size)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day08_test")

    check(part1(testInput) == 14)
    //check(part2(testInput) == 11387)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

class Antenna(var coordinate: Coordinate) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Antenna) return false
        return this.coordinate == other.coordinate
    }

    override fun hashCode(): Int {
        return 31 * coordinate.hashCode()
    }
}

class Antinode(var coordinate: Coordinate) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Antinode) return false
        return this.coordinate == other.coordinate
    }

    override fun hashCode(): Int {
        return 31 * coordinate.hashCode()
    }
}

class Frequency (var letter: Char) {
    var antennas: MutableSet<Antenna> = mutableSetOf()
    var antinodes: MutableSet<Antinode> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Frequency) return false
        return this.letter == other.letter
    }

    override fun hashCode(): Int {
        return letter.hashCode()
    }
}