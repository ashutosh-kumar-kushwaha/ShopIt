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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ashutosh.shopit.GenericTextWatcher
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentSignUpOtpVerificationBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.repository.SignUpOtpVerificationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpOtpVerificationFragment : Fragment() {

    private var _binding : FragmentSignUpOtpVerificationBinding? = null
    private val binding : FragmentSignUpOtpVerificationBinding get() = _binding!!

    private val signUpOtpVerificationViewModel by viewModels<SignUpOtpVerificationViewModel>()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpOtpVerificationBinding.inflate(inflater, container, false)

        if(arguments?.getString("email") != null && signUpOtpVerificationViewModel.email.isEmpty()){
            signUpOtpVerificationViewModel.email = arguments?.getString("email")!!
        }

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

        binding.otpETxt1.addTextChangedListener(GenericTextWatcher(binding.otpETxt1, null, binding.otpETxt2))
        binding.otpETxt2.addTextChangedListener(GenericTextWatcher(binding.otpETxt2, binding.otpETxt1, binding.otpETxt3))
        binding.otpETxt3.addTextChangedListener(GenericTextWatcher(binding.otpETxt3, binding.otpETxt2, binding.otpETxt4))
        binding.otpETxt4.addTextChangedListener(GenericTextWatcher(binding.otpETxt4, binding.otpETxt3, binding.otpETxt5))
        binding.otpETxt5.addTextChangedListener(GenericTextWatcher(binding.otpETxt5, binding.otpETxt4, binding.otpETxt6))
        binding.otpETxt6.addTextChangedListener(GenericTextWatcher(binding.otpETxt6, binding.otpETxt5, null))

        binding.resendOtpTxtVw.setOnClickListener {
            lifecycleScope.launch {
                signUpOtpVerificationViewModel.resendOtp()
            }
        }

        signUpOtpVerificationViewModel.timer.start()

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

        signUpOtpVerificationViewModel.resendOtpResponseLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is NetworkResult.Success -> {
                    progressBar.hide()
                    signUpOtpVerificationViewModel.canResend.value = false
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    progressBar.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        })

        signUpOtpVerificationViewModel.canResend.observe(viewLifecycleOwner, Observer{
            if(it){
                binding.resendOtpTxtVw.text = "Resend OTP"
                binding.resendOtpTimeTxtVw.visibility = View.GONE
                binding.secondsTxtVw.visibility = View.GONE
            }
            else{
                binding.resendOtpTxtVw.text = "Resend OTP in"
                binding.resendOtpTimeTxtVw.visibility = View.VISIBLE
                binding.secondsTxtVw.visibility = View.VISIBLE
            }
        })

        signUpOtpVerificationViewModel.timeLiveData.observe(viewLifecycleOwner, Observer{
            binding.resendOtpTimeTxtVw.text = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}