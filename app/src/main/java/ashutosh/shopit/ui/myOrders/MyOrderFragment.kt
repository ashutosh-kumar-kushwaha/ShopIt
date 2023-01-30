package ashutosh.shopit.ui.myOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ashutosh.shopit.R
import ashutosh.shopit.databinding.FragmentMyOrderBinding

class MyOrderFragment : Fragment() {

    private var _binding: FragmentMyOrderBinding? = null
    private val binding: FragmentMyOrderBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyOrderBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}