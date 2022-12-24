package ashutosh.shopit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.SignUpRequest

class SignUpRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _signUpResponseLiveData = MutableLiveData<NetworkResult<DefaultResponse>>()
    val signUpResponseLiveData : LiveData<NetworkResult<DefaultResponse>> get() = _signUpResponseLiveData

    suspend fun signUp(email : String, otp : String, firstName : String, lastName : String, gender : String, password : String){
        _signUpResponseLiveData.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.signUp(SignUpRequest(email, otp, firstName, lastName, gender, password))
            Log.d("Response", response.code().toString())
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        _signUpResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                401 -> _signUpResponseLiveData.value = NetworkResult.Error("Invalid OTP")
                408 -> _signUpResponseLiveData.value = NetworkResult.Error("Session Time-out")
                503 -> _signUpResponseLiveData.value = NetworkResult.Error("Invalid Action")
                else -> _signUpResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code ${response.code()}")
            }
        }
        catch (e : Exception){
            _signUpResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

}