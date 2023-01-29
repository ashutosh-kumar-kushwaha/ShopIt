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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import ashutosh.shopit.R
import ashutosh.shopit.adapters.DescriptionAdapter
import ashutosh.shopit.adapters.ProductImageAdapter
import ashutosh.shopit.adapters.QuestionsAnswersAdapter
import ashutosh.shopit.adapters.SpecsParentAdapter
import ashutosh.shopit.databinding.FragmentProductBinding
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.ProgressBarBinding
import ashutosh.shopit.ui.MainActivity
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
                    progressBar.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    progressBar.hide()
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

                    binding.productNameTxtVw.text = product.productName
                    val currentPrice = "₹${price((product.originalPrice-(product.offerPercentage/100)*product.originalPrice).roundToInt())}"
                    binding.currentPriceTxtVw.text = currentPrice
                    val originalPrice = "₹${price(product.originalPrice.roundToInt())}"
                    binding.originalPriceTxtVw.text = originalPrice
                    binding.ratingTxtVw.text = product.rating.toString()
                    binding.descriptionRecyclerView.adapter = DescriptionAdapter(product.description)
                    binding.descriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.specificationRecyclerView.adapter = SpecsParentAdapter(product.specification)
                    binding.specificationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.warrantyDetailsTxtVw.text = product.warranty
                    binding.questionsAnswerRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    selectItem(0)
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
                    Log.d("Ashu", it.message.toString())
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }

        productViewModel.toastMsg.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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