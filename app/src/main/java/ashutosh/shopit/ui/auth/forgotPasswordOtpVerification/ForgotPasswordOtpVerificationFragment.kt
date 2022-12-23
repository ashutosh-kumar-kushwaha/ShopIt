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
import androidx.fragment.app.viewModels
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentForgotPasswordOtpVerificationBinding
import ashutosh.shopit.databinding.ProgressBarBinding

class ForgotPasswordOtpVerificationFragment : Fragment() {

    private var _binding : FragmentForgotPasswordOtpVerificationBinding? = null
    private val binding : FragmentForgotPasswordOtpVerificationBinding get() = _binding!!

    private val forgotPasswordOtpVerificationViewModel by viewModels<ForgotPasswordOtpVerificationViewModel>()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForgotPasswordOtpVerificationBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = forgotPasswordOtpVerificationViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.continueBtn.setOnClickListener {

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}