package ashutosh.shopit.repository

import android.util.Log
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.LoginRequest
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.models.SignUpRequest

class SignUpRepository {
    private val retrofitAPI = ServiceBuilder.buildService(RetrofitAPI::class.java)

    val signUpResponseLiveData = SingleLiveEvent<NetworkResult<LoginResponse>>()

    suspend fun signUp(email : String, otp : String, firstName : String, lastName : String, gender : String, password : String){
        signUpResponseLiveData.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.signUp(SignUpRequest(email, otp, firstName, lastName, gender, password))
            Log.d("Response", response.code().toString())
            when(response.code()){
                200 -> {
                    login(email, password)
                }
                401 -> signUpResponseLiveData.value = NetworkResult.Error("Invalid OTP")
                408 -> signUpResponseLiveData.value = NetworkResult.Error("Session Time-out")
                503 -> signUpResponseLiveData.value = NetworkResult.Error("Invalid Action")
                else -> signUpResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code ${response.code()}")
            }
        }
        catch (e : Exception){
            signUpResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun login(email: String, password: String) {
        try {
            val response = retrofitAPI.login(LoginRequest(email, password))
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        signUpResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
                    }
                }
                404 -> signUpResponseLiveData.postValue(NetworkResult.Error("User does not exist"))

                400 -> signUpResponseLiveData.postValue(NetworkResult.Error("Invalid Format of email or password"))

                401 -> signUpResponseLiveData.postValue(NetworkResult.Error("Wrong Password"))

                else -> signUpResponseLiveData.postValue(NetworkResult.Error("Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            signUpResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

}