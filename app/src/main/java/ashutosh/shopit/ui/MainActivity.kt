package ashutosh.shopit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ashutosh.shopit.R
import ashutosh.shopit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        
//        findViewById<Button>(R.id.signOutBtn).setOnClickListener {
//            lifecycleScope.launch {
//                val dataStoreManager = DataStoreManager(this@MainActivity)
//                dataStoreManager.storeLogInInfo(LogInInfo("", "", false, "", "", ""))
//                val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationBar.setupWithNavController(navController)



    }
}