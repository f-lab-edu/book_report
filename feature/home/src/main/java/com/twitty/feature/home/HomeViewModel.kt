package com.twitty.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.BookReportRepository
import com.twitty.core.data.repository.FavoritesRepository
import com.twitty.model.Book
import com.twitty.model.BookReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val bookReportRepository: BookReportRepository
) : ViewModel() {

    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks: StateFlow<List<Book>> = _favoriteBooks

    private val _favoriteBookReports = MutableStateFlow<List<BookReport>>(emptyList())
    val favoriteBookReports: StateFlow<List<BookReport>> = _favoriteBookReports.asStateFlow()

    private val _bookReports = MutableStateFlow<List<BookReport>>(emptyList())
    val bookReports: StateFlow<List<BookReport>> = _bookReports.asStateFlow()

    init {
        observeAllItems()
    }

    private fun observeAllItems() {
        viewModelScope.launch {
            favoritesRepository.fetchFavoriteBooks().collect { book ->
                _favoriteBooks.value = book
            }
            favoritesRepository.fetchFavoriteBookReports().collect { bookReport ->
                _favoriteBookReports.value = bookReport
            }
            bookReportRepository.fetchBookReports().collect { bookReport ->
                _bookReports.value = bookReport
            }
        }
    }

}