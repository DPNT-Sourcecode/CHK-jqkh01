package solutions.HLO

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import solutions.HLO.HelloSolution.hello

class HelloSolutionTest {

    @Test
    fun testHello() {
        assertEquals("Hello, Hoang!", hello("Hoang"))
    }
}