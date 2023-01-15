package ashutosh.shopit.ui.product

import android.os.Bundle
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
import ashutosh.shopit.di.NetworkResult
import kotlin.math.roundToInt

class ProductFragment : Fragment() {

    private var _binding : FragmentProductBinding? = null
    private val binding : FragmentProductBinding get() = _binding!!

    private val productViewModel by viewModels<ProductViewModel>()

    private var circles = mutableListOf<ImageView>()
    private var circleNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        binding.viewModel = productViewModel
        binding.lifecycleOwner = viewLifecycleOwner

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.productDetailsResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    val product = it.data!!
                    binding.photosViewPager.adapter = ProductImageAdapter(product.imageUrls)
                    circleNumber = product.imageUrls.size
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
                    val currentPrice = "₹${(product.originalPrice-(product.offerPercentage/100)*product.originalPrice).roundToInt()}"
                    binding.currentPriceTxtVw.text = currentPrice
                    binding.originalPriceTxtVw.text = product.originalPrice.toString()
                    binding.ratingTxtVw.text = product.rating.toString()
                    binding.descriptionRecyclerView.adapter = DescriptionAdapter(product.description)
                    binding.descriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.specificationRecyclerView.adapter = SpecsParentAdapter(product.specification)
                    binding.specificationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.warrantyDetailsTxtVw.text = product.warranty
                    binding.questionsAnswerRecyclerView.adapter = QuestionsAnswersAdapter(product.questions)
                    binding.questionsAnswerRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    selectItem(0)
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
        val str = StringBuilder(p.toString())
        str.reverse()
        for(i in str.indices){
            if((i-1)%3 == 0){
                str.insert(i, ',')
            }
        }
        return str.reverse().toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}