package ashutosh.shopit.ui.auth.getStarted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ashutosh.shopit.repository.GetStartedRepository

class GetStartedViewModel : ViewModel() {

    private val getStartedRepository = GetStartedRepository()

    val signUpEmailResponseLiveData get() = getStartedRepository.signUpEmailResponseLiveData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage : LiveData<String> get()= _errorMessage

    val emailLiveData = MutableLiveData("")

    suspend fun signUpEmail(){
        if(emailLiveData.value!=null){
            getStartedRepository.signUpEmail(emailLiveData.value!!)
        }
    }

}