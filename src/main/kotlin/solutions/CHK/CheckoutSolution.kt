package solutions.CHK

object CheckoutSolution {
    private val prices = mapOf(
        'A' to 50,
        'B' to 30,
        'C' to 20,
        'D' to 15,
        'E' to 40
    )

    private val offers = mapOf(
        'A' to listOf(Pair(3, 130), Pair(5, 200)),
        'B' to listOf(Pair(2, 45)),
    )

    private val bonusOffers = mapOf(
        'E' to Pair('B', 1)
    )

    fun checkout(skus: String): Int {
        if (!skus.all { it in prices.keys }) return -1

        val itemCounts = skus.groupingBy { it } .eachCount().toMutableMap()

        val totalPrice = itemCounts.entries.sumOf {(item, count) ->
            applyOffers(item, count)
        }

        applyBonusOffers(itemCounts)

        return totalPrice - discountForBonusItems(itemCounts)
    }

    private fun applyOffers(item: Char, count: Int): Int {
        offers[item]?.let { itemOffers ->
            var total = 0
            var remainingCount = count
            for ((quantity, price) in itemOffers.sortedByDescending { it.first }) {
                val applicableTimes = remainingCount / quantity
                total += applicableTimes * price
                remainingCount -= applicableTimes * quantity
            }
            return total + remainingCount * prices.getValue(item)
        }
        return prices.getValue(item) * count
    }

    private fun applyBonusOffers(itemCounts: MutableMap<Char, Int>) {
        bonusOffers.forEach {(key, value) ->
            val (bonusItem, freeCount) = value
            val requiredForBonus = 2
            itemCounts[key]?.let {
                val bonusTimes = it / requiredForBonus
                itemCounts.merge(bonusItem, bonusTimes * freeCount) {oldValue, bonusValue ->
                    maxOf(0, oldValue - bonusValue)
                }
            }
        }
    }

    private fun discountForBonusItems(itemCounts: MutableMap<Char, Int>): Int =
        itemCounts.entries.sumOf { (item, count) ->
            if (item in bonusOffers.values.map {it.first}) {
                count * prices.getValue(item)
            } else 0
        }
}
