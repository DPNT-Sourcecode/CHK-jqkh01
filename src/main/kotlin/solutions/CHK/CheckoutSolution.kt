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
        'S' to 20,
        'T' to 20,
        'U' to 40,
        'V' to 50,
        'W' to 20,
        'X' to 17,
        'Y' to 20,
        'Z' to 21
    )

    private val offers = mapOf(
        'A' to listOf(Offer(3, 130), Offer(5, 200)),
        'B' to listOf(Offer(2, 45)),
        'E' to listOf(Offer(2, 80, 'B', 1)),
        'F' to listOf(Offer(3, 20)),
        'H' to listOf(Offer(10, 80), Offer(5, 45)),
        'K' to listOf(Offer(2, 120)),
        'N' to listOf(Offer(3, 120, 'M', 1)),
        'P' to listOf(Offer(5, 200)),
        'Q' to listOf(Offer(3, 80)),
        'R' to listOf(Offer(3, 150, 'Q', 1)),
        'U' to listOf(Offer(4, 120)),
        'V' to listOf(Offer(3, 130), Offer(2, 90)),
        )

    data class Offer(
        val quantity: Int,
        val price: Int = 0,
        val bonusItem: Char = '\u0000',
        val bonusQuantity: Int = 0
    )

    private val itemsInGroupOffer = listOf('S', 'T', 'X', 'Y', 'Z')

    fun checkout(skus: String): Int {
        if (skus.any { it !in prices }) return -1

        val itemCounts = skus.groupingBy { it }.eachCount().toMutableMap()

        val groupOfferCost = applyGroupOffers(itemCounts, skus)

        val itemOfferPrices = applyOffers(itemCounts)

        return groupOfferCost + itemCounts.entries.sumOf { (item, count) ->
            count * prices.getValue(item) + itemOfferPrices.getValue(item)
        }
    }


    private fun applyOffers(itemCounts: MutableMap<Char, Int>): MutableMap<Char, Int> {
        val itemOfferPrices: MutableMap<Char, Int> = itemCounts.mapValues { 0 }.toMutableMap()

        applySpecialOffers(itemCounts, itemOfferPrices)

        applyRegularOffers(itemCounts, itemOfferPrices)

        return itemOfferPrices
    }

    private fun applyGroupOffers(
        itemCounts: MutableMap<Char, Int>,
        skus: String
    ): Int {
        val temp: ArrayList<Char> = ArrayList()
        var cost = 0

        for (c in skus) {
            if (c in itemsInGroupOffer) {
                if (temp.size == 3) {
                    cost += 45
                    temp.clear()
                }

                if (itemCounts[c] != 0) {
                    itemCounts[c] = 0
                }

                temp.add(c)
            }
        }

        for (c in temp) {
            cost += prices[c]!!
        }

        return cost
    }

    private fun applyRegularOffers(
        itemCounts: MutableMap<Char, Int>,
        itemOfferPrices: MutableMap<Char, Int>
    ) {
        offers.forEach { (item, offerList) ->
            var count = itemCounts[item] ?: 0

            offerList.sortedByDescending { it.quantity }.forEach { offer ->

                if (offer.bonusItem == '\u0000') {
                    val applicableTimes = count / offer.quantity
                    if (applicableTimes > 0) {
                        itemOfferPrices[item] = itemOfferPrices[item]!! + applicableTimes * offer.price
                        itemCounts[item] = itemCounts[item]!! - applicableTimes * offer.quantity
                        count = itemCounts[item]!!
                    }
                }
            }
        }
    }

    private fun applySpecialOffers(
        itemCounts: MutableMap<Char, Int>,
        itemOfferPrices: MutableMap<Char, Int>
    ) {
        val specialOffers = offers.mapValues { (_, offerList) ->
            offerList.filter { it.bonusItem != '\u0000' }
        }

        specialOffers.forEach { (item, offerList) ->
            val count = itemCounts[item] ?: 0

            offerList.sortedByDescending { it.quantity }.forEach { offer ->
                val bonusApplies = count / offer.quantity
                if (bonusApplies > 0 && itemCounts[offer.bonusItem] != null) {
                    itemOfferPrices[item] = itemOfferPrices[item]!! + bonusApplies * offer.price
                    itemCounts[offer.bonusItem] = itemCounts[offer.bonusItem]!! - bonusApplies * offer.bonusQuantity
                    itemCounts[item] = itemCounts[item]!! - bonusApplies * offer.quantity
                }
            }
        }
    }
}

