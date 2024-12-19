package com.twitty.feature.bookreport

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.twitty.core.data.repository.IBookReportRepository
import com.twitty.core.data.repository.IBookRepository
import com.twitty.feature.bookreport.navigation.BookReportRoute
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.BookSearchCriteria
import com.twitty.model.Tag
import com.twitty.model.emptyBook
import com.twitty.model.emptyBookReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BookReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookReportRepository: IBookReportRepository,
    private val bookRepository: IBookRepository,
) : ViewModel() {
    val bookReportId: Int = savedStateHandle.toRoute<BookReportRoute>().id

    private val _detailBook = MutableStateFlow<Book>(emptyBook)
    val detailBook: StateFlow<Book> = _detailBook

    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    val bookList: StateFlow<List<Book>> = _bookList

    private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
    val tagList: StateFlow<List<Tag>> = _tagList

    private val _bookReport = MutableStateFlow(emptyBookReport)
    val bookReport: StateFlow<BookReport> = _bookReport

    private val _bookReportList = MutableStateFlow<List<BookReport>>(emptyList())
    val bookReportList: StateFlow<List<BookReport>> = _bookReportList

    init {
        Timber.d("BookReportViewModel init: $bookReportId")
        getAllTags()
        fetchBookReportList()
    }

    fun searchBooks(bookSearchCriteria: BookSearchCriteria) {
        viewModelScope.launch {
            bookRepository.searchBooks(bookSearchCriteria)
                .collect { book ->
                    when {
                        bookSearchCriteria.title != null -> _bookList.value = book
                        else -> _detailBook.value =
                            book.firstOrNull() ?: bookList.value.find { it.isbn == bookSearchCriteria.isbn } ?: emptyBook
                    }
                }
        }
    }

    private fun getAllTags() {
        viewModelScope.launch {
            bookReportRepository.getAllTags().collect { tags ->
                _tagList.value = tags
            }
        }
    }

    private fun fetchBookReportList() {
        viewModelScope.launch {
            bookReportRepository.fetchBookReports().collect { bookReport ->
                _bookReportList.value = bookReport
            }
        }
    }

    fun fetchBookReport(bookReportId: Int) {
        viewModelScope.launch {
            _bookReport.value = bookReportRepository.fetchBookReport(bookReportId)
        }
    }

    fun addBookReportTag(tagId: Int) {
        tagList.value.find { it.id == tagId }?.let { tag ->
            _bookReport.value = bookReportRepository.addTag(bookReport.value, tag)
        }
    }

    fun removeBookReportTag(tagId: Int) {
        _bookReport.value = bookReportRepository.removeTag(bookReport.value, tagId)
    }

    fun saveBookReport(bookReport: BookReport) {
        viewModelScope.launch {
            bookReportRepository.saveBookReport(bookReport)
        }
    }

    fun saveBook(book: Book) {
        viewModelScope.launch {
            bookReportRepository.saveBook(book)
        }
    }
}