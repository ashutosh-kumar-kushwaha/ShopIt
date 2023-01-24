package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.AddAddressRequest
import ashutosh.shopit.models.Address
import javax.inject.Inject

class AddAddressRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val addAddressResponse = SingleLiveEvent<NetworkResult<List<Address>>>()

    suspend fun addAddress(addAddressRequest: AddAddressRequest){
        addAddressResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.addAddress(addAddressRequest)
            when(response.code()){
                200 -> {
                    addAddressResponse.value = NetworkResult.Success(200, response.body()!!)
                }
                401 -> {
                    addAddressResponse.value = NetworkResult.Error(401, "Token expired")
                }
                else -> {
                    addAddressResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            addAddressResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}