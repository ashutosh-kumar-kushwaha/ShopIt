package ashutosh.shopit.ui.auth.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ashutosh.shopit.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var gso : GoogleSignInOptions
    private lateinit var gsc : GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleBtn.setOnClickListener {
            signInWithGoogle()
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



                Toast.makeText(requireContext(), account?.givenName, Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), account?.idToken, Toast.LENGTH_SHORT).show()
            }
            catch (e : Exception){
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}