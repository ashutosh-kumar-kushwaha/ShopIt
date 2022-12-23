package ashutosh.shopit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.DefaultResponse

class GetStartedRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _signUpEmailResponseLiveData = MutableLiveData<NetworkResult<DefaultResponse>>()
    val signUpEmailResponseLiveData : LiveData<NetworkResult<DefaultResponse>> get() = _signUpEmailResponseLiveData

    suspend fun signUpEmail(email : String){
        _signUpEmailResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signUpEmail(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        _signUpEmailResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> _signUpEmailResponseLiveData.value = NetworkResult.Error("Email a valid email")
                409 -> _signUpEmailResponseLiveData.value = NetworkResult.Error("User Already Exist")
                503 -> _signUpEmailResponseLiveData.value = NetworkResult.Error("Unable to make your request")
                else -> _signUpEmailResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            _signUpEmailResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

}