package interceptor

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class ResponseInterceptor : Interceptor {
    private val error400Response = "{\n" +
            "    \"message\": \"Some error message\",\n" +
            "    \"error\": \"Some error\",\n" +
            "    \"status\": 400,\n" +
            "    \"cause\": []\n" +
            "}"
    private val error500Response = "{\n" +
            "    \"message\": \"Some Backend error message\",\n" +
            "    \"error\": \"Some Backend error\",\n" +
            "    \"status\": 500,\n" +
            "    \"cause\": []\n" +
            "}"
    private val invalidJsonResponse = "{"
    private val nullResponse = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().uri().toString()
        val request = chain.request()
        val response = chain.proceed(request)

        val body = response.body()

        val code = when {
            uri.contains("sites/MLA/search?q=product1") -> 400
            uri.contains("sites/MLA/search?q=product2") -> 500
            uri.contains("sites/MLA/search?q=product3") -> 200
            uri.contains("sites/MLA/search?q=product4") -> 200
            else -> response.code()
        }
        val responseString = when {
            uri.contains("sites/MLA/search?q=product1") -> error400Response
            uri.contains("sites/MLA/search?q=product2") -> error500Response
            uri.contains("sites/MLA/search?q=product3") -> invalidJsonResponse
            uri.contains("sites/MLA/search?q=product4") -> nullResponse
            else -> body?.string() ?: ""
        }

        val contentType: MediaType? = response.body()?.contentType()
        val responseBody = ResponseBody.create(contentType, responseString)

        return response
            .newBuilder()
            .code(code)
            .body(responseBody)
            .build()
    }
}
