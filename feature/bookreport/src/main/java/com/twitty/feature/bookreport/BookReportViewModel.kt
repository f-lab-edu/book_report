package com.twitty.feature.bookreport

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.twitty.core.data.repository.IBookReportRepository
import com.twitty.feature.bookreport.navigation.BookReportRoute
import com.twitty.model.BookReport
import com.twitty.model.BookSearchCriteria
import com.twitty.model.Tag
import com.twitty.model.emptyBookReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bookReportRepository: IBookReportRepository,
) : ViewModel() {
    private val bookReportId: Long = savedStateHandle.toRoute<BookReportRoute>().bookReportId
    private val bookIsbn: String = savedStateHandle.toRoute<BookReportRoute>().bookIsbn

    private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
    val tagList: StateFlow<List<Tag>> = _tagList

    private val _bookReport = MutableStateFlow(emptyBookReport)
    val bookReport: StateFlow<BookReport> = _bookReport

    init {
        fetchAllTags()
        fetchBookReport()
    }

    private fun fetchAllTags() {
        viewModelScope.launch {
            bookReportRepository.fetchAllTags().collect { tags ->
                _tagList.value = tags
            }
        }
    }

    private fun fetchBookReport() {
        viewModelScope.launch {
            if (bookReportId == 0L && bookIsbn != "-") {
                bookReportRepository.searchBooks(BookSearchCriteria(isbn = bookIsbn)).collect { books ->
                    _bookReport.value = books.first().let { book ->
                        emptyBookReport.copy(book = book)
                    }
                }
            } else if (bookReportId != 0L) {
                bookReportRepository.fetchBookReport(bookReportId).let { bookReport ->
                    _bookReport.value = bookReport
                }
            } else {
                _bookReport.value = emptyBookReport
            }
        }
    }

    fun addBookReportTag(tagId: Int) {
        tagList.value.find { it.id == tagId }?.let { tag ->
            _bookReport.value = _bookReport.value.copy(tags = _bookReport.value.tags + tag)
        }
    }

    fun removeBookReportTag(tagId: Int) {
        _bookReport.value = _bookReport.value.copy(tags = _bookReport.value.tags.filter { it.id != tagId })
    }

    fun saveBookReport() {
        viewModelScope.launch {
            bookReportRepository.saveBookReport(bookReport.value)
        }
    }
}