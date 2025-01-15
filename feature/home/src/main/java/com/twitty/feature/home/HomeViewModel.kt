package com.twitty.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twitty.core.data.repository.IBookReportRepository
import com.twitty.core.data.repository.IFavoritesRepository
import com.twitty.core.data.repository.IRecommendedBooksRepository
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
    private val bookReportRepository: IBookReportRepository,
    private val recommendedBooksRepository: IRecommendedBooksRepository
) : ViewModel() {

    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks: StateFlow<List<Book>> = _favoriteBooks

    private val _bookReports = MutableStateFlow<List<BookReport>>(emptyList())
    val bookReports: StateFlow<List<BookReport>> = _bookReports

    private val _recommendedBooks = MutableStateFlow<List<Book>>(emptyList())
    val recommendedBooks: StateFlow<List<Book>> = _recommendedBooks

    init {
        observeAllItems()
    }

    private fun observeAllItems() {
        viewModelScope.launch {
            launch {
                favoritesRepository.fetchFavoriteBooks()
                    .collect { book ->
                        _favoriteBooks.value = book
                    }
            }
            launch {
                bookReportRepository.fetchBookReports()
                    .collect { bookReport ->
                        _bookReports.value = bookReport
                    }
            }
            launch {
                recommendedBooksRepository.fetchRecommendedBooks()
                    .collect { books ->
                        _recommendedBooks.value = books
                    }
            }
        }
    }
}