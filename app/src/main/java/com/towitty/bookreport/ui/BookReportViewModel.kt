package com.towitty.bookreport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.network.BookRemoteRepository
import com.towitty.bookreport.model.BookItem
import com.towitty.bookreport.model.emptyBookItem
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

    private val _selectedBook = MutableStateFlow<BookItem>(emptyBookItem)
    val selectedBook: StateFlow<BookItem> = _selectedBook

    fun searchBooks(query: String) {
        viewModelScope.launch {
            val books = bookRemoteRepository.searchBooks(query)
            _bookList.value = books.bookList
        }
    }

    fun findBookByIsbn(isbn: String) {
        _selectedBook.value = _bookList.value.find { it.isbn == isbn } ?: emptyBookItem
    }
}