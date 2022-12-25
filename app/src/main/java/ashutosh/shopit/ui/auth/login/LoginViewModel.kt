package ashutosh.shopit.ui.auth.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    val loginResponseLiveData : LiveData<NetworkResult<LoginResponse>> get() = loginRepository.loginResponseLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    val emailLiveData = MutableLiveData("")

    val passwordLiveData = MutableLiveData("")

    suspend fun login(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            if(passwordLiveData.value!!.matches(Regex("^(?=.[a-z])(?=.[A-Z])(?=.\\\\d)(?=.[@\$!%?&])[A-Za-z\\\\d@\$!%?&]{8,}\$"))){
                loginRepository.login(emailLiveData.value!!, passwordLiveData.value!!)
            }
            else{
                _errorMessage.value = "Wrong format of password"
            }
        }
        else{
            _errorMessage.value = "Invalid Email"
        }
    }

    suspend fun googleSignIn(token : String){
        loginRepository.signGoogle(token)
    }

}