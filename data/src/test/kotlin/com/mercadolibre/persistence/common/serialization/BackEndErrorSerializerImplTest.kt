package com.mercadolibre.persistence.common.serialization

import com.google.gson.Gson
import com.mercadolibre.persistence.common.model.BackendModel
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class BackEndErrorSerializerImplTest {

    @Test
    fun `given a valid JSON string, when fromJson is called, then it returns the expected BackEndError`() {
        val serializer: BackEndErrorSerializer = BackEndErrorSerializerImpl(Gson())
        val error = BackendModel.BackEndError(message = "message", error = "error", status = 401)

        val result = serializer.fromJson(Gson().toJson(error))

        assertEquals(error.message, result.message)
        assertEquals(error.error, result.error)
        assertEquals(error.status, result.status)
    }

    @Test
    fun `given an invalid JSON string, when fromJson is called, then it returns the expected BackEndError`() {
        val serializer: BackEndErrorSerializer = BackEndErrorSerializerImpl(Gson())

        val result = serializer.fromJson("{\"id\":2323}")

        assertTrue(result.message.isEmpty())
        assertTrue(result.error.isEmpty())
        assertTrue(result.status == 0)
    }

    @Test
    fun `given a malformed JSON string, when fromJson is called, then it returns the expected BackEndError`() {
        val serializer: BackEndErrorSerializer = BackEndErrorSerializerImpl(Gson())

        val result = serializer.fromJson("{\"id\":2323")

        assertTrue(result.message.isEmpty())
        assertTrue(result.error.isEmpty())
        assertTrue(result.status == 0)
    }
}
