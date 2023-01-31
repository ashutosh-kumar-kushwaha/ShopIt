package ashutosh.shopit.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.adapters.OffersAdapter
import ashutosh.shopit.adapters.ProductOffersAdapter
import ashutosh.shopit.databinding.OffersBottomSheetBinding
import ashutosh.shopit.models.Offer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OffersBottomSheetFragment(private val offers: List<Offer>): BottomSheetDialogFragment() {

    private var _binding: OffersBottomSheetBinding? = null
    private val binding: OffersBottomSheetBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OffersBottomSheetBinding.inflate(inflater, container, false)

        binding.offersRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.offersRecyclerView.adapter = ProductOffersAdapter(offers)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}