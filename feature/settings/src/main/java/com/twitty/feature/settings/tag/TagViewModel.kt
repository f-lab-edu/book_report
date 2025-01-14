package com.twitty.feature.settings.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.ITagRepository
import com.twitty.model.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagRepository: ITagRepository
) : ViewModel() {

    private val _tags: MutableStateFlow<List<Tag>> = MutableStateFlow(emptyList())
    val tags: StateFlow<List<Tag>> = _tags

    init {
        fetchAllTags()
    }

    private fun fetchAllTags() {
        viewModelScope.launch {
            tagRepository.fetchAllTags().collect {
                _tags.value = it
            }
        }
    }

    fun insertTag(tag: Tag) {
        viewModelScope.launch {
            tagRepository.insertTag(tag)
        }
    }

    fun updateTag(tag: Tag) {
        viewModelScope.launch {
            tagRepository.updateTag(tag)
        }
    }

    fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            tagRepository.deleteTag(tag)
        }
    }

}