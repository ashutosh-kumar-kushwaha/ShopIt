package ashutosh.shopit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.SignUpResponse

class ForgotPasswordRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _forgotPasswordResponseLiveData = MutableLiveData<NetworkResult<SignUpResponse>>()
    val forgotPasswordResponseLiveData : LiveData<NetworkResult<SignUpResponse>> get() = _forgotPasswordResponseLiveData

    suspend fun forgotPassword(email : String){
        _forgotPasswordResponseLiveData.value = NetworkResult.Loading()
        val response = retrofitAPI.forgotPassword(Email(email))
        try{
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        _forgotPasswordResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                404 -> _forgotPasswordResponseLiveData.value = NetworkResult.Error("No user with this email is found")

                else -> _forgotPasswordResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code : "+ response.code().toString())
            }
        }
        catch (e : Exception){
            _forgotPasswordResponseLiveData.value = NetworkResult.Error(e.message)
        }

    }
}