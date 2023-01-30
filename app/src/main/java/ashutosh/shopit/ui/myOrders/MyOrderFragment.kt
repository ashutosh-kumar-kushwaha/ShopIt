package ashutosh.shopit.ui.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.MyOrderAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentMyOrderBinding

class MyOrderFragment : Fragment() {

    private var _binding: FragmentMyOrderBinding? = null
    private val binding: FragmentMyOrderBinding get() = _binding!!

    private val myOrderViewModel by viewModels<MyOrderViewModel>()

    private val myOrderAdapter = MyOrderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyOrderBinding.inflate(inflater, container, false)
        binding.viewModel = myOrderViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ordersRecyclerView.adapter = myOrderAdapter

        myOrderViewModel.getAllOrders()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myOrderViewModel.myOrdersResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    myOrderAdapter.submitList(it.data?.myOrderContent)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}