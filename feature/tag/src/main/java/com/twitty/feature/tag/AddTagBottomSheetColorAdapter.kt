package com.twitty.feature.tag

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.twitty.feature.tag.databinding.ItemSelectableColorBinding
import com.twitty.model.ColorPicker

class AddTagBottomSheetColorAdapter(private val items: List<ColorPicker>) :
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
        fun bind(colorItem: ColorPicker, position: Int) {
            binding.ivColor.setBackgroundResource(colorItem.color)

            binding.ivColor.setOnClickListener {
                if (selectedColor !== colorItem) {
                    selectedColor.toggle()
                    notifyItemChanged(items.indexOf(selectedColor))
                    selectedColor = colorItem
                    notifyItemChanged(position)
                }
            }
            binding.ivColor.strokeWidth = if (colorItem.selected) 5f else 0f
        }
    }
}