package com.mercadolibre.productsearch.interfaceadapters.gateways

import com.mercadolibre.productsearch.usecases.UseCaseError
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ResponseWrapperTest {

    @Test
    fun `given a response wrapper, with default values, then has no error`() {
        val responseWrapper = ResponseWrapper<String>()

        val error = responseWrapper.hasError()

        assertFalse(error)
    }

    @Test
    fun `given a response wrapper, with an error, then has no error`() {
        val responseWrapper = ResponseWrapper("Response", UseCaseError(error = "error"))

        val hasError = responseWrapper.hasError()

        assertTrue(hasError)
    }

    @Test
    fun `given a response wrapper, with an error message, then has no error`() {
        val responseWrapper = ResponseWrapper("Response", UseCaseError(message = "message"))

        val hasError = responseWrapper.hasError()

        assertTrue(hasError)
    }

    @Test
    fun `given a response wrapper, with an error and error message, then has no error`() {
        val responseWrapper = ResponseWrapper("Response", UseCaseError(error = "error", message = "message"))

        val hasError = responseWrapper.hasError()

        assertTrue(hasError)
    }
}
