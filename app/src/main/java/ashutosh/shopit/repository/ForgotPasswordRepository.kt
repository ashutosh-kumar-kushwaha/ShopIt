package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.DefaultResponse
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(private val retrofitAPI: RetrofitAPI)  {

    val forgotPasswordResponseLiveData =
        SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun forgotPassword(email : String){
        forgotPasswordResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.forgotPassword(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        forgotPasswordResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> forgotPasswordResponseLiveData.value = NetworkResult.Error("Invalid Action")
                404 -> forgotPasswordResponseLiveData.value = NetworkResult.Error("No user with this email is found")
                else -> forgotPasswordResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e : Exception){
            forgotPasswordResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }


}