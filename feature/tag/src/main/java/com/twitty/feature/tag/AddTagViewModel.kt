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
class AddTagViewModel @Inject constructor(
    private val tagRepository: ITagRepository
) : ViewModel() {

    private val _tagInserted = MutableStateFlow(false)
    val tagInserted: StateFlow<Boolean> = _tagInserted.asStateFlow()

    fun insertTag(tagEntity: Tag) {
        viewModelScope.launch {
            try {
                tagRepository.insertTag(tagEntity)
                _tagInserted.emit(true)
            } catch (e: Exception) {
                _tagInserted.emit(false)
            }
        }
    }
}
