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

//        var accessToken = "acc"
//        CoroutineScope(Dispatchers.Default).launch {
//            accessToken = token.await()
//            Log.d("Ashu", "t:$accessToken")
//        }

        val request = chain.request()
        val shouldAddToken = request.headers["isAuthorized"]!="false"
        val requestBuilder = request.newBuilder().removeHeader("isAuthorized")
        if(shouldAddToken){
            requestBuilder.addHeader("Authorization", "Bearer $token")
            Log.d("Ashu", "Token added")
        }
        Log.d("Ashu", token.toString())


        return chain.proceed(requestBuilder.build())
    }
}