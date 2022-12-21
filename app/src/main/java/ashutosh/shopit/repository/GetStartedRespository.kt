package ashutosh.shopit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.SignUpResponse

class GetStartedRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _signUpEmailResponseLiveData = MutableLiveData<NetworkResult<SignUpResponse>>()
    val signUpEmailResponseLiveData : LiveData<NetworkResult<SignUpResponse>> get() = _signUpEmailResponseLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> get()= _errorMessage

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
                else -> _signUpEmailResponseLiveData.value = NetworkResult.Error(response.code().toString())
            }
        }
        catch (e : Exception){
            _errorMessage.value = e.message
        }
    }

}