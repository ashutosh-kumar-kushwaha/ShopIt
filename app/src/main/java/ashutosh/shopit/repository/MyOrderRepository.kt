package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.MyOrderResponse
import javax.inject.Inject

class MyOrderRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val myOrdersResponse = SingleLiveEvent<NetworkResult<MyOrderResponse>>()

    suspend fun getAllOrders(){
        myOrdersResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getAllOrders()
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        myOrdersResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        myOrdersResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    myOrdersResponse.value = NetworkResult.Error(401, "Token expired")
                }
                else -> {
                    myOrdersResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            myOrdersResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}