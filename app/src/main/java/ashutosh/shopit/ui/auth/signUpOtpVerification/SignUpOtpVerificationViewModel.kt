package ashutosh.shopit.ui.auth.signUpOtpVerification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.SignUpOtpVerificationRepository

class SignUpOtpVerificationViewModel(var email: String) : ViewModel() {
    private val signUpOtpVerificationRepository = SignUpOtpVerificationRepository()

    val responseLiveData get() = signUpOtpVerificationRepository.responseLiveData

    private val _errorMessage = MutableLiveData("")
    val errorMessage : LiveData<String> get() = _errorMessage

    val otp1LiveData = MutableLiveData("")
    val otp2LiveData = MutableLiveData("")
    val otp3LiveData = MutableLiveData("")
    val otp4LiveData = MutableLiveData("")
    val otp5LiveData = MutableLiveData("")
    val otp6LiveData = MutableLiveData("")

    var otp : String = ""

    suspend fun verifySignUpOtp(){
        if(otp1LiveData.value.isNullOrEmpty() || otp2LiveData.value.isNullOrEmpty() || otp3LiveData.value.isNullOrEmpty() || otp4LiveData.value.isNullOrEmpty() || otp5LiveData.value.isNullOrEmpty() || otp6LiveData.value.isNullOrEmpty()){
            _errorMessage.value = "Enter a valid OTP"
        }
        else{
            otp = "${otp1LiveData.value}${otp2LiveData.value}${otp3LiveData.value}${otp4LiveData.value}${otp5LiveData.value}${otp6LiveData.value}"
            signUpOtpVerificationRepository.verifySignUpOtp(email, otp)
        }
    }
}