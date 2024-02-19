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
        assertEquals(80, checkout("EE"))
    }

    @Test
    fun testWithF () {
        assertEquals(30, checkout("FFFF"))
        assertEquals(10, checkout("F"))
        assertEquals(20, checkout("FF"))

    }

    @Test
    fun testWithU() {
        assertEquals(120, checkout("UUUU"))
        assertEquals(160, checkout("UUUUU"))
        assertEquals(240, checkout("UUUUUUUU"))
    }

    @Test
    fun testWithEB() {
        assertEquals(160, checkout("EEEEBB"))
        assertEquals(160, checkout("BEBEEE"))
        assertEquals(300, checkout("RRRRRRQQ"))
    }

    @Test
    fun testGroupOffer(){
        assertEquals(110, checkout("STSXYZS"))
        assertEquals(111, checkout("STSXYZZ"))
        assertEquals(110+160, checkout("STSXYZSEEEEBB"))

    }
}