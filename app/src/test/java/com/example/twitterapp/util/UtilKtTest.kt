package com.example.twitterapp.util

import org.junit.Assert.*

import org.junit.Test

class UtilKtTest {

    @Test
    fun emailPasswordIsEmpty() {
        val result = isDetailsValid("","")
        assertFalse(result)
    }

    @Test
    fun emailPasswordIsNotEmpty() {
        val result = isDetailsValid("a1@gmail.com","123456")
        assertTrue(result)
    }

    @Test
    fun emailIsEmpty() {
        val result = isDetailsValid("","123456")
        assertFalse(result)
    }

    @Test
    fun passwordIsEmpty() {
        val result = isDetailsValid("a1@gmail.com","")
        assertFalse(result)
    }

}