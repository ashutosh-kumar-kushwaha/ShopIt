package ashutosh.shopit.ui.account

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.RecommendationAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentAccountBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.datastore.DataStoreManager
import ashutosh.shopit.models.Recommendation
import ashutosh.shopit.ui.auth.AuthenticationActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding : FragmentAccountBinding? = null
    private val binding : FragmentAccountBinding get() = _binding!!

    private val accountViewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.viewModel = accountViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            val dataStoreManager = DataStoreManager(requireContext())
            dataStoreManager.getLogInInfo().collect{
                val name = "${it.firstName}!"
                binding.nameTxtVw.text = name
                binding.nameTxtVw2.text = it.firstName
                binding.emailTxtVw.text = it.email
            }
        }

//        val adapter = RecommendationAdapter()
//        binding.recommendationRecyclerView.adapter = adapter
//        adapter.submitList(listOf(Recommendation(2, "SmartPhones and devices", R.drawable.iphone), Recommendation(3, "SmartPhones and devices", R.drawable.iphone), Recommendation(4, "SmartPhones and devices", R.drawable.iphone), Recommendation(5, "SmartPhones and devices", R.drawable.iphone), Recommendation(1, "SmartPhones and devices", R.drawable.iphone)))
//        binding.recommendationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.logOutBtn.setOnClickListener {
            logOut()
        }

        binding.profileTxtVw.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_profileFragment)
        }

        accountViewModel.getProfile()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountViewModel.profileResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.profilePicShimmer.stopShimmerAnimation()
                    binding.profilePicCardVw.visibility = View.VISIBLE
                    binding.profilePicCardVwShimmer.visibility = View.GONE
                    binding.profilePicImgVw.load(it.data?.profilePhoto)
                }
                is NetworkResult.Error -> {
                    binding.profilePicShimmer.stopShimmerAnimation()
                    binding.profilePicCardVw.visibility = View.VISIBLE
                    binding.profilePicCardVwShimmer.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.profilePicCardVw.visibility = View.GONE
                    binding.profilePicCardVwShimmer.visibility = View.VISIBLE
                    binding.profilePicShimmer.startShimmerAnimation()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}