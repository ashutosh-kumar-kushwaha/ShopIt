package ashutosh.shopit.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ashutosh.shopit.R
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.DialogEditDetailsBinding
import ashutosh.shopit.databinding.FragmentProfileBinding
import ashutosh.shopit.databinding.ProgressBarBinding
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

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val profileViewModel by viewModels<ProfileViewModel>()

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

        binding.editDetailsBtn.setOnClickListener {
            showEditDetailsDialogue()
        }

        profileViewModel.getProfile()

        binding.addAddressBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addAddressFragment)
        }

        return binding.root
    }

    private fun showEditDetailsDialogue(){
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
                    binding.emailTxtVw.text = it.data?.email
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
                    binding.emailTxtVw.text = it.data?.email
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _editDetailsBinding = null
        _progressBarBinding = null
    }

}