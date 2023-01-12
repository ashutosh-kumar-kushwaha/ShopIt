package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.ProductDetailsResponse

class ProductRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    val productDetailsResponse = SingleLiveEvent<NetworkResult<ProductDetailsResponse>>()

    suspend fun getProductDetails(productId : Int){
        productDetailsResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getProductDetails(productId)
            when(response.code()){
                200 -> {
                    if (response.body() != null){
                        productDetailsResponse.value = NetworkResult.Success(response.body()!!)
                    }
                    else{
                        productDetailsResponse.value = NetworkResult.Error("Something went wrong!\nError: Response is null}")
                    }
                }
                else -> {
                    productDetailsResponse.value = NetworkResult.Error("Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            productDetailsResponse.value = NetworkResult.Error(e.message)
        }
    }

}