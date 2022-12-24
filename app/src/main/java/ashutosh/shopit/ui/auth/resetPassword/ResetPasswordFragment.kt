package ashutosh.shopit.ui.auth.resetPassword

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
import androidx.lifecycle.get
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentLoginBinding
import ashutosh.shopit.databinding.FragmentResetPasswordBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.ui.auth.forgotPasswordOtpVerification.ForgotPasswordOtpVerificationViewModel

class ResetPasswordFragment : Fragment() {

    private var _binding : FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private var _resetPasswordViewModel : ResetPasswordViewModel? = null
    private val resetPasswordViewModel : ResetPasswordViewModel get() = _resetPasswordViewModel!!

    private var email = ""
    private var otp = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)


        if(arguments?.getString("email") != null){
            email = arguments?.getString("email")!!
        }
        if(arguments?.getString("otp") != null){
            otp = arguments?.getString("otp")!!
        }

        _resetPasswordViewModel = ViewModelProvider(viewModelStore, ResetPasswordViewModelFactory(email, otp))[ResetPasswordViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = resetPasswordViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetPasswordViewModel.resetPasswordResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
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

        resetPasswordViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}