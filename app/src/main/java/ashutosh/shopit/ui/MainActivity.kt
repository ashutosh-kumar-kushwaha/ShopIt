package ashutosh.shopit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import ashutosh.shopit.R
import ashutosh.shopit.datastore.DataStoreManager
import ashutosh.shopit.models.LogInInfo
import ashutosh.shopit.ui.auth.AuthenticationActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        findViewById<Button>(R.id.signOutBtn).setOnClickListener {
            lifecycleScope.launch {
                val dataStoreManager = DataStoreManager(this@MainActivity)
                dataStoreManager.storeLogInInfo(LogInInfo("", "", false, "", "", ""))
                val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}