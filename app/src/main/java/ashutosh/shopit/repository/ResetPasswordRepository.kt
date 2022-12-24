package ashutosh.shopit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.ResetPasswordRequest

class ResetPasswordRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)
    private val _resetPasswordResponseLiveData = MutableLiveData<NetworkResult<DefaultResponse>>()
    val resetPasswordResponseLiveData : LiveData<NetworkResult<DefaultResponse>> get() = _resetPasswordResponseLiveData

    suspend fun resetPassword(email : String, otp : String, password : String){
        _resetPasswordResponseLiveData.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.resetPassword(ResetPasswordRequest(email, otp, password))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        _resetPasswordResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                403 -> _resetPasswordResponseLiveData.value = NetworkResult.Error("Invalid OTP")
                406 -> _resetPasswordResponseLiveData.value = NetworkResult.Error("Invalid Action")
                408 -> _resetPasswordResponseLiveData.value = NetworkResult.Error("Invalid OTP Input")
                else -> _resetPasswordResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            _resetPasswordResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }
}