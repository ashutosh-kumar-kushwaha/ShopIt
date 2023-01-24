package ashutosh.shopit.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val accountRepository: AccountRepository) : ViewModel() {

    val profileResponse get() = accountRepository.profileResponse

    fun getProfile(){
        viewModelScope.launch {
            accountRepository.getProfile()
        }
    }

}