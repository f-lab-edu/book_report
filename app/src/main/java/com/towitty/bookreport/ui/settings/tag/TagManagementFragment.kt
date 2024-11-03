package com.towitty.bookreport.ui.settings.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.towitty.bookreport.databinding.FragmentTagManagementBinding

class TagManagementFragment : Fragment() {
    private var _binding: FragmentTagManagementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagManagementBinding.inflate(inflater, container, false)

        setNavigation()
        binding.btnAddTag.setOnClickListener {
            // TODO: Add tag CustomBottomSheetDialogFragment 표시
        }
        return binding.root
    }

    private fun setNavigation() {
        binding.tbTagManagement.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}
