package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.UpdateOrderRequest
import ashutosh.shopit.models.UpdateOrderResponse
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val updateOrderResponse = SingleLiveEvent<NetworkResult<UpdateOrderResponse>>()

    suspend fun updateOrder(updateOrderRequest: UpdateOrderRequest){
        updateOrderResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.updateOrder(updateOrderRequest)
            when(response.code()){
                200 -> {
                    updateOrderResponse.value = NetworkResult.Success(200, response.body()!!)
                }
                401 -> {
                    updateOrderResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                else -> {
                    updateOrderResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            updateOrderResponse.value = NetworkResult.Error(-1, e.message)
            e.printStackTrace()
        }
    }

    suspend fun updateDirectOrder(productId: Int, quantity: Int, updateOrderRequest: UpdateOrderRequest){
        updateOrderResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.updateDirectOrder(productId, quantity, updateOrderRequest)
            when(response.code()){
                200 -> {
                    updateOrderResponse.value = NetworkResult.Success(200, response.body()!!)
                }
                401 -> {
                    updateOrderResponse.value = NetworkResult.Error(401, "Session Expired")
                }
                else -> {
                    updateOrderResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            updateOrderResponse.value = NetworkResult.Error(-1, e.message)
            e.printStackTrace()
        }
    }
}