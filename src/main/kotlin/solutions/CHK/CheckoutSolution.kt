package solutions.CHK

object CheckoutSolution {
    private val prices = mapOf(
        'A' to 50,
        'B' to 30,
        'C' to 20,
        'D' to 15
    )

    private val offers = mapOf(
        'A' to Pair(3, 130),
        'B' to Pair(2, 45)
    )

    fun checkout(skus: String): Int {
        if (!skus.all { it in prices.keys }) return -1

        val itemCounts
    }
}