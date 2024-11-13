package com.towitty.bookreport.presentation.ui.fragment.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.towitty.bookreport.R
import com.towitty.bookreport.databinding.FragmentTagManagementBinding
import com.towitty.bookreport.presentation.ui.fragment.addtag.AddTagBottomSheetFragment
import com.towitty.bookreport.presentation.ui.fragment.addtag.AddTagViewModel
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class TagManagementFragment : Fragment() {
    private var _binding: FragmentTagManagementBinding? = null
    private val binding get() = _binding!!

    private val tagViewModel: TagManagementViewModel by viewModels()
    private val addTagViewModel: AddTagViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigation()
        showAddTagBottomSheet()
        setTagList()
        observeAddTagStatus()
    }

    private fun observeAddTagStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            addTagViewModel.tagInserted.collect { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.succeeded_to_save_tag),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.failed_to_save_tag),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setTagList() {
        binding.rvTagList.adapter = TagManagementAdapter { tagViewModel.deleteTag(it) }
        tagViewModel.getAllTags()
        // TODO: Tag 크기에 맞게 spanCount 를 변경할 수 있도록 수정

        viewLifecycleOwner.lifecycleScope.launch {
            tagViewModel.tagList.collect { tags ->
                (binding.rvTagList.adapter as TagManagementAdapter).setTags(tags)
            }
        }
    }

    private fun setNavigation() {
        binding.tbTagManagement.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showAddTagBottomSheet() {
        binding.btnAddTag.setOnClickListener {
            val addTagBottomSheetFragment = AddTagBottomSheetFragment()
            addTagBottomSheetFragment.show(parentFragmentManager, addTagBottomSheetFragment.tag)
        }
    }
}
