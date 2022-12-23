package ashutosh.shopit.ui.auth.forgotPassword

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
import ashutosh.shopit.databinding.FragmentForgotPasswordBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.ui.auth.AuthenticationActivity

class ForgotPasswordFragment : Fragment() {

    private var _binding : FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val forgotPasswordViewModel by viewModels<ForgotPasswordViewModel>()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = forgotPasswordViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgotPasswordViewModel.forgotPasswordResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    //navigate to next fragment
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> progressBar.show()
            }
        })

        forgotPasswordViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}