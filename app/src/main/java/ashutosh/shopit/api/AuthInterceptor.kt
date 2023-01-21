package ashutosh.shopit.api

import android.util.Log
import ashutosh.shopit.datastore.DataStoreManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreManager.getLogInInfo().first()
        }.accessToken

        val request = chain.request()
        val requestBuilder = request.newBuilder().removeHeader("isAuthorized")
        val shouldAddToken = request.headers["isAuthorized"]=="true"
        if(shouldAddToken){
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}