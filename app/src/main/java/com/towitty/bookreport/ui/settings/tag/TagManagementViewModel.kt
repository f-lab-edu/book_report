package com.towitty.bookreport.ui.settings.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.local.TagRepository
import com.towitty.bookreport.model.TagEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TagManagementViewModel @Inject constructor(
    private val tagLocalRepository: TagRepository
) : ViewModel() {

    private val _tagList = MutableStateFlow<List<TagEntity>>(emptyList())
    val tagList: StateFlow<List<TagEntity>> = _tagList.asStateFlow()

    fun getAllTags() {
        viewModelScope.launch {
            tagLocalRepository.getAllTags().collect { tags ->
                _tagList.value = tags
            }
        }
    }

    fun deleteTag(tagEntity: TagEntity) {
        viewModelScope.launch {
            tagLocalRepository.deleteTag(tagEntity)
        }
    }
}