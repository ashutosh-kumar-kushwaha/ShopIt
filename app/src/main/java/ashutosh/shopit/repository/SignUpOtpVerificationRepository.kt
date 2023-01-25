package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
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
                        responseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        responseLiveData.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                406 -> responseLiveData.value = NetworkResult.Error(406, "Wrong OTP")
                403 -> responseLiveData.value = NetworkResult.Error(403, "Session Time-out\nPlease Try Again")
                else -> responseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            responseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun resendOtp(email : String){
        resendOtpResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.signUpEmail(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        resendOtpResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        resendOtpResponseLiveData.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                400 -> resendOtpResponseLiveData.value = NetworkResult.Error(400, "Email a valid email")
                409 -> resendOtpResponseLiveData.value = NetworkResult.Error(409, "User Already Exist")
                503 -> resendOtpResponseLiveData.value = NetworkResult.Error(503, "Unable to make your request")
                else -> resendOtpResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            resendOtpResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }
}