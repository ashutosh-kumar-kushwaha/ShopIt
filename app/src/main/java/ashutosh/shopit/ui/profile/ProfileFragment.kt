package ashutosh.shopit.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.AddressOrderAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.DialogEditDetailsBinding
import ashutosh.shopit.databinding.DialogUpdateEmailBinding
import ashutosh.shopit.databinding.FragmentProfileBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.interfaces.AddressClickListener
import ashutosh.shopit.models.UpdateProfileRequest
import coil.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private var _editDetailsBinding : DialogEditDetailsBinding? = null
    private val editDetailsBinding : DialogEditDetailsBinding get() = _editDetailsBinding!!
    private lateinit var editDetailsDialog : Dialog

    private var _updateEmailBinding : DialogUpdateEmailBinding? = null
    private val updateEmailBinding : DialogUpdateEmailBinding get() = _updateEmailBinding!!
    private lateinit var updateEmailDialog : Dialog

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val profileViewModel by viewModels<ProfileViewModel>()

    private lateinit var addressOrderAdapter : AddressOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        val addressClickListener = object : AddressClickListener {
            override fun onAddressClick(addressId: Int) {
                binding.addressRecyclerView.post(addressOrderAdapter::notifyDataSetChanged)
            }
        }

        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        addressOrderAdapter = AddressOrderAdapter(addressClickListener)
        binding.addressRecyclerView.adapter = addressOrderAdapter

        binding.editDetailsBtn.setOnClickListener {
            showEditDetailsDialog()
        }

        profileViewModel.getProfile()
        profileViewModel.getAddresses()

        binding.addAddressBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addAddressFragment)
        }

        binding.updateEmailBtn.setOnClickListener {
            if(profileViewModel.email.value!! != profileViewModel.originalEmail){
                profileViewModel.updateEmail()
            }
            else{
                Toast.makeText(requireContext(), "This is already your current email", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun showUpdateEmailDialog(){
        val displayRectangle = Rect()
        requireActivity().window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        _updateEmailBinding = DialogUpdateEmailBinding.inflate(layoutInflater)
        updateEmailDialog = Dialog(updateEmailBinding.root.context)
        updateEmailDialog.setCanceledOnTouchOutside(false)
        updateEmailDialog.setContentView(updateEmailBinding.root)
        updateEmailDialog.show()
        val otpSentTxt = "Enter OTP sent to ${profileViewModel.email}"
        updateEmailBinding.otpSentTxtVw.text = otpSentTxt
        updateEmailBinding.continueBtn.setOnClickListener {
            submitEmailOtp()
        }
    }

    private fun submitEmailOtp(){
        val otp1 = updateEmailBinding.otpETxt1.text
        val otp2 = updateEmailBinding.otpETxt2.text
        val otp3 = updateEmailBinding.otpETxt3.text
        val otp4 = updateEmailBinding.otpETxt4.text
        val otp5 = updateEmailBinding.otpETxt5.text
        val otp6 = updateEmailBinding.otpETxt6.text
        if(otp1.isNullOrEmpty() || otp2.isNullOrEmpty() || otp3.isNullOrEmpty() || otp4.isNullOrEmpty() || otp5.isNullOrEmpty() || otp6.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Enter a valid otp", Toast.LENGTH_SHORT).show()
        }
        else{

        }
    }

    private fun showEditDetailsDialog(){
        val displayRectangle = Rect()
        requireActivity().window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        _editDetailsBinding = DialogEditDetailsBinding.inflate(layoutInflater)
        editDetailsDialog = Dialog(editDetailsBinding.root.context)
        editDetailsBinding.root.minimumWidth = displayRectangle.width()
        editDetailsDialog.setCanceledOnTouchOutside(false)
        editDetailsDialog.setContentView(editDetailsBinding.root)
        editDetailsDialog.show()
        editDetailsBinding.firstNameETxt.setText(binding.firstNameTxtVw.getText().toString())
        editDetailsBinding.lastNameETxt.setText(binding.lastNameTxtVw.getText().toString())
        val dropDownAdapter = ArrayAdapter(requireContext(), R.layout.gender_spinner_item, resources.getStringArray(R.array.genders))
        editDetailsBinding.genderSpinner.adapter = dropDownAdapter
        if(binding.genderTxtVw.text == "male"){
            editDetailsBinding.genderSpinner.setSelection(0)
        }
        else{
            editDetailsBinding.genderSpinner.setSelection(1)
        }
        editDetailsBinding.cancelBtn.setOnClickListener{
            editDetailsDialog.dismiss()
            _editDetailsBinding = null
        }
        editDetailsBinding.backBtn.setOnClickListener{
            editDetailsDialog.dismiss()
            _editDetailsBinding = null
        }

        editDetailsBinding.continueBtn.setOnClickListener{
            val gender = if(editDetailsBinding.genderSpinner.selectedItem.toString() == "Male"){
                "m"
            }
            else{
                "f"
            }
            profileViewModel.updateProfile(UpdateProfileRequest(editDetailsBinding.firstNameETxt.text.toString(), editDetailsBinding.lastNameETxt.text.toString(), gender))
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.profileResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    binding.profilePicImgVw.load(it.data?.profilePhoto)
                    binding.firstNameTxtVw.text = it.data?.firstname
                    binding.lastNameTxtVw.text = it.data?.lastname
                    binding.genderTxtVw.text = it.data?.gender
                    binding.emailTxtVw.setText(it.data?.email.toString())
                    profileViewModel.originalEmail = it.data?.email!!
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

        profileViewModel.updateProfileResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    binding.profilePicImgVw.load(it.data?.profilePhoto)
                    binding.firstNameTxtVw.text = it.data?.firstname
                    binding.lastNameTxtVw.text = it.data?.lastname
                    binding.genderTxtVw.text = it.data?.gender
                    binding.emailTxtVw.setText(it.data?.email.toString())
                    Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                    editDetailsDialog.dismiss()
                    _editDetailsBinding = null
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

        profileViewModel.addressResponse.observe(viewLifecycleOwner){
            when (it){
                is NetworkResult.Success -> {
                    addressOrderAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {}
            }
        }

        profileViewModel.updateEmailResponse.observe(viewLifecycleOwner){
            when (it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    showUpdateEmailDialog()
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

        profileViewModel.resetEmailResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), "Email changed successfully", Toast.LENGTH_SHORT).show()
                    _updateEmailBinding = null
                    updateEmailDialog.dismiss()
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _editDetailsBinding = null
        _progressBarBinding = null
    }

}