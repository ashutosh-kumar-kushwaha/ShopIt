package ashutosh.shopit.ui.search

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentSearchBinding
import ashutosh.shopit.interfaces.ButtonClickListener
import ashutosh.shopit.interfaces.ProductClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Field

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel>()

    private lateinit var productsAdapter : ProductsAdapter

    private lateinit var sortByBottomSheetFragment: SortByBottomSheetFragment


    @SuppressLint("DiscouragedPrivateApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val searchText = binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val font = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        searchText.typeface = font
        searchText.letterSpacing = 0.05F
        searchText.setTextColor(ContextCompat.getColor(requireContext(), R.color.color2))
        searchText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.color2))


        if(Build.VERSION.SDK_INT >= 29){
            searchText.textCursorDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.cursor_2)
        }
        else{
            try{
                val f: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                f.isAccessible = true
                f.set(searchText, R.drawable.cursor_2)
            }
            catch (_: Exception){}
        }

        val icon = binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        icon.setImageResource(R.drawable.ic_search_icon)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.search(newText)
                return true
            }

        })

        val productClickListener = object : ProductClickListener {
            override fun onProductClick(productId: Int) {
                Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        val buttonClickListener = object : ButtonClickListener{
            override fun onButtonClick(sortBy: String, sortDir: String) {
                Log.d("Ashu", "$sortBy $sortDir")
                searchViewModel.sortBy = sortBy
                searchViewModel.sortDir = sortDir
                searchViewModel.search(binding.searchView.query.toString())
//                sortByBottomSheetFragment.dismiss()
            }

        }

        binding.searchView.requestFocus()

        sortByBottomSheetFragment = SortByBottomSheetFragment(buttonClickListener)

        productsAdapter = ProductsAdapter(productClickListener)
        binding.productsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.productsRecyclerView.adapter = productsAdapter

        binding.productsRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_24), resources.getDimensionPixelSize(R.dimen.dp_9)))

        binding.sortByBtn.setOnClickListener {
            sortByBottomSheetFragment.show(parentFragmentManager, "SortByBottomSheet")
        }

//        binding.backBtn.setOnClickListener{
//            back()
//        }

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