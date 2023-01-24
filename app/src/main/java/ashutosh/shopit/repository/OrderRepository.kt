package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.Address
import ashutosh.shopit.models.CartProductsResponse
import ashutosh.shopit.models.OrderResponse
import javax.inject.Inject

class OrderRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val addressResponse = SingleLiveEvent<NetworkResult<List<Address>>>()
    val placeOrderResponse = SingleLiveEvent<NetworkResult<OrderResponse>>()
    val getCartProductsResponse = SingleLiveEvent<NetworkResult<CartProductsResponse>>()

    suspend fun getAddresses(){
        addressResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getAddresses()
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        addressResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        addressResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null")
                    }
                }
                else -> {
                    addressResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addressResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun placeOrderByCart(addressId: Int){
        placeOrderResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.placeOrderByCart(addressId)
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        placeOrderResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        placeOrderResponse.value = NetworkResult.Error(200, "Something went wrong!\nError: Response is null")
                    }
                }
                401 -> {
                    placeOrderResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                else -> {
                    placeOrderResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: Response code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            placeOrderResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
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
}