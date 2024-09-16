package com.towitty.bookreport.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.towitty.bookreport.databinding.FragmentAddBookReportModalBinding

class AddBookReportModalFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddBookReportModalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddBookReportModalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDirectEnter.setOnClickListener { Toast.makeText(context, "직접 입력", Toast.LENGTH_SHORT).show() }
        binding.tvSearchBook.setOnClickListener { Toast.makeText(context, "책 검색", Toast.LENGTH_SHORT).show() }
        binding.tvBarcode.setOnClickListener { Toast.makeText(context, "바코드", Toast.LENGTH_SHORT).show() }
    }
}