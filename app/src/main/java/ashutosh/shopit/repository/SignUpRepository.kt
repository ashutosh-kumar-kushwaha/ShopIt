package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.models.SignUpRequest
import javax.inject.Inject

class SignUpRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val signUpResponseLiveData = SingleLiveEvent<NetworkResult<LoginResponse>>()

    suspend fun signUp(email : String, otp : String, firstName : String, lastName : String, gender : String, password : String){
        signUpResponseLiveData.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.signUp(SignUpRequest(email, otp, firstName, lastName, gender, password))
            when(response.code()){
                200 -> {
                    login(email, password)
                }
                401 -> signUpResponseLiveData.value = NetworkResult.Error(401, "Invalid OTP")
                408 -> signUpResponseLiveData.value = NetworkResult.Error(408, "Session Time-out")
                503 -> signUpResponseLiveData.value = NetworkResult.Error(503, "Invalid Action")
                else -> signUpResponseLiveData.value = NetworkResult.Error(response.code(),"Something went wrong\nError code ${response.code()}")
            }
        }
        catch (e : Exception){
            signUpResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            val response = retrofitAPI.login(LoginRequest(email, password))
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        signUpResponseLiveData.postValue(NetworkResult.Success(200, response.body()!!))
                    }
                    else{
                        signUpResponseLiveData.postValue(NetworkResult.Error(response.code(),"Something went wrong\nError : Response is null"))
                    }
                }
                404 -> signUpResponseLiveData.postValue(NetworkResult.Error(404, "User does not exist"))

                400 -> signUpResponseLiveData.postValue(NetworkResult.Error(400, "Invalid Format of email or password"))

                401 -> signUpResponseLiveData.postValue(NetworkResult.Error(401, "Wrong Password"))

                else -> signUpResponseLiveData.postValue(NetworkResult.Error(response.code(),"Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            signUpResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

}