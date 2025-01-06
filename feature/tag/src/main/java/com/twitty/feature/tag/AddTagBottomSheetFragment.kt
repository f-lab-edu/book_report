package com.twitty.feature.tag

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.twitty.feature.tag.databinding.FragmentAddTagBottomSheetBinding
import com.twitty.model.ColorPicker
import com.twitty.model.Tag
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
            val tag = Tag(
                id = 0,
                name = binding.etTagName.text.toString(),
                color = Color.RED
            )
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.insertTag(tag)
                dismiss()
            }

        }
    }

    private fun setColorAdapter() {
        val colorItems = listOf(
            ColorPicker(color = Color.RED, selected = true),
            ColorPicker(color = Color.BLUE),
            ColorPicker(color = Color.GREEN),
            ColorPicker(color = Color.YELLOW),
            ColorPicker(color = Color.CYAN),
            ColorPicker(color = Color.MAGENTA),
            ColorPicker(color = Color.GRAY),
            ColorPicker(color = Color.BLACK),
        )
        binding.rvColorList.adapter = AddTagBottomSheetColorAdapter(colorItems)
    }
}
