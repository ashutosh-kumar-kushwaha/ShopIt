package ashutosh.shopit.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.models.LoginResponse
import ashutosh.shopit.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    val loginResponseLiveData : LiveData<NetworkResult<LoginResponse>> get() = loginRepository.loginResponseLiveData

    val emailLiveData = MutableLiveData("")

    val passwordLiveData = MutableLiveData("")

    suspend fun login(){
        loginRepository.login(emailLiveData.value!!, passwordLiveData.value!!)
    }

}