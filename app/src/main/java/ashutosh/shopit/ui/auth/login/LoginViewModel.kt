package ashutosh.shopit.ui.auth.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.SingleLiveEvent
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

     val loginResponseLiveData : SingleLiveEvent<NetworkResult<LoginResponse>> get() = loginRepository.loginResponseLiveData

     val errorMessage = SingleLiveEvent<String>()

    val emailLiveData = MutableLiveData("")

    val passwordLiveData = MutableLiveData("")

    suspend fun login(){
        if(emailLiveData.value != null && Patterns.EMAIL_ADDRESS.matcher(emailLiveData.value.toString()).matches()){
            if(passwordLiveData.value!!.matches(Regex("^(?=.[a-z])(?=.[A-Z])(?=.\\\\d)(?=.[@\$!%?&])[A-Za-z\\\\d@\$!%?&]{8,}\$"))){
                loginRepository.login(emailLiveData.value!!, passwordLiveData.value!!)
            }
            else{
                errorMessage.value = "Wrong format of password"
            }
        }
        else{
            errorMessage.value = "Invalid Email"
        }
    }

    suspend fun googleSignIn(token : String){
        loginRepository.signGoogle(token)
    }

}