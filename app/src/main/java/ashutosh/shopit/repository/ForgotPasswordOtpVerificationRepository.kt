package ashutosh.shopit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.VerifyOtpRequest

class ForgotPasswordOtpVerificationRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _responseLiveData = MutableLiveData<NetworkResult<DefaultResponse>>()
    val responseLiveData : LiveData<NetworkResult<DefaultResponse>> get() = _responseLiveData

    suspend fun verifyForgetPasswordOtp(email : String, otp : String){
        _responseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.verifyForgotPasswordOtp(VerifyOtpRequest(email, otp))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        _responseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> _responseLiveData.value = NetworkResult.Error("Invalid Action")
                406 -> _responseLiveData.value = NetworkResult.Error("Wrong OTP")
                408 -> _responseLiveData.value = NetworkResult.Error("Session Time-out\nPlease Try Again")
                else -> _responseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            _responseLiveData.value = NetworkResult.Error(e.message)
        }
    }
}