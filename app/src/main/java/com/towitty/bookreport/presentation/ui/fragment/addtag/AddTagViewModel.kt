package com.towitty.bookreport.presentation.ui.fragment.addtag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.repository.ITagRepository
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

    fun insertTag(tagEntity: TagEntity) {
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
