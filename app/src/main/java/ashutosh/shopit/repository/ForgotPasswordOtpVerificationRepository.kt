package ashutosh.shopit.repository

import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.VerifyOtpRequest

class ForgotPasswordOtpVerificationRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    val responseLiveData = MutableLiveData<NetworkResult<DefaultResponse>>()

    suspend fun verifyForgetPasswordOtp(email : String, otp : String){
        responseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.verifyForgotPasswordOtp(VerifyOtpRequest(email, otp))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        responseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> responseLiveData.value = NetworkResult.Error("Invalid Action")
                406 -> responseLiveData.value = NetworkResult.Error("Wrong OTP")
                408 -> responseLiveData.value = NetworkResult.Error("Session Time-out\nPlease Try Again")
                else -> responseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            responseLiveData.value = NetworkResult.Error(e.message)
        }
    }
}