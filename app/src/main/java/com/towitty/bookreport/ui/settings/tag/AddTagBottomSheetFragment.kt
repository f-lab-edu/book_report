package com.towitty.bookreport.ui.settings.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.towitty.bookreport.databinding.FragmentAddTagBottomSheetBinding
import com.towitty.bookreport.model.ColorItem

class AddTagBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTagBottomSheetBinding? = null
    private val binding get() = _binding!!

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
        _binding = null
    }

    private fun setButtonEvents() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            TODO("Save tag")
        }
    }

    private fun setColorAdapter() {
        val colorItems = ColorItem.entries.toList()
        binding.rvColorList.adapter = AddTagBottomSheetColorAdapter(colorItems)
    }
}
