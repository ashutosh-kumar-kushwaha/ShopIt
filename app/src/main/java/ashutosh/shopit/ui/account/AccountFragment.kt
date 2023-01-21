package ashutosh.shopit.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.RecommendationAdapter
import ashutosh.shopit.databinding.FragmentAccountBinding
import ashutosh.shopit.datastore.DataStoreManager
import ashutosh.shopit.models.Recommendation
import ashutosh.shopit.ui.auth.AuthenticationActivity
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private var _binding : FragmentAccountBinding? = null
    private val binding : FragmentAccountBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        val adapter = RecommendationAdapter()
        binding.recommendationRecyclerView.adapter = adapter
        adapter.submitList(listOf(Recommendation(2, "SmartPhones and devices", R.drawable.iphone), Recommendation(3, "SmartPhones and devices", R.drawable.iphone), Recommendation(4, "SmartPhones and devices", R.drawable.iphone), Recommendation(5, "SmartPhones and devices", R.drawable.iphone), Recommendation(1, "SmartPhones and devices", R.drawable.iphone)))
        binding.recommendationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.logOutBtn.setOnClickListener {
            logOut()
        }


        return binding.root
    }

    private fun logOut(){
        lifecycleScope.launch {
            val dataStoreManager = DataStoreManager(requireContext())
            dataStoreManager.deleteLogInInfo()
        }
        val intent = Intent(requireContext(), AuthenticationActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}