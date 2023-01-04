package ashutosh.shopit.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.R
import ashutosh.shopit.adapters.RecommendationAdapter
import ashutosh.shopit.databinding.FragmentAccountBinding
import ashutosh.shopit.models.Recommendation

class AccountFragment : Fragment() {

    private var _binding : FragmentAccountBinding? = null
    private val binding : FragmentAccountBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)


        val adapter = RecommendationAdapter()
        binding.recommendationRecyclerView.adapter = adapter
        adapter.submitList(listOf(Recommendation(2, "SmartPhones and devices", R.drawable.iphone), Recommendation(3, "SmartPhones and devices", R.drawable.iphone), Recommendation(4, "SmartPhones and devices", R.drawable.iphone), Recommendation(5, "SmartPhones and devices", R.drawable.iphone), Recommendation(1, "SmartPhones and devices", R.drawable.iphone)))
        binding.recommendationRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return binding.root
    }

}