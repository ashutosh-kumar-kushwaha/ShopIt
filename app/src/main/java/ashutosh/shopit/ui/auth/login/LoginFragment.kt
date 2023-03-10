package ashutosh.shopit.ui.auth.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentLoginBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.datastore.DataStoreManager
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.LogInInfo
import ashutosh.shopit.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = loginViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken("1084765789984-87pi66fb0jaur5gphrf7tnck4p53pue6.apps.googleusercontent.com").build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleBtn.setOnClickListener {
            signInWithGoogle()
        }

        binding.forgotPasswordBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.RegisterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_getStartedFragment)
        }

        gsc.signOut()

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launch{
                loginViewModel.login()
            }
        }

        return binding.root
    }

    private fun signInWithGoogle() {
        val signInWithGoogleIntent = gsc.signInIntent
        startActivityForResult(signInWithGoogleIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                task.getResult(ApiException::class.java)
                val account = GoogleSignIn.getLastSignedInAccount(requireContext())

                if(account?.idToken != null) {
                    lifecycleScope.launch {
                        loginViewModel.googleSignIn(account.idToken!!)
                    }
                }
            }
            catch (e : Exception){
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginResponseLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    lifecycleScope.launch {
                        val job = lifecycleScope.launch {
                            val dataStoreManager = DataStoreManager(requireContext())
                            dataStoreManager.storeLogInInfo(LogInInfo(it.data?.accessToken, it.data?.refreshToken, true, it.data?.firstname, it.data?.lastname, it.data?.roles?.get(0)?.name, it.data?.email))
                        }
                        job.join()
                    }
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
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

        loginViewModel.errorMessage.observe(viewLifecycleOwner, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }


}