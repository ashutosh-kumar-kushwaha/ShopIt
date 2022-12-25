package ashutosh.shopit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse

class LoginRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _loginResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResponseLiveData: LiveData<NetworkResult<LoginResponse>> get() = _loginResponseLiveData

    suspend fun login(email: String, password: String) {
        _loginResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val response = retrofitAPI.login(LoginRequest(email, password))
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        _loginResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
                    }
                }
                404 -> _loginResponseLiveData.postValue(NetworkResult.Error("User does not exist"))

                400 -> _loginResponseLiveData.postValue(NetworkResult.Error("Invalid Format of email or password"))

                401 -> _loginResponseLiveData.postValue(NetworkResult.Error("Wrong Password"))

                else -> _loginResponseLiveData.postValue(NetworkResult.Error("Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            _loginResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun signGoogle(token : String){
        _loginResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signGoogle(token)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        _loginResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> _loginResponseLiveData.value = NetworkResult.Error("Invalid token")
                403 -> _loginResponseLiveData.value = NetworkResult.Error("Either the token is expired or the token is not authorized")
                else -> _loginResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e: Exception){
            _loginResponseLiveData.value = NetworkResult.Error(e.message)s
        }
    }
}