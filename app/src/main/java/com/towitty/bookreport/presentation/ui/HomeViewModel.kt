package com.towitty.bookreport.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.repository.BookLocalRepository
import com.towitty.bookreport.data.repository.BookReportRepository
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bookReportRepository: BookReportRepository,
    private val bookRepository: BookLocalRepository
): ViewModel() {
    private val _favoriteBooks = MutableStateFlow<List<Book>>(emptyList())
    val favoriteBooks: StateFlow<List<Book>> = _favoriteBooks.asStateFlow()

    private val _favoriteBookReports = MutableStateFlow<List<BookReport>>(emptyList())
    val favoriteBookReports: StateFlow<List<BookReport>> = _favoriteBookReports.asStateFlow()

    init {
        viewModelScope.launch {
            bookRepository.fetchFavoriteBooks().collect { _favoriteBooks.value = it }
        }
        viewModelScope.launch {
            bookReportRepository.fetchFavoriteBookReports().collect { _favoriteBookReports.value = it }
        }
    }
}