package ashutosh.shopit.ui.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import ashutosh.shopit.R
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentAddAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressFragment : Fragment() {

    private var _binding: FragmentAddAddressBinding? = null
    private val binding: FragmentAddAddressBinding get() = _binding!!

    private val addAddressViewModel by viewModels<AddAddressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = addAddressViewModel

        binding.addAddressBtn.setOnClickListener {
            addAddressViewModel.addAddress()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addAddressViewModel.addAddressResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                is NetworkResult.Error -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                is NetworkResult.Loading -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}