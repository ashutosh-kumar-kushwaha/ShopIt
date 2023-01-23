package ashutosh.shopit.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.AddressOrderAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentOrderBinding
import ashutosh.shopit.interfaces.AddressClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment() : Fragment() {

    private var _binding : FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding get() = _binding!!

    private lateinit var addressOrderAdapter : AddressOrderAdapter

    private val orderViewModel by viewModels<OrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        binding.viewModel = orderViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        orderViewModel.getAddresses()

        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        val addressClickListener = object : AddressClickListener {
//            override fun onAddressClick(addressId: Int) {
//                binding.addressRecyclerView.post(addressOrderAdapter::notifyDataSetChanged)
//            }

            override fun onAddressClick(addressId: Int) {
                binding.addressRecyclerView.post(addressOrderAdapter::notifyDataSetChanged)
            }
        }

        addressOrderAdapter = AddressOrderAdapter(addressClickListener)
        binding.addressRecyclerView.adapter = addressOrderAdapter

        binding.checkoutBtn.setOnClickListener{
            orderViewModel.placeOrderByCart(orderViewModel.addressResponse.value!!.data!![addressOrderAdapter.selectedPosition].id)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel.addressResponse.observe(viewLifecycleOwner){
            when (it){
                is NetworkResult.Success -> {
                    addressOrderAdapter.submitList(it.data)
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        }

        orderViewModel.placeOrderResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    override fun onAddressClick(addressId: Int) {
//        Toast.makeText(requireContext(), addressId.toString(), Toast.LENGTH_SHORT).show()
//    }
}