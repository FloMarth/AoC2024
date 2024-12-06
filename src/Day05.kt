fun main() {
    fun getOrderRuleForOrder(orderRules: List<List<Int>>, order: List<Int>): List<List<Int>> {
        val mutableOrderRulesList = orderRules.toMutableList()

        orderRules.forEach {
            if (!order.contains(it[0]) || !order.contains(it[1])) {
                mutableOrderRulesList.removeAt(mutableOrderRulesList.indexOf(it))
            }
        }

        return mutableOrderRulesList
    }

    fun splitOrderRules(input:List<String>): List<List<Int>> {
        val foo =  input.stream().map { it.split("\\|".toRegex()) }.
        map { outerIt -> outerIt.stream().map { it.toInt() }.toList() }.toList()

        return foo
    }

    fun splitOrders(input: List<String>): List<List<Int>> {
        return input.stream().map {it.split("\\,".toRegex())}.
        map { outerIt -> outerIt.stream().map { it.toInt() }.toList() }.toList()
    }

    fun part1(orderRules: List<List<Int>>, orders: List<List<Int>>): Int {
        var finalSum = 0

        orders.forEach { it: List<Int> ->
            var correctOrder = true
            val orderRule = getOrderRuleForOrder(orderRules, it)

            orderRule.forEach { innerIt ->
                val firstElem = innerIt[0]
                val secondElem = innerIt[1]

                if (it.indexOf(firstElem) > it.indexOf(secondElem)) {
                    correctOrder = false
                }
            }
            if (correctOrder) {
                finalSum += it[it.size / 2]
            }

        }

        return finalSum
    }

    fun reorderOrder(order: MutableList<Int>, orderRules: List<List<Int>>): List<Int> {
        var wereChange = true

        while (wereChange) {
            wereChange = false
            orderRules.forEach {
                val firstElem = it[0]
                val secondElem = it[1]

                if (order.indexOf(firstElem) > order.indexOf(secondElem)) {
                    order.remove(firstElem)
                    order.add(order.indexOf(secondElem), firstElem)
                    wereChange = true
                }
            }
        }

        return order
    }

    fun part2(orderRules: List<List<Int>>, orders: List<List<Int>>): Int {
        var finalSum = 0

        orders.forEach { it: List<Int> ->
            var correctOrder = true
            val orderRule = getOrderRuleForOrder(orderRules, it)

            orderRule.forEach { innerIt ->
                val firstElem = innerIt[0]
                val secondElem = innerIt[1]

                if (it.indexOf(firstElem) > it.indexOf(secondElem)) {
                    correctOrder = false
                }
            }
            if (!correctOrder) {
                val reorderedOrder = reorderOrder(it.toMutableList(), orderRule)
                finalSum += reorderedOrder[reorderedOrder.size / 2]
            }
        }

        return finalSum
    }

    val testOrderRules = splitOrderRules(readInput("Day05_order_rules_test"))
    val testOrders = splitOrders(readInput("Day05_orders_test"))

    check(part1(testOrderRules, testOrders) == 143)
    check(part2(testOrderRules, testOrders) == 123)

    // Read the input from the `src/Day05_order_rules_test.txt` file.
    val orderRules = splitOrderRules(readInput("Day05_order_rules"))
    val orders = splitOrders(readInput("Day05_orders"))
    part1(orderRules, orders).println()
    part2(orderRules, orders).println()
}
