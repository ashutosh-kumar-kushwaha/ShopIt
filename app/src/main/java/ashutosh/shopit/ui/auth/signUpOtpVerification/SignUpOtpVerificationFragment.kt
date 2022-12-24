package ashutosh.shopit.ui.auth.signUpOtpVerification

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentForgotPasswordOtpVerificationBinding
import ashutosh.shopit.databinding.FragmentSignUpOtpVerificationBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.ui.auth.forgotPasswordOtpVerification.ForgotPasswordOtpVerificationViewModel
import kotlinx.coroutines.launch

class SignUpOtpVerificationFragment : Fragment() {

    private var _binding : FragmentSignUpOtpVerificationBinding? = null
    private val binding : FragmentSignUpOtpVerificationBinding get() = _binding!!

    private var _signUpOtpVerificationViewModel : SignUpOtpVerificationViewModel? = null
    private val signUpOtpVerificationViewModel : SignUpOtpVerificationViewModel get() = _signUpOtpVerificationViewModel!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private var email = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpOtpVerificationBinding.inflate(inflater, container, false)

        if(arguments?.getString("email") != null){
            email = arguments?.getString("email")!!
        }

        _signUpOtpVerificationViewModel = ViewModelProvider(viewModelStore, SignUpOtpVerificationViewModelFactory(email))[SignUpOtpVerificationViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = signUpOtpVerificationViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launch {
                signUpOtpVerificationViewModel.verifySignUpOtp()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpOtpVerificationViewModel.responseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    val bundle = Bundle()
                    bundle.putString("email", signUpOtpVerificationViewModel.email)
                    bundle.putString("otp", signUpOtpVerificationViewModel.otp)
                    findNavController().navigate(R.id.action_signUpOtpVerificationFragment_to_signUpFragment, bundle)
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        })

        signUpOtpVerificationViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}