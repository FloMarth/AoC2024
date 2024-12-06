fun main() {

    fun findObstacles(inputMap: List<String>): MutableList<Coordinate> {
        val obstaclesList: MutableList<Coordinate> = mutableListOf()
        for (y in inputMap.indices) {
            for (x in inputMap.indices) {
                if (inputMap[y][x] == '#') {
                    obstaclesList.add(Coordinate(x, y))
                }
            }
        }
        return obstaclesList
    }

    fun getStartingPosition(inputMap: List<String>): Coordinate {
        for (y in inputMap.indices) {
            for (x in inputMap.indices) {
                if (inputMap[y][x] == '^') {
                    return Coordinate(x, y)
                }
            }
        }
        throw IllegalArgumentException("There has to be at least one starting position!")
    }

    fun needDirectionChange(position: Coordinate, obstacles: List<Coordinate>, direction: DirectionCode): Boolean {
        var tempPosition = position.clone()
        when (direction) {
            DirectionCode.UP -> tempPosition.y--
            DirectionCode.RIGHT -> tempPosition.x++
            DirectionCode.DOWN -> tempPosition.y++
            DirectionCode.LEFT -> tempPosition.x--
        }

        return obstacles.contains(tempPosition)
    }

    fun alterPosition(position: Coordinate, direction: DirectionCode) {
        when (direction) {
            DirectionCode.UP -> position.y--
            DirectionCode.RIGHT -> position.x++
            DirectionCode.DOWN -> position.y++
            DirectionCode.LEFT -> position.x--
        }
    }

    fun part1(inputMap: List<String>): Int {
        val obstacles = findObstacles(inputMap)
        var position = getStartingPosition(inputMap)
        val boundariesY = inputMap.size
        val boundariesX = inputMap[0].length
        val traceSet = mutableSetOf<Coordinate>()

        var direction = DirectionCode.UP

        traceSet.add(position)

        while(true) {
            if (needDirectionChange(position, obstacles, direction)) {
                direction = direction.next()
            }
            position = position.clone()

            alterPosition(position, direction)

            if (position.x < 0 || position.x == boundariesX || position.y == boundariesY || position.y < 0) {
                return traceSet.size
            }

            traceSet.add(position)
        }
    }

    fun part2(inputMap: List<String>): Int {
        val obstacles = findObstacles(inputMap)
        var position = getStartingPosition(inputMap)
        val boundariesY = inputMap.size
        val boundariesX = inputMap[0].length
        val traceSet = mutableSetOf<Coordinate>()

        var direction = DirectionCode.UP

        traceSet.add(position)

        while(true) {
            while (needDirectionChange(position, obstacles, direction)) {
                direction = direction.next()
            }

            position = position.clone()

            alterPosition(position, direction)

            if (position.x < 0 || position.x == boundariesX || position.y == boundariesY || position.y < 0) {
                break
            }

            traceSet.add(position)
        }

        var alteredTraceSet: MutableSet<DirectedPath>
        var sumOfPossibleObstaclePositions = 0

        for (i in traceSet.indices){
            alteredTraceSet = mutableSetOf()
            position = getStartingPosition(inputMap)
            direction = DirectionCode.UP

            //Placing something here is not allowed
            if (traceSet.elementAt(i) != position) {
                obstacles.remove(traceSet.elementAt(i-1))
                obstacles.add(traceSet.elementAt(i))
            }

            while(true) {
                while (needDirectionChange(position, obstacles, direction)) {
                    direction = direction.next()
                }
                position = position.clone()

                alterPosition(position, direction)

                if (position.x < 0 || position.x == boundariesX || position.y == boundariesY || position.y < 0) {
                    break
                }

                if (alteredTraceSet.contains(DirectedPath(position, direction))) {
                    sumOfPossibleObstaclePositions++
                    break
                }

                alteredTraceSet.add(DirectedPath(position, direction))
            }

        }
        return sumOfPossibleObstaclePositions
    }

    val testInputMap = readInput("Day06_test")

    check(part1(testInputMap) == 41)
    check(part2(testInputMap) == 6)

    val inputMap = readInput("Day06")
    part1(inputMap).println()
    part2(inputMap).println()
}

class Coordinate (var x: Int, var y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Coordinate) return false
        if (other.x == this.x && other.y == this.y) return true

        return false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}

fun Coordinate.clone(): Coordinate {
    val coordinate = Coordinate(x, y)
    return coordinate
}

class DirectedPath (var coordinate: Coordinate, var directionCode: DirectionCode){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DirectedPath) return false
        if (other.coordinate == this.coordinate && other.directionCode.ordinal == this.directionCode.ordinal) return true

        return false
    }

    override fun hashCode(): Int {
        var result = coordinate.hashCode()
        result = 31 * result + directionCode.hashCode()
        return result
    }
}

enum class DirectionCode {
    UP, RIGHT, DOWN, LEFT
}

inline fun <reified T: Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}