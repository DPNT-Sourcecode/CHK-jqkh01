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
        assertEquals(-1, checkout("@"))
        assertEquals(-1, checkout("AAABB@"))
    }

    @Test
    fun testUpdatedAOffersAndE () {
        assertEquals(200, checkout("AAAAA") )
        assertEquals(40, checkout("E"))
        assertEquals(80, checkout("EEB"))
    }

    @Test
    fun testWithF () {
        assertEquals(30, checkout("FFFF"))
        assertEquals(10, checkout("F"))
        assertEquals(20, checkout("FF"))
        assertEquals(40, checkout("FFFFFF"))
    }
}