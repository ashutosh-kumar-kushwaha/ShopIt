package ashutosh.shopit.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import ashutosh.shopit.R
import ashutosh.shopit.interfaces.ProductClickListener
import ashutosh.shopit.adapters.OffersAdapter
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.databinding.FragmentHomeBinding
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.repository.HomePageRepository
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Field
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductClickListener {

    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

//    private lateinit var homeViewModel: HomeViewModel

//    @Inject
//    lateinit var homePageRepository: HomePageRepository

    private val productsAdapter = ProductsAdapter(this)

    private var circles = mutableListOf<ImageView>()
    private var circleNumber = 0

    private lateinit var categories : List<Chip>

    @SuppressLint("DiscouragedPrivateApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

//        homeViewModel = ViewModelProvider(viewModelStore, HomeViewModelFactory(homePageRepository))[HomeViewModel::class.java]


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = homeViewModel

        categories = listOf(binding.categoryAllChip, binding.categoryChip1, binding.categoryChip2, binding.categoryChip3, binding.categoryChip4, binding.categoryChip5)

//        val searchText = binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
//        val font = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
//        searchText.typeface = font
//        searchText.setTextColor(ContextCompat.getColor(requireContext(), R.color.color2))
//        searchText.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.color2))
//
//        searchText.setOnClickListener{
//            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
//        }
//
//        if(Build.VERSION.SDK_INT >= 29){
//            searchText.textCursorDrawable = AppCompatResources.getDrawable(requireContext(), R.drawable.cursor_2)
//        }
//        else{
//            try{
//                val f: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
//                f.isAccessible = true
//                f.set(searchText, R.drawable.cursor_2)
//            }
//            catch (_: Exception){}
//        }

//        val icon = binding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
//        icon.setImageResource(R.drawable.ic_search_icon)

        binding.itemRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.itemRecyclerView.adapter = productsAdapter

        binding.itemRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_24), resources.getDimensionPixelSize(R.dimen.dp_9)))

        binding.searchView.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.offersViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                val currentItem = binding.offersViewPager.currentItem

                selectItem(currentItem)
                unselectItem(currentItem-1)
                unselectItem(currentItem+1)
                if(currentItem >= 2*circleNumber){
                    binding.offersViewPager.setCurrentItem(currentItem-circleNumber, false)
                }

                if(currentItem < circleNumber){
                    binding.offersViewPager.setCurrentItem(currentItem+circleNumber, false)
                }

            }
        })


        binding.categoryAllChip.setOnClickListener {
            homeViewModel.getAllProducts()
        }
        binding.categoryChip1.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(0)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(it1)
                unselectAllCategories()
                selectCategory(it as Chip)
            }
        }
        binding.categoryChip2.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(1)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(it1)
                unselectAllCategories()
                selectCategory(it as Chip)
            }
        }
        binding.categoryChip3.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(2)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(it1)
                unselectAllCategories()
                selectCategory(it as Chip)
            }
        }
        binding.categoryChip4.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(3)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(it1)
                unselectAllCategories()
                selectCategory(it as Chip)
            }
        }
        binding.categoryChip5.setOnClickListener {
            homeViewModel.categoriesLiveData.value?.data?.content?.get(4)?.categoryId?.let { it1 ->
                homeViewModel.getProductsByCategory(it1)
                unselectAllCategories()
                selectCategory(it as Chip)
            }
        }

        homeViewModel.getCategories()
        homeViewModel.getAllProducts()
        homeViewModel.getAdvertisements()

        return binding.root
    }

    private fun selectItem(index: Int){
        val imageView = circles[index%circleNumber]
        imageView.setImageResource(R.drawable.viewpager_selected)
        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_18), resources.getDimensionPixelSize(R.dimen.dp_6))
        lp.marginStart = resources.getDimensionPixelSize(R.dimen.dp_2)
        imageView.layoutParams = lp
    }

    private fun unselectItem(index: Int){
        val imageView = circles[index%circleNumber]
        imageView.setImageResource(R.drawable.viewpager_not_selected)
        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_6), resources.getDimensionPixelSize(R.dimen.dp_6))
        lp.marginStart = resources.getDimensionPixelSize(R.dimen.dp_2)
        imageView.layoutParams = lp
    }

    private fun unselectAllCategories(){
        for(chip in categories){
            chip.setTextColor(Color.BLACK)
            chip.setChipBackgroundColorResource(R.color.white)
        }
    }

    private fun selectCategory(chip: Chip){
        chip.setChipBackgroundColorResource(R.color.black)
        chip.setTextColor(Color.WHITE)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.categoriesScrollView.visibility = View.INVISIBLE
                    binding.categoriesShimmer.visibility = View.VISIBLE
                    binding.categoriesShimmer.startShimmerAnimation()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.categoriesShimmer.stopShimmerAnimation()
                }
                is NetworkResult.Success -> {
                    binding.categoryChip1.text = it.data?.content?.get(0)?.categoryName
                    binding.categoryChip2.text = it.data?.content?.get(1)?.categoryName
                    binding.categoryChip3.text = it.data?.content?.get(2)?.categoryName
                    binding.categoryChip4.text = it.data?.content?.get(3)?.categoryName
                    binding.categoryChip5.text = it.data?.content?.get(4)?.categoryName
                    binding.categoriesShimmer.stopShimmerAnimation()
                    binding.categoriesShimmer.visibility = View.GONE
                    binding.categoriesScrollView.visibility = View.VISIBLE
                }
            }
        }

        homeViewModel.productsLiveData.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    binding.itemRecyclerVwShimmer.stopShimmerAnimation()
                    binding.itemRecyclerView.visibility = View.VISIBLE
                    binding.itemRecyclerVwShimmer.visibility = View.GONE
                    productsAdapter.submitList(it.data?.content)
                    Log.d("Ashu", it.data?.content.toString())
                }

                is NetworkResult.Loading -> {
                    binding.itemRecyclerVwShimmer.visibility = View.VISIBLE
                    binding.itemRecyclerView.visibility = View.INVISIBLE
                    binding.itemRecyclerVwShimmer.startShimmerAnimation()
                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.itemRecyclerVwShimmer.stopShimmerAnimation()
                }
            }
        }

        homeViewModel.offersList.observe(viewLifecycleOwner) {
            when(it){
                is NetworkResult.Success -> {
                    binding.offersShimmer.stopShimmerAnimation()
                    binding.viewpagerDetailsShimmer.stopShimmerAnimation()
                    binding.offersShimmer.visibility = View.GONE
                    binding.offersViewPager.visibility = View.VISIBLE
                    binding.offersViewPager.adapter = OffersAdapter(it.data?.images!! + it.data.images + it.data.images)
                    circleNumber = it.data.images.size
                    binding.viewpagerDetails.visibility = View.VISIBLE
                    binding.viewpagerDetailsShimmer.visibility = View.GONE
                    for(i in 0 until circleNumber) {
                        val imageView = ImageView(requireContext())
                        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_6), resources.getDimensionPixelSize(R.dimen.dp_6))
                        imageView.layoutParams= lp
                        lp.marginStart = resources.getDimensionPixelSize(R.dimen.dp_2)
                        imageView.setImageResource(R.drawable.viewpager_not_selected)
                        binding.viewpagerDetails.addView(imageView)
                        circles.add(imageView)
                    }
                    binding.offersViewPager.setCurrentItem(circleNumber, false)
                    selectItem(0)
                }
                is NetworkResult.Loading -> {
                    binding.offersShimmer.visibility = View.VISIBLE
                    binding.offersViewPager.visibility = View.INVISIBLE
                    binding.offersShimmer.startShimmerAnimation()
                    binding.viewpagerDetails.visibility = View.INVISIBLE
                    binding.viewpagerDetailsShimmer.visibility = View.VISIBLE
                    binding.viewpagerDetailsShimmer.startShimmerAnimation()
                }
                is NetworkResult.Error -> {
                    binding.offersShimmer.stopShimmerAnimation()
                    binding.viewpagerDetailsShimmer.stopShimmerAnimation()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClick(productId: Int) {
        val bundle = Bundle()
        bundle.putInt("productId", productId)
        findNavController().navigate(R.id.action_homeFragment_to_productFragment, bundle)
    }
}