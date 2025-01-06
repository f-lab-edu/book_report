package com.twitty.feature.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.ITagRepository
import com.twitty.model.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TagManagementViewModel @Inject constructor(
    private val tagLocalRepository: ITagRepository
) : ViewModel() {

    private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
    val tagList: StateFlow<List<Tag>> = _tagList.asStateFlow()

    fun getAllTags() {
        viewModelScope.launch {
            tagLocalRepository.getAllTags().collect { tags ->
                _tagList.value = tags
            }
        }
    }

    fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            tagLocalRepository.deleteTag(tag)
        }
    }
}