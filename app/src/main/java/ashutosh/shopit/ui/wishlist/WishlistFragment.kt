package ashutosh.shopit.ui.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentWishlistBinding
import ashutosh.shopit.interfaces.ProductClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {

    private var _binding: FragmentWishlistBinding? = null
    private val binding: FragmentWishlistBinding get() = _binding!!

    private val wishlistViewModel by viewModels<WishlistViewModel>()

    private lateinit var productsAdapter : ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = wishlistViewModel

        val productClickListener = object: ProductClickListener{
            override fun onProductClick(productId: Int) {
                val bundle = Bundle()
                bundle.putInt("productId", productId)
                findNavController().navigate(R.id.action_wishlistFragment_to_productFragment, bundle)
            }
        }
        productsAdapter = ProductsAdapter(productClickListener)
        binding.productsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productsRecyclerView.adapter = productsAdapter
        binding.productsRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_24), resources.getDimensionPixelSize(R.dimen.dp_9)))

        wishlistViewModel.getWishlist()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wishlistViewModel.wishlistResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    productsAdapter.submitList(it.data?.content)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}