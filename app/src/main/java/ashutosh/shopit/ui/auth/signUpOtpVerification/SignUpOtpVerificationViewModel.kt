package ashutosh.shopit.ui.auth.signUpOtpVerification

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.repository.SignUpOtpVerificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SignUpOtpVerificationViewModel @Inject constructor(private val signUpOtpVerificationRepository: SignUpOtpVerificationRepository) : ViewModel() {

    val responseLiveData get() = signUpOtpVerificationRepository.responseLiveData
    val resendOtpResponseLiveData get() = signUpOtpVerificationRepository.resendOtpResponseLiveData

    var email = ""

    val errorMessage = SingleLiveEvent<String>()

    val otp1LiveData = MutableLiveData("")
    val otp2LiveData = MutableLiveData("")
    val otp3LiveData = MutableLiveData("")
    val otp4LiveData = MutableLiveData("")
    val otp5LiveData = MutableLiveData("")
    val otp6LiveData = MutableLiveData("")

    var otp : String = ""

    val timeLiveData = MutableLiveData("60")

    val canResend = MutableLiveData(false)

    val timer = object : CountDownTimer(60000, 1000){
        override fun onTick(millisUntilFinished: Long) {
            val min = millisUntilFinished/60000
            val sec = (millisUntilFinished/1000)%60
            timeLiveData.value = if(sec < 10){
                "0${min}:0${sec}"
            }
            else{
                "0${min}:${sec}"
            }
        }

        override fun onFinish() {
            canResend.value = true
        }
    }

    suspend fun verifySignUpOtp(){
        if(otp1LiveData.value.isNullOrEmpty() || otp2LiveData.value.isNullOrEmpty() || otp3LiveData.value.isNullOrEmpty() || otp4LiveData.value.isNullOrEmpty() || otp5LiveData.value.isNullOrEmpty() || otp6LiveData.value.isNullOrEmpty()){
            errorMessage.value = "Enter a valid OTP"
        }
        else{
            otp = "${otp1LiveData.value}${otp2LiveData.value}${otp3LiveData.value}${otp4LiveData.value}${otp5LiveData.value}${otp6LiveData.value}"
            signUpOtpVerificationRepository.verifySignUpOtp(email, otp)
        }
    }

    suspend fun resendOtp(){
        if(canResend.value == true){
            signUpOtpVerificationRepository.resendOtp(email)
            timer.start()
        }
    }
}