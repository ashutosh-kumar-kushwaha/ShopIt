package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.Address
import ashutosh.shopit.models.Profile
import ashutosh.shopit.models.UpdateProfileRequest
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val profileResponse = SingleLiveEvent<NetworkResult<Profile>>()
    val updateProfileResponse = SingleLiveEvent<NetworkResult<Profile>>()
    val addressResponse = SingleLiveEvent<NetworkResult<List<Address>>>()

    suspend fun getProfile(){
        profileResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getProfile()
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        profileResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        profileResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    profileResponse.value = NetworkResult.Error(401, "Session expired")
                }
                else -> {
                    profileResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            profileResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun updateProfile(updateProfileRequest: UpdateProfileRequest){
        profileResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.updateProfile(updateProfileRequest)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        updateProfileResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        updateProfileResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    updateProfileResponse.value = NetworkResult.Error(401, "Session expired")
                }
                else -> {
                    updateProfileResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            profileResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

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