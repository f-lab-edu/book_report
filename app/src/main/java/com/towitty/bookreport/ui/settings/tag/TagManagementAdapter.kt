package com.towitty.bookreport.ui.settings.tag

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.towitty.bookreport.databinding.ItemTagBinding
import com.towitty.bookreport.model.TagEntity

class TagManagementAdapter(
    private val onDeleteClick: (TagEntity) -> Unit
) : RecyclerView.Adapter<TagManagementAdapter.TagViewHolder>() {

    private var tags: List<TagEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int = tags.size

    @SuppressLint("NotifyDataSetChanged")
    fun setTags(tags: List<TagEntity>) {
        this.tags = tags
        notifyDataSetChanged()
    }

    inner class TagViewHolder(private val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: TagEntity) {
            binding.tvTagName.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, tag.color)
            binding.tvTagName.text = tag.name

            binding.btnDelete.setOnClickListener {
                onDeleteClick(tag)
            }
        }
    }
}