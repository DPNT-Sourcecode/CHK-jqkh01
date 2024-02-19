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
        'E' to listOf(Pair(2, 80))
    )

    private val bonusOffers = mapOf(
        'E' to Pair('B', 1)
    )

    fun checkout(skus: String): Int {
        if (!skus.all { it in prices.keys }) return -1

        val itemCounts = skus.groupingBy { it } .eachCount().toMutableMap()

        val totalPrice = itemCounts.entries.sumOf {(item, count) ->
            val (offerQuantity, offerPrice) = offers[item] ?: Pair(0, 0)

            if (offerQuantity > 0) {
                val offerCount = count / offerQuantity
                val normalCount = count % offerQuantity
                (offerCount * offerPrice) + (normalCount * (prices[item] ?: 0))
            } else {
                count *(prices[item] ?:0)
            }
        }

        return totalPrice
    }

    private fun calculatePriceForItem(item: Char, count: Int): Int
}