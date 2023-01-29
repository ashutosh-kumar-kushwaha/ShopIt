package ashutosh.shopit.ui.category

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.CategoryAdapter
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.databinding.FragmentCategoriesBinding
import ashutosh.shopit.models.Category
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Field

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding : FragmentCategoriesBinding? = null
    private val binding : FragmentCategoriesBinding get() = _binding!!

    private val categoryViewModel by viewModels<CategoryViewModel>()

    private lateinit var categoriesAdapter : CategoryAdapter

    @SuppressLint("DiscouragedPrivateApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

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
        categoriesAdapter = CategoryAdapter(resources.getStringArray(R.array.colors).toList())

//        categoriesAdapter.submitList(listOf(Category(1, "Mobile"), Category(2, "Mobile"), Category(3, "Mobile"), Category(4, "Mobile"), Category(5, "Mobile")))
        binding.categoriesRecyclerView.adapter = categoriesAdapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        categoryViewModel.getCategories()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {}

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Success -> {
                    categoriesAdapter.submitList(it.data?.content)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}