package interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OfflineInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        throw IOException("No internet");
    }
}
