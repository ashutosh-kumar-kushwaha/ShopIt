package ashutosh.shopit.ui.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.SignUpRepository

class SignUpViewModel(val email : String, val otp : String) : ViewModel() {
    private val signUpRepository = SignUpRepository()
    var gender : String? = null
    val firstNameLiveData = MutableLiveData("")
    val lastNameLiveData = MutableLiveData("")
    val password1LiveData = MutableLiveData("")
    val password2LiveData = MutableLiveData("")

    private val _errorMessage = MutableLiveData("")
    val errorMessage : LiveData<String> get() = _errorMessage

    val signUpResponseLiveData get() = signUpRepository.signUpResponseLiveData

    suspend fun signUp(){
        if(gender != null){
            if(firstNameLiveData.value.isNullOrEmpty()){
                if(password1LiveData.value != null && password2LiveData.value != null){
                    if(password1LiveData.value == password2LiveData.value){
                        if(password1LiveData.value!!.length < 8){
                            if(password1LiveData.value!!.matches(Regex("^(?=.[a-z])(?=.[A-Z])(?=.\\\\d)(?=.[@\$!%?&])[A-Za-z\\\\d@\$!%?&]{8,}\$"))){
                                signUpRepository.signUp(email, otp, firstNameLiveData.value!!, lastNameLiveData.value!!, gender!!, password1LiveData.value!!)
                            }
                            else{
                                _errorMessage.value = "Password must contain at least one uppercase letter, one lowercase letter, one numeric character, one special character and no spaces"
                            }
                        }
                        else{
                            _errorMessage.value = "Password must contain at least 8 characters"
                        }
                    }
                    else{
                        _errorMessage.value = "Enter same passwords in both fields"
                    }
                }
                else{
                    _errorMessage.value = "Enter passwords in both fields"
                }
            }
            else{
                _errorMessage.value = "Enter a first name"
            }
        }
        else{
            _errorMessage.value = "Select a gender"
        }
    }
}