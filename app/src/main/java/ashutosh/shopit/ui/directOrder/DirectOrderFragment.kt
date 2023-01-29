package ashutosh.shopit.ui.directOrder

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.AddressOrderAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentDirectOrderBinding
import ashutosh.shopit.databinding.FragmentOrderBinding
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.interfaces.AddressClickListener
import ashutosh.shopit.ui.order.OrderViewModel
import ashutosh.shopit.ui.payment.PaymentActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class DirectOrderFragment : Fragment() {

    private var _binding : FragmentDirectOrderBinding? = null
    private val binding: FragmentDirectOrderBinding get() = _binding!!

    private lateinit var addressOrderAdapter : AddressOrderAdapter

    private val directOrderViewModel by viewModels<DirectOrderViewModel>()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDirectOrderBinding.inflate(inflater, container, false)

        binding.viewModel = directOrderViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        if(arguments?.getInt("productId") != null){
            directOrderViewModel.productId = arguments?.getInt("productId")!!
        }
        if(arguments?.getInt("quantity") != null){
            directOrderViewModel.quantity = arguments?.getInt("quantity")!!
        }
        if(arguments?.getString("price") != null){
            val price = arguments?.getString("price")!!
            binding.priceTxtVw.text = price
            binding.deliveryPriceTxtVw.text = "₹30.00"
        }
        if(arguments?.getString("totalPrice") != null){
            val price = arguments?.getString("totalPrice")!!
            binding.totalPriceTxtVw.text = price
        }

        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val addressClickListener = object : AddressClickListener {
            override fun onAddressClick(addressId: Int) {
                binding.addressRecyclerView.post(addressOrderAdapter::notifyDataSetChanged)
            }
        }

        addressOrderAdapter = AddressOrderAdapter(addressClickListener)
        binding.addressRecyclerView.adapter = addressOrderAdapter

        binding.checkoutBtn.setOnClickListener{
            if(addressOrderAdapter.selectedPosition!=-1){
                directOrderViewModel.directPlaceOrder(directOrderViewModel.addressResponse.value!!.data!![addressOrderAdapter.selectedPosition].id)
            }
            else{
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
            }
        }

        directOrderViewModel.getAddresses()
        binding.addAddressBtn.setOnClickListener {
            findNavController().navigate(R.id.action_directOrderFragment_to_addAddressFragment)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        directOrderViewModel.addressResponse.observe(viewLifecycleOwner){
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

        directOrderViewModel.placeOrderResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    val intent = Intent(requireContext(), PaymentActivity::class.java)
                    val jsonString = Gson().toJson(it.data)
                    intent.putExtra("data", jsonString)
                    intent.putExtra("paymentType", 2)
                    intent.putExtra("productId", directOrderViewModel.productId)
                    intent.putExtra("quantity", directOrderViewModel.quantity)
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _progressBarBinding = null
    }
}