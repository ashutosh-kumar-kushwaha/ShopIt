package ashutosh.shopit.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import ashutosh.shopit.R
import ashutosh.shopit.adapters.OffersAdapter
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.databinding.FragmentHomeBinding
import ashutosh.shopit.di.NetworkResult
import java.lang.reflect.Field

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    private val productsAdapter = ProductsAdapter()

    private lateinit var circles : List<ImageView>

    @SuppressLint("DiscouragedPrivateApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = homeViewModel

        val searchText = binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        val font = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        searchText.typeface = font
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

//        binding.offersViewPager.adapter = OffersAdapter(homeViewModel.offersList.value!!)

        binding.itemRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.itemRecyclerView.adapter = productsAdapter

        binding.itemRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_21), resources.getDimensionPixelSize(R.dimen.dp_8)))

        circles =  listOf(binding.circle1, binding.circle2, binding.circle3, binding.circle4, binding.circle5, binding.circle6)

        binding.offersViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                val currentItem = binding.offersViewPager.currentItem

                selectItem(currentItem)
                unselectItem(currentItem-1)
                unselectItem(currentItem+1)

            }
        })


        binding.categoryAllChip.setOnClickListener {
            homeViewModel.getAllProducts()
        }
        binding.categoryChip1.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(0)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(
                    it1
                )
            }
        }
        binding.categoryChip2.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(1)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(
                    it1
                )
            }
        }
        binding.categoryChip3.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(2)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(
                    it1
                )
            }
        }
        binding.categoryChip4.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(3)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(
                    it1
                )
            }
        }
        binding.categoryChip5.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(4)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(
                    it1
                )
            }
        }

        homeViewModel.getCategories()
        homeViewModel.getAllProducts()

        return binding.root
    }

    private fun selectItem(i: Int){
        var index = i
        if(index < 0) index+=6
        if(index > 5) index-=6
        val imageView = circles[index]
        imageView.setImageResource(R.drawable.viewpager_selected)
        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_16), resources.getDimensionPixelSize(R.dimen.dp_5))
        lp.marginStart = resources.getDimensionPixelSize(R.dimen.dp_2)
        imageView.layoutParams = lp
    }

    private fun unselectItem(i: Int){
        var index = i
        if(index < 0) index+=6
        if(index > 5) index-=6
        val imageView = circles[index]
        imageView.setImageResource(R.drawable.viewpager_not_selected)
        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_5), resources.getDimensionPixelSize(R.dimen.dp_5))
        lp.marginStart = resources.getDimensionPixelSize(R.dimen.dp_2)
        imageView.layoutParams = lp
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    binding.categoryChip1.text = it.data?.content?.get(0)?.categoryName
                    binding.categoryChip2.text = it.data?.content?.get(1)?.categoryName
                    binding.categoryChip3.text = it.data?.content?.get(2)?.categoryName
                    binding.categoryChip4.text = it.data?.content?.get(3)?.categoryName
                    binding.categoryChip5.text = it.data?.content?.get(4)?.categoryName
                }
            }
        }

        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.itemRecyclerView.visibility = View.VISIBLE
                    binding.itemRecyclerVwShimmer.visibility = View.GONE
                    productsAdapter.submitList(it.data?.content)
                    binding.itemRecyclerVwShimmer.stopShimmerAnimation()
                }

                is NetworkResult.Loading -> {
                    binding.itemRecyclerVwShimmer.visibility = View.VISIBLE
                    binding.itemRecyclerView.visibility = View.GONE
                    binding.itemRecyclerVwShimmer.startShimmerAnimation()
                }

                is NetworkResult.Error -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.offersList.observe(viewLifecycleOwner) {
            binding.offersViewPager.adapter = OffersAdapter(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}