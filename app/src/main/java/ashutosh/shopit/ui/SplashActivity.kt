package ashutosh.shopit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import ashutosh.shopit.R
import ashutosh.shopit.datastore.DataStoreManager
import ashutosh.shopit.ui.auth.AuthenticationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lateinit var intent : Intent
//        lifecycleScope.launch {
//            intent = getNextActivity()
//        }

        lifecycleScope.launch(Dispatchers.IO) {
            val dataStoreManager = DataStoreManager(this@SplashActivity)
            dataStoreManager.getLogInInfo().collect{
                intent = if(it.logInState){
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else{
                    Intent(this@SplashActivity, AuthenticationActivity::class.java)
                }
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }

//    private suspend fun getNextActivity() : Intent{
//        val intent = lifecycleScope.async {
//            var i = Intent()
//            val dataStoreManager = DataStoreManager(this@SplashActivity)
//            dataStoreManager.getLogInInfo().collect{
//                i = if(it.logInState){
//                    Intent(this@SplashActivity, MainActivity::class.java)
//                } else{
//                    Intent(this@SplashActivity, AuthenticationActivity::class.java)
//                }
//            }
//            i
//        }
//        return intent.await()
//    }
}