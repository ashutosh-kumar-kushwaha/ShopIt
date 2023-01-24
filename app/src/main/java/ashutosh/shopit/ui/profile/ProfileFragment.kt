package ashutosh.shopit.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ashutosh.shopit.R
import ashutosh.shopit.databinding.DialogEditDetailsBinding
import ashutosh.shopit.databinding.FragmentProfileBinding
import ashutosh.shopit.databinding.ProgressBarBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private var _editDetailsBinding : DialogEditDetailsBinding? = null
    private val editDetailsBinding : DialogEditDetailsBinding get() = _editDetailsBinding!!
    private lateinit var editDetailsDialog : Dialog

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        binding.editDetailsBtn.setOnClickListener {
            showEditDetailsDialogue()
        }

        return binding.root
    }

    private fun showEditDetailsDialogue(){
        val displayRectangle = Rect()
        requireActivity().window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        _editDetailsBinding = DialogEditDetailsBinding.inflate(layoutInflater)
        editDetailsDialog = Dialog(editDetailsBinding.root.context)
        editDetailsBinding.root.minimumWidth = displayRectangle.width()
        editDetailsDialog.setCanceledOnTouchOutside(false)
        editDetailsDialog.setContentView(editDetailsBinding.root)
        editDetailsBinding.firstNameETxt.text = binding.firstNameTxtVw.editableText
        editDetailsBinding.lastNameTxtVw.text = binding.lastNameTxtVw.editableText
        val dropDownAdapter = ArrayAdapter(requireContext(), R.layout.gender_spinner_item, resources.getStringArray(R.array.genders))
        editDetailsBinding.genderSpinner.adapter = dropDownAdapter
        editDetailsDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _editDetailsBinding = null
        _progressBarBinding = null
    }

}