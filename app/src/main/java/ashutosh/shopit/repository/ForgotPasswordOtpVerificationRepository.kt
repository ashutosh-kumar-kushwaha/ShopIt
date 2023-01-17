package ashutosh.shopit.repository

import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.api.RetrofitAPI
import ashutosh.shopit.api.ServiceBuilder
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
                        otpVerifyResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> otpVerifyResponseLiveData.value = NetworkResult.Error("Invalid Action")
                406 -> otpVerifyResponseLiveData.value = NetworkResult.Error("Wrong OTP")
                408 -> otpVerifyResponseLiveData.value = NetworkResult.Error("Session Time-out\nPlease Try Again")
                else -> otpVerifyResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code: ${response.code()}")
            }
        }
        catch (e : Exception){
            otpVerifyResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }

    suspend fun resendOtp(email : String){
        resendOtpResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.forgotPassword(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        resendOtpResponseLiveData.value = NetworkResult.Success(response.body()!!)
                    }
                }
                400 -> resendOtpResponseLiveData.value = NetworkResult.Error("Invalid Action")
                404 -> resendOtpResponseLiveData.value = NetworkResult.Error("No user with this email is found")
                else -> resendOtpResponseLiveData.value = NetworkResult.Error("Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e : Exception){
            resendOtpResponseLiveData.value = NetworkResult.Error(e.message)
        }
    }
}