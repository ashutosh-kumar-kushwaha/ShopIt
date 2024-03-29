package ashutosh.shopit.ui.product

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import ashutosh.shopit.R
import ashutosh.shopit.adapters.*
import ashutosh.shopit.databinding.FragmentProductBinding
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.ui.MainActivity
import ashutosh.shopit.ui.bottomSheet.OffersBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding : FragmentProductBinding? = null
    private val binding : FragmentProductBinding get() = _binding!!

    private val productViewModel by viewModels<ProductViewModel>()

    private var circles = mutableListOf<ImageView>()
    private var circleNumber = 0

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    private var offersBottomSheet = OffersBottomSheetFragment(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        binding.viewModel = productViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

//        (activity as MainActivity).hideBottomNavBar()

        if(arguments?.getInt("productId") != null){
            productViewModel.productId = arguments?.getInt("productId")!!
//            Toast.makeText(requireContext(), productViewModel.productId, Toast.LENGTH_SHORT).show()
        }
//        Toast.makeText(requireContext(), productViewModel.productId, Toast.LENGTH_SHORT).show()
        productViewModel.getProductDetails()
        productViewModel.getReviews()
        productViewModel.getQuestionAnswers()

        binding.photosViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                val currentItem = binding.photosViewPager.currentItem

                selectItem(currentItem)
                if(currentItem!=0){
                    unselectItem(currentItem-1)
                }
                unselectItem(currentItem+1)
//                if(currentItem >= 2*circleNumber){
//                    binding.photosViewPager.setCurrentItem(currentItem-circleNumber, false)
//                }
//
//                if(currentItem < circleNumber){
//                    binding.photosViewPager.setCurrentItem(currentItem+circleNumber, false)
//                }

            }
        })

        binding.addToCartBtn.setOnClickListener {
            productViewModel.addToCart()
        }
        binding.quantityPlusBtn.setOnClickListener{
            productViewModel.increaseQuantity()
        }
        binding.quantityMinusBtn.setOnClickListener{
            productViewModel.decreaseQuantity()
        }
        binding.rateProductBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("productId", productViewModel.productId)
            bundle.putString("productName", productViewModel.productDetailsResponse.value?.data?.productName)
            bundle.putString("productImage",
                productViewModel.productDetailsResponse.value?.data?.imageUrls?.get(0)?.imageUrl
            )
            findNavController().navigate(R.id.action_productFragment_to_reviewFragment, bundle)
        }

        binding.buyNowBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("productId", productViewModel.productId)
            bundle.putInt("quantity", Integer.parseInt(productViewModel.quantity.value!!))
            bundle.putString("price", binding.currentPriceTxtVw.text.toString())
            bundle.putString("totalPrice", "₹${productViewModel.currentPrice+30}")
            findNavController().navigate(R.id.action_productFragment_to_directOrderFragment, bundle)
        }

        binding.offersTxtVw.setOnClickListener {
            offersBottomSheet.show(parentFragmentManager, "Offer Bottom Sheet")
        }

        binding.likeBtn.setOnClickListener {
            productViewModel.addToWishlist()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.questionsAnswerRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.reviewsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.productDetailsResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    val product = it.data!!
                    binding.photosViewPager.adapter = ProductImageAdapter(product.imageUrls)
                    circleNumber = product.imageUrls.size
                    circles = mutableListOf()
                    for(i in 0 until circleNumber) {
                        val imageView = ImageView(requireContext())
                        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_6), resources.getDimensionPixelSize(R.dimen.dp_6))
                        imageView.layoutParams= lp
                        lp.marginStart = resources.getDimensionPixelSize(R.dimen.dp_2)
                        imageView.setImageResource(R.drawable.viewpager_not_selected)
                        binding.photosViewPagerIndicator.addView(imageView)
                        circles.add(imageView)
                    }
                    offersBottomSheet = OffersBottomSheetFragment(it.data.offers)
                    binding.productNameTxtVw.text = product.productName
                    productViewModel.currentPrice = (product.originalPrice-(product.offerPercentage/100)*product.originalPrice).roundToInt()
                    val currentPrice = "₹${price(productViewModel.currentPrice)}"
                    binding.currentPriceTxtVw.text = currentPrice
                    val originalPrice = "₹${price(product.originalPrice.roundToInt())}"
                    binding.originalPriceTxtVw.text = originalPrice
                    binding.ratingTxtVw.text = product.rating.toString()
                    binding.descriptionRecyclerView.adapter = DescriptionAdapter(product.description)
                    binding.descriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.specificationRecyclerView.adapter = SpecsParentAdapter(product.specification)
                    binding.specificationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.warrantyDetailsTxtVw.text = product.warranty
                    selectItem(0)
                    progressBar.dismiss()
                }
            }
        }

        productViewModel.addToCartResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.data!!.message, Toast.LENGTH_SHORT).show()
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

        productViewModel.toastMsg.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        productViewModel.reviewResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {}
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {

                        binding.reviewsRecyclerView.visibility = View.VISIBLE
                        binding.reviewsLayout.visibility = View.VISIBLE
                        binding.reviewsRecyclerView.adapter = ReviewsAdapter(it.data?.content!!)

                }
            }
        }

        productViewModel.addToWishlistResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
                is NetworkResult.Error -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    progressBar.dismiss()
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        productViewModel.questionAnswerResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {}
                is NetworkResult.Error -> {
                    binding.questionsAnswerRecyclerView.visibility = View.GONE
                    binding.questionsAnswersTxtVw.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    if(it.data?.content!!.isNotEmpty()){
                        binding.questionsAnswerRecyclerView.adapter = QuestionsAnswersAdapter(it.data.content)
                        binding.questionsAnswersTxtVw.visibility = View.VISIBLE
                        binding.questionsAnswerRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }
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

    private fun price(p : Int): String{
        val str = StringBuilder(p.toString().reversed())
        var count = 0
        var i = 1
        while(i < str.length){
            if(count == 2){
                str.insert(i, ',')
                i++
                count = 0
            }
            i++
            count++
        }
        return str.reverse().toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}