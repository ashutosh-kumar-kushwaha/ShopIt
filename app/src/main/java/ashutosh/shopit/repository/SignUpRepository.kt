package ashutosh.shopit.repository

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

    }

}