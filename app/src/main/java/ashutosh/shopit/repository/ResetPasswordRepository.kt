package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.ResetPasswordRequest
import javax.inject.Inject

class ResetPasswordRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val resetPasswordResponseLiveData =
        SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun resetPassword(email : String, otp : String, password : String){
        resetPasswordResponseLiveData.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.resetPassword(ResetPasswordRequest(email, otp, password))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        resetPasswordResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        resetPasswordResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError: Response is null")
                    }
                }
                403 -> resetPasswordResponseLiveData.value = NetworkResult.Error(403, "Invalid OTP")
                406 -> resetPasswordResponseLiveData.value = NetworkResult.Error(406,"Invalid Action")
                408 -> resetPasswordResponseLiveData.value = NetworkResult.Error(408, "Invalid OTP Input")
                else -> resetPasswordResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            resetPasswordResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }
}