package com.twitty.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.IBookRepository
import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookRepository: IBookRepository
) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    fun searchBooks(bookSearchCriteria: BookSearchCriteria) {
        viewModelScope.launch {
            bookRepository.searchBooks(bookSearchCriteria)
                .collect {
                    _books.value = it
                }
        }
    }
}