package ashutosh.shopit.repository

import android.util.Log
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.Product
import ashutosh.shopit.models.ProductsResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val searchResponse = SingleLiveEvent<NetworkResult<ProductsResponse>>()

    suspend fun search(keyword: String){
        searchResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.search(keyword)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        searchResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        searchResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError: Response is null")
                    }
                }
                401 -> searchResponse.value = NetworkResult.Error(401, "Session Expired")
                else -> searchResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            searchResponse.value = NetworkResult.Error(-1, e.message)
            Log.d("Ashu", e.message.toString())
            e.printStackTrace()
        }
    }
}