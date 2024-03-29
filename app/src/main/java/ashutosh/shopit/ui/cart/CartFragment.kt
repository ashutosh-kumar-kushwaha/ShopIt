package ashutosh.shopit.ui.cart

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.CartAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentCartBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.interfaces.ChangeProductQuantity
import ashutosh.shopit.models.CartItem
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CartFragment : Fragment() , ChangeProductQuantity{

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = _binding!!

    private val cartViewModel by viewModels<CartViewModel>()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private val cartAdapter = CartAdapter(this@CartFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = cartViewModel

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.productsRecyclerView.adapter = cartAdapter
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        cartViewModel.getCartProducts()

        binding.checkoutBtn.setOnClickListener { 
            findNavController().navigate(R.id.action_cartFragment_to_orderFragment)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


//        cartAdapter.submitList(listOf(CartItem(1, "ABCD", "In stock", 3000, R.drawable.iphone), CartItem(2, "ABCD", "In stock", 3000, R.drawable.iphone)))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel.getCartProductsResponse.observe(viewLifecycleOwner){ it ->
            when (it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    val list = it.data!!.content
                    cartAdapter.submitList(list)
                    var total = 0
                    for(item in list){
                        total += item.noOfProducts*(item.product.originalPrice-(item.product.offerPercentage/100)*item.product.originalPrice).roundToInt()
                    }
                    var price = "₹$total"
                    binding.priceTxtVw.text = price
                    total += 30
                    price = "₹$total"
                    binding.totalPriceTxtVw.text = price

                }

                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }

            cartViewModel.changeProductQuantity.observe(viewLifecycleOwner){
                when(it){
                    is NetworkResult.Success -> progressBar.dismiss()
                    is NetworkResult.Error -> {
                        progressBar.dismiss()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResult.Loading -> progressBar.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }

    override fun increaseProductQuantity(productId: Int) {
        cartViewModel.increaseProductQuantity(productId)
    }

    override fun decreaseProductQuantity(productId: Int) {
        cartViewModel.decreaseProductQuantity(productId)
    }
}