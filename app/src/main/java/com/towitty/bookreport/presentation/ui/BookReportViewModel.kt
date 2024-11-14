package com.towitty.bookreport.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.network.model.BookItem
import com.towitty.bookreport.data.repository.BookRemoteRepository
import com.towitty.bookreport.data.repository.ITagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookReportViewModel @Inject constructor(
    private val bookRemoteRepository: BookRemoteRepository,
    private val tagLocalRepository: ITagRepository
) : ViewModel() {
    private val _bookList = MutableStateFlow<List<BookItem>>(emptyList())
    val bookList: StateFlow<List<BookItem>> = _bookList

    private val _tagList = MutableStateFlow<List<TagEntity>>(emptyList())
    val tagList: StateFlow<List<TagEntity>> = _tagList

    private val _addedTagList = MutableStateFlow<List<TagEntity>>(emptyList())
    val addedTagList: StateFlow<List<TagEntity>> = _addedTagList

    init {
        getAllTags()
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _bookList.value = bookRemoteRepository.searchBooks(query)
        }
    }

    fun findBookByIsbn(isbn: String): BookItem {
        return bookRemoteRepository.findBookByIsbn(isbn)
    }

    private fun getAllTags() {
        viewModelScope.launch {
            tagLocalRepository.getAllTags().collect { tags ->
                _tagList.value = tags
            }
        }
    }

    fun addSelectedTag(id: Int) {
        viewModelScope.launch {
            tagLocalRepository.getTag(id).collect { tag ->
                if (_addedTagList.value.none { it.id == tag.id }) {
                    _addedTagList.value += tag
                }
            }
        }
    }

    fun removeAddedTag(id: Int) {
        viewModelScope.launch {
            _addedTagList.value = _addedTagList.value.filter { it.id != id }
        }
    }
}