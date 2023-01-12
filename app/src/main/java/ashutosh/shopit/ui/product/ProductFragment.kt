package ashutosh.shopit.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.DescriptionAdapter
import ashutosh.shopit.adapters.ProductImageAdapter
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

        if(arguments?.getInt("productId")!=null){
            productViewModel.productId = arguments?.getInt("productId")!!
        }

        productViewModel.getProductDetails()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.productDetailsResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Success -> {
                    val product = it.data!!
                    binding.photosViewPager.adapter = ProductImageAdapter(product.imageUrls)
                    circleNumber = product.imageUrls.size
                    for(i in 0 until circleNumber) {
                        val imageView = ImageView(requireContext())
                        val lp = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.dp_5), resources.getDimensionPixelSize(R.dimen.dp_5))
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
                    binding.questionsAnswerRecyclerView.adapter =
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}