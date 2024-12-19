package com.twitty.feature.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.IBookRepository
import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: IBookRepository
) : ViewModel() {
    private val _book: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val book: StateFlow<List<Book>> = _book

    fun searchBooks(bookSearchCriteria: BookSearchCriteria) {
        viewModelScope.launch {
            bookRepository.searchBooks(bookSearchCriteria).collect {
                _book.value = it
            }
        }
    }
}