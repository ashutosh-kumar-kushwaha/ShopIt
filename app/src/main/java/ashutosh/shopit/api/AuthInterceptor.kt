package ashutosh.shopit.api

import android.util.Log
import ashutosh.shopit.datastore.DataStoreManager
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreManager.getAccessToken()
        }

        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        Log.d("Ashu", token)
        return chain.proceed(request.build())
    }
}