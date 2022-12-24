package ashutosh.shopit.ui.auth.forgotPasswordOtpVerification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.ForgotPasswordOtpVerificationRepository

class ForgotPasswordOtpVerificationViewModel(val email : String) : ViewModel() {

    private val forgotPasswordOtpVerificationRepository = ForgotPasswordOtpVerificationRepository()

    val responseLiveData get() = forgotPasswordOtpVerificationRepository.responseLiveData

    private val _errorMessage = MutableLiveData("")
    val errorMessage : LiveData<String> get() = _errorMessage

    val otp1LiveData = MutableLiveData("")
    val otp2LiveData = MutableLiveData("")
    val otp3LiveData = MutableLiveData("")
    val otp4LiveData = MutableLiveData("")
    val otp5LiveData = MutableLiveData("")
    val otp6LiveData = MutableLiveData("")

    var otp : String = ""

    suspend fun verifyForgotPasswordOtp(){
        otp = "${otp1LiveData.value}${otp2LiveData.value}${otp3LiveData.value}${otp4LiveData.value}${otp5LiveData.value}${otp6LiveData.value}"
        forgotPasswordOtpVerificationRepository.verifyForgetPasswordOtp(email, otp)
    }



}