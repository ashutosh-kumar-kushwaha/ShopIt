package ashutosh.shopit.ui.auth.resetPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.repository.ResetPasswordRepository

class ResetPasswordViewModel(val email : String, val otp : String) : ViewModel() {
    private var resetPasswordRepository = ResetPasswordRepository()

    val resetPasswordResponseLiveData get() = resetPasswordRepository.resetPasswordResponseLiveData

    val errorMessage = SingleLiveEvent<String>()

    val password1LiveData = MutableLiveData("")
    val password2LiveData = MutableLiveData("")

    suspend fun resetPassword(){
        if(password1LiveData.value != null && password2LiveData.value != null){
            if(password1LiveData.value == password2LiveData.value){
                if(password1LiveData.value!!.length < 8){
                    if(password1LiveData.value!!.matches(Regex("^(?=.[a-z])(?=.[A-Z])(?=.\\\\d)(?=.[@\$!%?&])[A-Za-z\\\\d@\$!%?&]{8,}\$"))){
                        resetPasswordRepository.resetPassword(email, otp, password1LiveData.value!!)
                    }
                    else{
                        errorMessage.value = "Password must contain at least one uppercase letter, one lowercase letter, one numeric character, one special character and no spaces"
                    }
                }
                else{
                    errorMessage.value = "Password must contain at least 8 characters"
                }
            }
            else{
                errorMessage.value = "Enter same passwords in both fields"
            }
        }
        else{
            errorMessage.value = "Enter passwords in both fields"
        }
    }

}