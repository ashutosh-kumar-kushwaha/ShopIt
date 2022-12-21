package ashutosh.shopit.repository

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

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

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
                404 -> _loginResponseLiveData.postValue(NetworkResult.Error("Invalid Email"))

                400 -> _loginResponseLiveData.postValue(NetworkResult.Error("Invalid Format of email or password"))

                401 -> _loginResponseLiveData.postValue(NetworkResult.Error("Wrong Password"))

                else -> _loginResponseLiveData.postValue(NetworkResult.Error(response.code().toString()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}