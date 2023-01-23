package ashutosh.shopit.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentSearchBinding
import ashutosh.shopit.interfaces.ProductClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel>()

    private lateinit var productsAdapter : ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.search(newText)
                Log.d("Ashu", newText.toString())
                return true
            }

        })

        val productClickListener = object : ProductClickListener {
            override fun onProductClick(productId: Int) {
                Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        productsAdapter = ProductsAdapter(productClickListener)
        binding.productsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.productsRecyclerView.adapter = productsAdapter

        binding.productsRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_24), resources.getDimensionPixelSize(R.dimen.dp_9)))


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.searchResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    productsAdapter.submitList(it.data?.content)
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