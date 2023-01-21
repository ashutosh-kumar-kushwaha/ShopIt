package ashutosh.shopit.repository

import android.net.Network
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.ProductDetailsResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val productDetailsResponse = SingleLiveEvent<NetworkResult<ProductDetailsResponse>>()
    val addToCartResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()

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

    suspend fun addToCart(productId: Int){
        addToCartResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.addToCart(productId)
            when(response.code()){
                200 -> {
                    if (response.body() != null) {
                        addToCartResponse.value = NetworkResult.Success(response.body()!!)
                    }
                    else{
                        addToCartResponse.value = NetworkResult.Error("Something went wrong!\nError: Response is null}")
                    }
                }
                409 -> {
                    addToCartResponse.value = NetworkResult.Error("Product already exist in the cart")
                }
                else -> {
                    addToCartResponse.value = NetworkResult.Error("Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addToCartResponse.value = NetworkResult.Error(e.message)
        }
    }

}