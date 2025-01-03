package com.twitty.feature.book

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.twitty.core.data.repository.IBookRepository
import com.twitty.feature.book.navigation.BookRoute
import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import com.twitty.model.emptyBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookRepository: IBookRepository
) : ViewModel() {
    val isbn = savedStateHandle.toRoute<BookRoute>().isbn

    private val _book: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val book: StateFlow<List<Book>> = _book

    init {
        searchBooks(BookSearchCriteria(isbn = isbn))
    }

    private fun searchBooks(bookSearchCriteria: BookSearchCriteria) {
        viewModelScope.launch {
            bookRepository.searchBooks(bookSearchCriteria).collect {
                _book.value = it
            }
        }
    }

    fun favoriteBook() {
        viewModelScope.launch {
            val book = book.value.firstOrNull() ?: emptyBook
            val newBookId = bookRepository.saveBook(book.copy(isFavorite = !book.isFavorite))
            searchBooks(BookSearchCriteria(id = newBookId))
        }
    }
}