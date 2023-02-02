package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.models.*
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val profileResponse = SingleLiveEvent<NetworkResult<Profile>>()
    val updateProfileResponse = SingleLiveEvent<NetworkResult<Profile>>()
    val addressResponse = SingleLiveEvent<NetworkResult<List<Address>>>()
    val updateEmailResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()
    val resetEmailResponse = SingleLiveEvent<NetworkResult<LoginResponse>>()
    val resendOtpResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()
    val deleteAddressResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()
    val changeProfilePicResponse = SingleLiveEvent<NetworkResult<ChangeProfilePicResponse>>()

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
        updateProfileResponse.value = NetworkResult.Loading()
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
            updateProfileResponse.value = NetworkResult.Error(-1, e.message)
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

    suspend fun updateEmail(email: Email){
        updateEmailResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.updateEmail(email)
            when(response.code()){
                200 -> {
                    updateEmailResponse.value = NetworkResult.Success(200, DefaultResponse("OTP sent successfully", true))
                }
                401 -> {
                    updateEmailResponse.value = NetworkResult.Error(401, "Session expired")
                }
                406 -> {
                    updateEmailResponse.value = NetworkResult.Error(406, "Email already exist")
                }
                else -> {
                    updateEmailResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            updateEmailResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun resetEmail(resetEmailRequest: ResetEmailRequest){
        resetEmailResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.resetEmail(resetEmailRequest)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        resetEmailResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        resetEmailResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }                }
                401 -> {
                    resetEmailResponse.value = NetworkResult.Error(401, "Session expired")
                }
                406 -> {
                    resetEmailResponse.value = NetworkResult.Error(406, "Invalid OTP")
                }
                else -> {
                    resetEmailResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            resetEmailResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun resendOtp(email : Email){
        resendOtpResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.updateEmail(email)
            when(response.code()){
                200 -> {
                    updateEmailResponse.value = NetworkResult.Success(200, DefaultResponse("OTP sent successfully", true))
                }
                401 -> {
                    updateEmailResponse.value = NetworkResult.Error(401, "Session expired")
                }
                406 -> {
                    updateEmailResponse.value = NetworkResult.Error(406, "Email already exist")
                }
                else -> {
                    updateEmailResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e : Exception){
            resendOtpResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun deleteAddress(addressId: Int){
        deleteAddressResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.deleteAddress(addressId)
            when(response.code()){
                200 -> {
                    deleteAddressResponse.value = NetworkResult.Success(200, DefaultResponse("Deleted", true))
                }
                401 -> {
                    deleteAddressResponse.value = NetworkResult.Error(401, "Session expired")
                }
                403 -> {
                    deleteAddressResponse.value = NetworkResult.Error(403, "User not allowed to perform this action")
                }
                else -> {
                    deleteAddressResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            deleteAddressResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun changeProfilePic(image: MultipartBody.Part){
        changeProfilePicResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.changeProfilePic(image)
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        changeProfilePicResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        changeProfilePicResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    changeProfilePicResponse.value = NetworkResult.Error(401, "Session expired")
                }
                else -> {
                    changeProfilePicResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            changeProfilePicResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}