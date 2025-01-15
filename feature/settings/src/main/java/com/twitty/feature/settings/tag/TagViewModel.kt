package com.twitty.feature.settings.tag

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.ITagRepository
import com.twitty.designsystem.theme.Red
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

    private val _selectedColor: MutableStateFlow<Color> = MutableStateFlow(Red)
    val selectedColor: StateFlow<Color> = _selectedColor

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

    fun insertTag(name: String, color: Color) {
        viewModelScope.launch {
            tagRepository.insertTag(name, color.toArgb())
        }
    }

    fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            // TODO BookReport 테이블에서 사용중인 Tag 모두 찾아서 삭제
            tagRepository.deleteTag(tag)
        }
    }

    fun onSelectedColor(color: Color) {
        _selectedColor.value = color
    }


}