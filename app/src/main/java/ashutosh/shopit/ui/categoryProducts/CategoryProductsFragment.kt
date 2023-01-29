package ashutosh.shopit.ui.categoryProducts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentCategoryProductsBinding
import ashutosh.shopit.interfaces.ProductClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryProductsFragment : Fragment() {

    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding: FragmentCategoryProductsBinding get() = _binding!!

    private lateinit var productsAdapter : ProductsAdapter

    private val categoryProductsViewModel by viewModels<CategoryProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = categoryProductsViewModel

        if(arguments?.getInt("categoryId")!=null){
            categoryProductsViewModel.categoryId = arguments?.getInt("categoryId")!!
        }
        if(arguments?.getString("categoryName")!=null){
            categoryProductsViewModel.categoryName = arguments?.getString("categoryName")!!
        }


        binding.categoryNameTxtVw.text = categoryProductsViewModel.categoryName

        val productClickListener = object : ProductClickListener{
            override fun onProductClick(productId: Int) {
                val bundle = Bundle()
                bundle.putInt("productId", productId)
                findNavController().navigate(R.id.action_categoryProductsFragment_to_productFragment, bundle)
            }
        }

        productsAdapter = ProductsAdapter(productClickListener)
        binding.productsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productsRecyclerView.adapter = productsAdapter
        binding.productsRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_24), resources.getDimensionPixelSize(R.dimen.dp_9)))

        categoryProductsViewModel.getProductsByCategory()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryProductsViewModel.productsLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    productsAdapter.submitList(it.data?.content)
//                    Log.d("")
                }

                is NetworkResult.Loading -> {}

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}