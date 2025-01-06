package com.twitty.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.IBookReportRepository
import com.twitty.core.data.repository.IFavoritesRepository
import com.twitty.model.Book
import com.twitty.model.BookReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val favoritesRepository: IFavoritesRepository,
    private val bookReportRepository: IBookReportRepository
) : ViewModel() {

    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks: StateFlow<List<Book>> = _favoriteBooks

    private val _favoriteBookReports = MutableStateFlow<List<BookReport>>(emptyList())
    val favoriteBookReports: StateFlow<List<BookReport>> = _favoriteBookReports

    private val _bookReports = MutableStateFlow<List<BookReport>>(emptyList())
    val bookReports: StateFlow<List<BookReport>> = _bookReports

    init {
        observeAllItems()
    }

    private fun observeAllItems() {
        viewModelScope.launch {
            bookReportRepository.fetchBookReports().collect { bookReport ->
                _bookReports.value = bookReport
            }
            favoritesRepository.fetchFavoriteBooks().collect { book ->
                _favoriteBooks.value = book
            }
            favoritesRepository.fetchFavoriteBookReports().collect { bookReport ->
                _favoriteBookReports.value = bookReport
            }
        }
    }
}