package com.towitty.bookreport.ui.settings.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.towitty.bookreport.databinding.FragmentAddTagBottomSheetBinding
import com.towitty.bookreport.model.ColorItem
import com.towitty.bookreport.model.TagEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddTagBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTagBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddTagViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAddTagBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonEvents()
        setColorAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.etTagName.text?.clear()
        _binding = null
    }

    private fun setButtonEvents() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val color = ColorItem.entries.find { it.selected }?.color
            val tagEntity = TagEntity(
                id = 0,
                name = binding.etTagName.text.toString(),
                color = color ?: ColorItem.RED.color
            )
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.insertTag(tagEntity)
                dismiss()
            }

        }
    }

    private fun setColorAdapter() {
        val colorItems = ColorItem.entries.toList()
        binding.rvColorList.adapter = AddTagBottomSheetColorAdapter(colorItems)
    }
}
