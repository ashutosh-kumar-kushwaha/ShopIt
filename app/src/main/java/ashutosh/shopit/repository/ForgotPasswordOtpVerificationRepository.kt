package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.DefaultResponse
import ashutosh.shopit.models.Email
import ashutosh.shopit.models.VerifyOtpRequest
import javax.inject.Inject

class ForgotPasswordOtpVerificationRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val otpVerifyResponseLiveData = SingleLiveEvent<NetworkResult<DefaultResponse>>()

    val resendOtpResponseLiveData = SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun verifyForgetPasswordOtp(email : String, otp : String){
        otpVerifyResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.verifyForgotPasswordOtp(VerifyOtpRequest(email, otp))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        otpVerifyResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        otpVerifyResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError: Response is null")
                    }
                }
                400 -> otpVerifyResponseLiveData.value = NetworkResult.Error(400, "Invalid Action")
                406 -> otpVerifyResponseLiveData.value = NetworkResult.Error(406, "Wrong OTP")
                408 -> otpVerifyResponseLiveData.value = NetworkResult.Error(408, "Session Time-out\nPlease Try Again")
                else -> otpVerifyResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            otpVerifyResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

    suspend fun resendOtp(email : String){
        resendOtpResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.forgotPassword(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        resendOtpResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        resendOtpResponseLiveData.value = NetworkResult.Error(200, "Something went wrong\nError : response is null")
                    }
                }
                400 -> resendOtpResponseLiveData.value = NetworkResult.Error(400, "Invalid Action")
                404 -> resendOtpResponseLiveData.value = NetworkResult.Error(404, "No user with this email is found")
                else -> resendOtpResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e : Exception){
            resendOtpResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }
}