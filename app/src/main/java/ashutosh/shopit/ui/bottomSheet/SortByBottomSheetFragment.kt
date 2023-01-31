package ashutosh.shopit.ui.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RadioButton
import ashutosh.shopit.databinding.LayoutSortbyBinding
import ashutosh.shopit.interfaces.ButtonClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortByBottomSheetFragment(private val buttonClickListener: ButtonClickListener) : BottomSheetDialogFragment(){

    private var _binding: LayoutSortbyBinding? = null
    private val binding: LayoutSortbyBinding get() = _binding!!

    private lateinit var radioBtns : List<RadioButton>

    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutSortbyBinding.inflate(inflater, container, false)

        radioBtns = listOf(binding.priceAsc, binding.priceDesc, binding.ratingAsc, binding.ratingDesc, binding.offerAsc, binding.offerDesc)

        val sortDetails = listOf(listOf("originalPrice", "asc"), listOf("originalPrice", "dsc"), listOf("rating", "asc"), listOf("rating", "dsc"), listOf("offerPercentage", "asc"), listOf("offerPercentage", "dsc"))

        count = 0
        for(btn in radioBtns){
            btn.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    for (btn2 in radioBtns) {
                        if (btn != btn2) {
                            btn2.isChecked = false
                        }
                    }
                    buttonClickListener.onButtonClick(sortDetails[radioBtns.indexOf(btn)][0], sortDetails[radioBtns.indexOf(btn)][1])
                }
                count++
                if(count == 2){
                    dismiss()
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding = null
    }
}