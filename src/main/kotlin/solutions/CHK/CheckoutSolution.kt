package solutions.CHK

object CheckoutSolution {
    private val prices = mapOf(
        'A' to 50,
        'B' to 30,
        'C' to 20,
        'D' to 15,
        'E' to 40,
        'F' to 10,
        'G' to 20,
        'H' to 10,
        'I' to 35,
        'J' to 60,
        'K' to 80,
        'L' to 90,
        'M' to 15,
        'N' to 40,
        'O' to 10,
        'P' to 50,
        'Q' to 30,
        'R' to 50,
        'S' to 30,
        'T' to 20,
        'U' to 40,
        'V' to 50,
        'W' to 20,
        'X' to 90,
        'Y' to 10,
        'Z' to 50
        )

    private val offers = mapOf(
        'A' to listOf(Pair(3, 130), Pair(5, 200)),
        'B' to listOf(Pair(2, 45)),
        'H' to listOf(Offer(10, 80), Offer(5, 45)),
        'K' to listOf(Offer(2, 150)),
        'N' to listOf(Offer(3, 0, 'M', 1)),
        'P' to listOf(Offer(5, 200)),
        'Q' to listOf(Offer(3, 80)),
        'R' to listOf(Offer(3, 0, 'Q', 1)),
        'U' to listOf(Offer(3, 0, 'U', 1)),
    )

    data class Offer(
        val quantity: Int,
        val price: Int = 0,
        val bonusItem: Char = '\u0000',
        val bonusQuantity: Int = 0
    )

    fun checkout(skus: String): Int {
        if (skus.any { it !in prices }) return -1

        val itemCounts = skus.groupingBy { it } .eachCount().toMutableMap()

        applySpecialOfferForF(itemCounts)

        applyBonusOffers(itemCounts)

        val totalPrice = itemCounts.entries.sumOf {(item, count) ->
            applyOffers(item, count)
        }


        return totalPrice
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
                if (itemCounts.containsKey(bonusItem)) {
                    itemCounts[bonusItem] = maxOf(0, itemCounts[bonusItem]!! - bonusTimes)
                }
            }
        }
    }

    private fun applySpecialOfferForF(itemCounts: MutableMap<Char, Int>) {
        itemCounts['F']?.let {count ->
            val freeFs = count / 3
            itemCounts['F'] = count - freeFs
        }
    }
}


