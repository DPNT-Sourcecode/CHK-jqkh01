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
        'A' to listOf(Offer(3, 130), Offer(5, 200)),
        'B' to listOf(Offer(2, 45)),
        'H' to listOf(Offer(10, 80), Offer(5, 45)),
        'K' to listOf(Offer(2, 150)),
        'N' to listOf(Offer(3, 120, 'M', 1)),
        'P' to listOf(Offer(5, 200)),
        'Q' to listOf(Offer(3, 80)),
        'R' to listOf(Offer(3, 150, 'Q', 1)),
        'U' to listOf(Offer(4, 120, 'U', 1)),
        'V' to listOf(Offer(3, 130), Offer(2, 90)),
        'F' to listOf(Offer(3,20))
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

        applyOffers(itemCounts)

        return itemCounts.entries.sumOf { (item, count) ->
            count * prices.getValue(item)
        }
    }

    private fun applyOffers(itemCounts: MutableMap<Char, Int>) {
        offers.forEach { (item, offerList) ->
            offerList.sortedByDescending {it.quantity}. forEach { offer ->
                var count = itemCounts[item] ?: 0

                if (offer.bonusItem != '\u0000') {
                    val bonusApplies = count / offer.quantity
                    itemCounts[offer.bonusItem] = itemCounts.getOrDefault(offer.bonusItem, 0) - (bonusApplies * offer.bonusQuantity)
                    if (itemCounts[offer.bonusItem]!! < 0) itemCounts[offer.bonusItem] = 0
                } else {
                    val applicableTimes = count / offer.quantity
                    itemCounts[item] = count - (applicableTimes * offer.quantity)
                }
            }
        }
    }
}