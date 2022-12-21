package ashutosh.shopit.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.models.LoginUnsuccessfulResponse

class LoginRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    private val _loginResponseLiveData = MutableLiveData<NetworkResult<LoginResponse>>()
    val loginResponseLiveData : LiveData<NetworkResult<LoginResponse>> get() = _loginResponseLiveData

    suspend fun login(email : String, password : String){
        _loginResponseLiveData.postValue(NetworkResult.Loading())
        try{
            val response = retrofitAPI.login(LoginRequest(email, password))
            if(response.code() == 200 && response.body() != null){
                _loginResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            else if(response.code() == 404 && response.errorBody() != null){
                _loginResponseLiveData.postValue(NetworkResult.Error("Invalid Email"))
            }
            else if(response.code() == 400 && response.errorBody() != null){
                _loginResponseLiveData.postValue(NetworkResult.Error("Invalid Format of email or password"))
            }
            else if(response.code() == 401 && response.errorBody() != null){
                _loginResponseLiveData.postValue(NetworkResult.Error("Wrong Password"))
            }
            else{
                _loginResponseLiveData.postValue(NetworkResult.Error("Something went wrong!!"))
            }
        }
        catch(e : Exception){
            e.printStackTrace()
        }

    }
}