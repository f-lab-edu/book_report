package com.towitty.bookreport.presentation.ui.fragment.addtag

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.towitty.bookreport.databinding.ItemSelectableColorBinding
import com.towitty.bookreport.presentation.model.ColorItem

class AddTagBottomSheetColorAdapter(private val items: List<ColorItem>) :
    RecyclerView.Adapter<AddTagBottomSheetColorAdapter.ColorViewHolder>() {

    private var selectedColor = items[0]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {

        val binding = ItemSelectableColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    inner class ColorViewHolder(private val binding: ItemSelectableColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(colorItem: ColorItem, position: Int) {
            binding.ivColor.setBackgroundResource(colorItem.color)

            binding.ivColor.setOnClickListener {
                if (selectedColor !== colorItem) {
                    selectedColor.selected = false
                    notifyItemChanged(items.indexOf(selectedColor))
                    colorItem.selected = true
                    selectedColor = colorItem
                    notifyItemChanged(position)
                }
            }
            binding.ivColor.strokeWidth = if (colorItem.selected) 5f else 0f
        }
    }
}