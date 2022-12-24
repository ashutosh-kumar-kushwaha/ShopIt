package ashutosh.shopit.ui.auth.signUp

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
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentSignUpBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.di.NetworkResult
import ashutosh.shopit.ui.auth.getStarted.GetStartedViewModel
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    private val binding : FragmentSignUpBinding get() = _binding!!

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private var _signUpViewModel : SignUpViewModel? = null
    private val signUpViewModel : SignUpViewModel get() = _signUpViewModel!!

    private var email = ""
    private var otp = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        if(arguments?.getString("email")!=null){
            email = arguments?.getString("email")!!
        }
        if(arguments?.getString("otp")!=null){
            otp = arguments?.getString("otp")!!
        }

        _signUpViewModel = ViewModelProvider(viewModelStore, SignUpViewModelFactory(email, otp))[SignUpViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = signUpViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

//        signUpViewModel.email = arguments?.getString("email")
//        signUpViewModel.otp = arguments?.getString("otp")

        binding.manImgVw.setOnClickListener {
            signUpViewModel.gender = "m"
            it.setBackgroundResource(R.drawable.man_selected)
            binding.womanImgVw.setBackgroundResource(R.drawable.woman)
        }

        binding.womanImgVw.setOnClickListener {
            signUpViewModel.gender = "f"
            it.setBackgroundResource(R.drawable.women_selected)
            binding.manImgVw.setBackgroundResource(R.drawable.man)
        }

        binding.createAccountBtn.setOnClickListener{
            lifecycleScope.launch {
                signUpViewModel.signUp()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.signUpResponseLiveData.observe(viewLifecycleOwner, Observer{
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

        signUpViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}