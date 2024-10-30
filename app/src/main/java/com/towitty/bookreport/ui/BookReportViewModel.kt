package com.towitty.bookreport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.network.BookRemoteRepository
import com.towitty.bookreport.model.BookItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookReportViewModel @Inject constructor(
    private val bookRemoteRepository: BookRemoteRepository,
) : ViewModel() {
    private val _bookList = MutableStateFlow<List<BookItem>>(emptyList())
    val bookList: StateFlow<List<BookItem>> = _bookList

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _bookList.value = bookRemoteRepository.searchBooks(query)
        }
    }

    fun findBookByIsbn(isbn: String): BookItem {
        return bookRemoteRepository.findBookByIsbn(isbn)
    }
}