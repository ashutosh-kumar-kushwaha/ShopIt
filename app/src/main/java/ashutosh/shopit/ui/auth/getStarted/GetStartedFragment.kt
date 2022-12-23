package ashutosh.shopit.ui.auth.getStarted

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentGetStartedBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.di.NetworkResult
import kotlinx.coroutines.launch

class GetStartedFragment : Fragment() {

    private var _binding : FragmentGetStartedBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val getStartedViewModel by viewModels<GetStartedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGetStartedBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = getStartedViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_getStartedFragment_to_loginFragment)
        }

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launch {
                getStartedViewModel.signUpEmail()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStartedViewModel.signUpEmailResponseLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    //Navigate to otp verification
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

        getStartedViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }

}