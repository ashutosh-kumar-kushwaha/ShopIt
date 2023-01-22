package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.CartProductsResponse
import ashutosh.shopit.models.DefaultResponse
import javax.inject.Inject

class CartRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val getCartProductsResponse = SingleLiveEvent<NetworkResult<CartProductsResponse>>()
    val changeProductQuantity = SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun getCartProducts(){
        getCartProductsResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getCartProducts()
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        getCartProductsResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        getCartProductsResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    getCartProductsResponse.value = NetworkResult.Error(401, "Token expired")
                }
                else -> {
                    getCartProductsResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            getCartProductsResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun increaseProductQuantity(productId: Int){
        changeProductQuantity.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.increaseProductQuantity(productId)
            when(response.code()){
                200 -> {
                    changeProductQuantity.value = NetworkResult.Success(200, DefaultResponse("", true))
                    getCartProducts()
                }
                else -> {
                    changeProductQuantity.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            changeProductQuantity.value = NetworkResult.Error(-1, e.message)
        }
    }

}