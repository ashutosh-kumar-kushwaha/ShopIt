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
            val checkName = !addAddressViewModel.name.value.isNullOrEmpty()
            val checkType = !addAddressViewModel.type.value.isNullOrEmpty()
            val checkMobile = addAddressViewModel.mobile.value!!.matches(Regex("^(\\+\\d{2}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"))
            val checkPinCode = addAddressViewModel.pinCode.value!!.matches(Regex("^[1-9][0-9]{5}$"))
            val checkLocality = !addAddressViewModel.locality.value.isNullOrEmpty()
            val checkAddressLine = !addAddressViewModel.addressLine.value.isNullOrEmpty()
            val checkCity = !addAddressViewModel.city.value.isNullOrEmpty()
            val checkState = !addAddressViewModel.state.value.isNullOrEmpty()
            val checkMobile2 = addAddressViewModel.mobile2.value!!.matches(Regex("^(\\+\\d{2}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$")) || addAddressViewModel.mobile2.value!!.isEmpty()

            if(!checkName){
                binding.nameETxt.error = "Enter a valid name"
            }
            if(!checkType){
                binding.typeETxt.error = "Type can't be empty"
            }
            if(!checkMobile){
                binding.mobileETxt.error = "Enter a valid mobile number"
            }
            if(!checkPinCode){
                binding.pinCodeETxt.error = "Enter a valid pin code"
            }
            if(!checkLocality){
                binding.localityETxt.error = "Locality can't be empty"
            }
            if(!checkAddressLine){
                binding.addressLineETxt.error = "Address line can't be empty"
            }
            if(!checkCity){
                binding.cityETxt.error = "City can't be empty"
            }
            if(!checkState){
                binding.stateETxt.error = "State can't be empty"
            }
            if(!checkMobile2){
                binding.mobileETxt2.error = "Enter a valid mobile number"
            }

            if(checkName && checkType && checkMobile && checkPinCode && checkLocality && checkAddressLine && checkCity && checkState && checkMobile2){
                addAddressViewModel.addAddress()
            }
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