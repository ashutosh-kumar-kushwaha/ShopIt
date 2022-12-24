package ashutosh.shopit.ui.auth.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResetPasswordViewModelFactory(val email : String, val otp : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(email, otp) as T
    }
}