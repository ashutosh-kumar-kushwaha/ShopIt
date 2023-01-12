package ashutosh.shopit.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentProductBinding
import ashutosh.shopit.di.NetworkResult

class ProductFragment : Fragment() {

    private var _binding : FragmentProductBinding? = null
    private val binding : FragmentProductBinding get() = _binding!!

    private val productViewModel by viewModels<ProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        binding.viewModel = productViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if(arguments?.getInt("productId")!=null){
            productViewModel.productId = arguments?.getInt("productId")!!
        }

        productViewModel.getProductDetails()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.productDetailsResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Success -> {
                    
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}