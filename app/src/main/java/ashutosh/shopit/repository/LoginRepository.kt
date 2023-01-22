package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val loginResponseLiveData =
        SingleLiveEvent<NetworkResult<LoginResponse>>()

    suspend fun login(email: String, password: String) {
        loginResponseLiveData.postValue(NetworkResult.Loading())
        try {
            val response = retrofitAPI.login(LoginRequest(email, password))
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        loginResponseLiveData.postValue(NetworkResult.Success(200, response.body()!!))
                    }
                    else{
                        loginResponseLiveData.postValue(NetworkResult.Error(200, "Something went wrong\nError: Response is null"))
                    }
                }
                404 -> loginResponseLiveData.postValue(NetworkResult.Error(404, "User does not exist"))

                400 -> loginResponseLiveData.postValue(NetworkResult.Error(400, "Invalid Format of email or password"))

                401 -> loginResponseLiveData.postValue(NetworkResult.Error(401, "Wrong Password"))

                else -> loginResponseLiveData.postValue(NetworkResult.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            loginResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun signGoogle(token : String){
        loginResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signGoogle(token)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        loginResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        loginResponseLiveData.value = NetworkResult.Error(200, "Something went wrong\nError : Response is null")
                    }
                }
                400 -> loginResponseLiveData.value = NetworkResult.Error(400, "Invalid token")
                403 -> loginResponseLiveData.value = NetworkResult.Error(403, "Either the token is expired or the token is not authorized")
                else -> loginResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e: Exception){
            loginResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }
}