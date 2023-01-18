package ashutosh.shopit.api

import ashutosh.shopit.datastore.DataStoreManager
import ashutosh.shopit.models.LoginResponse
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(private val dataStoreManager: DataStoreManager) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        var refreshToken: String = ""
        runBlocking {
            dataStoreManager.getLogInInfo().collect {
                refreshToken = it.refreshToken!!
            }
        }
        return runBlocking {
            val apiResponse = regenerateAccessToken(refreshToken)

            if(!apiResponse.isSuccessful || apiResponse.body()==null){
                dataStoreManager.deleteAccessToken()
            }

            apiResponse.body()?.let {
                dataStoreManager.saveAccessToken(it.accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }

        }
    }

    private suspend fun regenerateAccessToken(refreshToken: String): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder().
            baseUrl("https://www.shopitanywhere.live/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        return retrofitAPI.regenerateAccessToken(refreshToken)
    }
}