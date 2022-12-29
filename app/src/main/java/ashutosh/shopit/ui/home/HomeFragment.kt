package ashutosh.shopit.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.OffersAdapter
import ashutosh.shopit.adapters.ProductSpacingItemDecoration
import ashutosh.shopit.adapters.ProductsAdapter
import ashutosh.shopit.databinding.FragmentHomeBinding
import ashutosh.shopit.models.Product
import ashutosh.shopit.ui.auth.getStarted.GetStartedViewModel
import java.lang.reflect.Field

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding : FragmentHomeBinding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()


    @SuppressLint("DiscouragedPrivateApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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

        binding.offersViewPager.adapter = OffersAdapter(listOf(R.drawable.offer, R.drawable.offer, R.drawable.offer, R.drawable.offer, R.drawable.offer, R.drawable.offer))

        binding.itemRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = ProductsAdapter()
        adapter.submitList(listOf(Product(1, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(2, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(3, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(4, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(5, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(6, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(7, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(8, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(9, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490"), Product(10, R.drawable.iphone, "Apple Iphone 14 128GB (Product) RED", "4.5", 1335, "₹ 77,490", "₹ 77,490")))
        binding.itemRecyclerView.adapter = adapter
        binding.itemRecyclerView.addItemDecoration(ProductSpacingItemDecoration(2, resources.getDimensionPixelSize(R.dimen.dp_21), resources.getDimensionPixelSize(R.dimen.dp_8)))


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}