package ashutosh.shopit.ui.order

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
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.Constants
import ashutosh.shopit.R
import ashutosh.shopit.adapters.AddressOrderAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentOrderBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.interfaces.AddressClickListener
import ashutosh.shopit.ui.payment.PaymentActivity
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import kotlin.math.roundToInt

@AndroidEntryPoint
class OrderFragment() : Fragment() {

    private var _binding : FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding get() = _binding!!

    private lateinit var addressOrderAdapter : AddressOrderAdapter

    private val orderViewModel by viewModels<OrderViewModel>()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        binding.viewModel = orderViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)


        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        val addressClickListener = object : AddressClickListener {
            override fun onAddressClick(addressId: Int) {
                binding.addressRecyclerView.post(addressOrderAdapter::notifyDataSetChanged)
            }

            override fun onDeleteClick(addressId: Int) {
                orderViewModel.deleteAddress(addressId)
            }
        }

        addressOrderAdapter = AddressOrderAdapter(addressClickListener)
        binding.addressRecyclerView.adapter = addressOrderAdapter

        binding.checkoutBtn.setOnClickListener{
            if(addressOrderAdapter.selectedPosition!=-1){
                orderViewModel.placeOrderByCart(orderViewModel.addressResponse.value!!.data!![addressOrderAdapter.selectedPosition].id)
            }
            else{
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
            }
        }

        orderViewModel.getAddresses()
        orderViewModel.getCartProducts()
        binding.addAddressBtn.setOnClickListener {
            findNavController().navigate(R.id.action_orderFragment_to_addAddressFragment)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel.addressResponse.observe(viewLifecycleOwner){
            when (it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    addressOrderAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

        orderViewModel.placeOrderResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    val intent = Intent(requireContext(), PaymentActivity::class.java)
                    val jsonString = Gson().toJson(it.data)
                    intent.putExtra("data", jsonString)
                    intent.putExtra("paymentType", 1)
                    startActivity(intent)
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

        orderViewModel.getCartProductsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    val list = it.data!!.content
                    var total = 0
                    for (item in list) {
                        total += item.noOfProducts * (item.product.originalPrice - (item.product.offerPercentage / 100) * item.product.originalPrice).roundToInt()
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
        }

        orderViewModel.deleteAddressResponse.observe(viewLifecycleOwner){
            when (it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                    orderViewModel.getAddresses()
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}