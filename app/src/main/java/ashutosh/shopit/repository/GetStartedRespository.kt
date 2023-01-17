package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.LoginResponse
import javax.inject.Inject

class GetStartedRepository @Inject constructor(private val retrofitAPI : RetrofitAPI) {

    val signUpEmailResponseLiveData =
        SingleLiveEvent<NetworkResult<DefaultResponse>>()

    val loginResponseLiveData =
        SingleLiveEvent<NetworkResult<LoginResponse>>()

    suspend fun signUpEmail(email : String){
        signUpEmailResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signUpEmail(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        signUpEmailResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> signUpEmailResponseLiveData.value = NetworkResult.Error("Email a valid email")
                409 -> signUpEmailResponseLiveData.value = NetworkResult.Error("User Already Exist")
                503 -> signUpEmailResponseLiveData.value = NetworkResult.Error("Unable to make your request")
                else -> signUpEmailResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            signUpEmailResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun signGoogle(token : String){
        loginResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signGoogle(token)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        loginResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> loginResponseLiveData.value = NetworkResult.Error("Invalid token")
                403 -> loginResponseLiveData.value = NetworkResult.Error("Either the token is expired or the token is not authorized")
                else -> loginResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e: Exception){
            loginResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

}