package com.twitty.feature.tag

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.twitty.feature.tag.databinding.ItemTagBinding
import com.twitty.model.Tag

class TagManagementAdapter(
    private val onDeleteClick: (Tag) -> Unit
) : RecyclerView.Adapter<TagManagementAdapter.TagViewHolder>() {

    private var tags: List<Tag> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int = tags.size

    @SuppressLint("NotifyDataSetChanged")
    fun setTags(tags: List<Tag>) {
        this.tags = tags
        notifyDataSetChanged()
    }

    inner class TagViewHolder(private val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: Tag) {
            binding.tvTagName.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, tag.color)
            binding.tvTagName.text = tag.name

            binding.btnDelete.setOnClickListener {
                onDeleteClick(tag)
            }
        }
    }
}