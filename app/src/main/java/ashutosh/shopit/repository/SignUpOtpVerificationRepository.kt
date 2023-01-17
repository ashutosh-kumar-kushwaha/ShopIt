package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.VerifyOtpRequest
import javax.inject.Inject

class SignUpOtpVerificationRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val responseLiveData =
        SingleLiveEvent<NetworkResult<DefaultResponse>>()

    val resendOtpResponseLiveData = SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun verifySignUpOtp(email : String, otp : String){
        responseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.verifySignUpOtp(VerifyOtpRequest(email, otp))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        responseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                406 -> responseLiveData.value = NetworkResult.Error("Wrong OTP")
                403 -> responseLiveData.value = NetworkResult.Error("Session Time-out\nPlease Try Again")
                else -> responseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            responseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun resendOtp(email : String){
        resendOtpResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signUpEmail(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        resendOtpResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> resendOtpResponseLiveData.value = NetworkResult.Error("Email a valid email")
                409 -> resendOtpResponseLiveData.value = NetworkResult.Error("User Already Exist")
                503 -> resendOtpResponseLiveData.value = NetworkResult.Error("Unable to make your request")
                else -> resendOtpResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            resendOtpResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }
}