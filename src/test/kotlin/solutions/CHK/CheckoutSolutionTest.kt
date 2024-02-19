package solutions.CHK

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import solutions.CHK.CheckoutSolution.checkout

class CheckoutSolutionTest {

    @Test
    fun testSingleItem() {
        assertEquals(50, checkout("A"))
    }

    @Test
    fun testMultipleItems(){
        assertEquals(80, checkout("AB"))
    }

    @Test
    fun testSpecialOffer() {
        assertEquals(130, checkout("AAA"))
    }

    @Test
    fun testSpecialOfferWithMultipleItems() {
        assertEquals(175, checkout("AAABB"))
    }

    @Test
    fun illegalInput() {
        assertEquals(-1, checkout("E"))
    }
}