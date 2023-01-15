package ashutosh.shopit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ashutosh.shopit.R
import ashutosh.shopit.databinding.ActivityMainBinding
import ashutosh.shopit.ui.product.ProductFragment

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationBar.setupWithNavController(navController)

    }
}