package ashutosh.shopit.repository

import android.util.Log
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.ProductsResponse
import javax.inject.Inject

class CategoryProductsRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val getProductsResponseLiveData = SingleLiveEvent<NetworkResult<ProductsResponse>>()

    suspend fun getProductsByCategory(categoryId : Int, sortBy: String, sortDir: String){
        getProductsResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getProductsByCategory(categoryId, sortBy, sortDir)
            Log.d("Ashu", response.body().toString())
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        getProductsResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        getProductsResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                    }
                }
                else -> getProductsResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            getProductsResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

}