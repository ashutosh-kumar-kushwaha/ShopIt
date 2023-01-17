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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ashutosh.shopit.databinding.FragmentResetPasswordBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.repository.ResetPasswordRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private var _binding : FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val resetPasswordViewModel by viewModels<ResetPasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)


        if(arguments?.getString("email") != null && resetPasswordViewModel.email.isEmpty()){
            resetPasswordViewModel.email = arguments?.getString("email")!!
        }
        if(arguments?.getString("otp") != null && resetPasswordViewModel.email.isEmpty()){
            resetPasswordViewModel.otp = arguments?.getString("otp")!!
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = resetPasswordViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.resetPasswordBtn.setOnClickListener {
            lifecycleScope.launch {
                resetPasswordViewModel.resetPassword()
            }
        }

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