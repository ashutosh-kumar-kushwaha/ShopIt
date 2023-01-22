package ashutosh.shopit.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.AddressOrderAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentOrderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private var _binding : FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding get() = _binding!!

    private val addressOrderAdapter = AddressOrderAdapter()

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
        binding.addressRecyclerView.adapter = addressOrderAdapter

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}