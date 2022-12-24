package ashutosh.shopit.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ashutosh.shopit.R
import ashutosh.shopit.ui.auth.forgotPasswordOtpVerification.ForgotPasswordOtpVerificationFragment
import ashutosh.shopit.ui.auth.login.LoginFragment

class AuthenticationActivity : AppCompatActivity() {

    var email = ""
    var otp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()
//        ft.add(R.id.fragmentContainer, ForgotPasswordOtpVerificationFragment())
//        ft.commit()
    }
}