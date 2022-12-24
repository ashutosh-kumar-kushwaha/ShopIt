package ashutosh.shopit.ui.auth.forgotPasswordOtpVerification

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentForgotPasswordOtpVerificationBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.ui.auth.AuthenticationActivity
import kotlinx.coroutines.launch

class ForgotPasswordOtpVerificationFragment : Fragment() {

    private var _binding : FragmentForgotPasswordOtpVerificationBinding? = null
    private val binding : FragmentForgotPasswordOtpVerificationBinding get() = _binding!!

//    private val forgotPasswordOtpVerificationViewModel by viewModels<ForgotPasswordOtpVerificationViewModel>()
    private var _forgotPasswordOtpVerificationViewModel : ForgotPasswordOtpVerificationViewModel? = null
    private val forgotPasswordOtpVerificationViewModel : ForgotPasswordOtpVerificationViewModel get() = _forgotPasswordOtpVerificationViewModel!!


    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private var email = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForgotPasswordOtpVerificationBinding.inflate(inflater, container, false)

        if(arguments?.getString("email") != null){
            email = arguments?.getString("email")!!
        }

        _forgotPasswordOtpVerificationViewModel = ViewModelProvider(viewModelStore, FPOtpVerifyViewModelFactory(email))[ForgotPasswordOtpVerificationViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = forgotPasswordOtpVerificationViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launch {
                forgotPasswordOtpVerificationViewModel.verifyForgotPasswordOtp()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgotPasswordOtpVerificationViewModel.responseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    val bundle = Bundle()
                    bundle.putString("email", forgotPasswordOtpVerificationViewModel.email)
                    bundle.putString("otp", forgotPasswordOtpVerificationViewModel.otp)
                    findNavController().navigate(R.id.action_forgotPasswordOtpVerificationFragment_to_resetPasswordFragment, bundle)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}