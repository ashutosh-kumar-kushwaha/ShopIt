package ashutosh.shopit.ui.auth.forgotPasswordOtpVerification

import android.app.AlertDialog
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

class ForgotPasswordOtpVerificationFragment : Fragment() {

    private var _binding : FragmentForgotPasswordOtpVerificationBinding? = null
    private val binding : FragmentForgotPasswordOtpVerificationBinding get() = _binding!!

    private val forgotPasswordOtpVerificationViewModel by viewModels<ForgotPasswordOtpVerificationViewModel>()

    private lateinit var progressBar: AlertDialog
    private var builder: AlertDialog.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForgotPasswordOtpVerificationBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = forgotPasswordOtpVerificationViewModel

        progressBar = getDialogueProgressBar().create()
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.continueBtn.setOnClickListener {

        }

        return binding.root
    }

    private fun getDialogueProgressBar(): AlertDialog.Builder {
        if (builder == null) {
            builder = AlertDialog.Builder(binding.root.context)
            val progressBar = ProgressBar(binding.root.context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            progressBar.layoutParams = lp
            builder!!.setView(progressBar)
        }
        return builder as AlertDialog.Builder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}