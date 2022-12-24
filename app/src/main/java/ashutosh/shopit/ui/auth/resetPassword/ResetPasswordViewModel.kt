package ashutosh.shopit.ui.auth.resetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.ResetPasswordRepository

class ResetPasswordViewModel(val email : String, val otp : String) : ViewModel() {
    private var resetPasswordRepository = ResetPasswordRepository()

    val resetPasswordResponseLiveData get() = resetPasswordRepository.resetPasswordResponseLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    val password1LiveData = MutableLiveData("")
    val password2LiveData = MutableLiveData("")

    suspend fun resetPassword(){
        if(password1LiveData.value != null){
            resetPasswordRepository.resetPassword(email, otp, password1LiveData.value!!)
        }
    }

}