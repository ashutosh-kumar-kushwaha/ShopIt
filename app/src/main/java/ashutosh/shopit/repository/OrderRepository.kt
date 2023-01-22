package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.Address
import javax.inject.Inject

class OrderRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val addressResponse = SingleLiveEvent<NetworkResult<List<Address>>>()

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
}