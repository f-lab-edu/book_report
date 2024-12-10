package com.towitty.bookreport.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.database.model.asTag
import com.towitty.bookreport.data.repository.IBookReportRepository
import com.towitty.bookreport.data.repository.IBookRepository
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.data.repository.model.emptyBookReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookReportViewModel @Inject constructor(
    private val bookReportRepository: IBookReportRepository,
    private val bookRemoteRepository: IBookRepository,
) : ViewModel() {
    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    val bookList: StateFlow<List<Book>> = _bookList

    private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
    val tagList: StateFlow<List<Tag>> = _tagList

    private val _bookReport = MutableStateFlow(emptyBookReport)
    val bookReport: StateFlow<BookReport> = _bookReport

    private val _bookReportList = MutableStateFlow<List<BookReportEntity>>(emptyList())
    val bookReportList: StateFlow<List<BookReportEntity>> = _bookReportList

    private val _foundBook = MutableStateFlow(emptyBook)
    val foundBook: StateFlow<Book> = _foundBook

    init {
        getAllTags()
        fetchBookReportList()
    }

    /**
     * Book
     */
    fun searchBooks(query: String) {
        viewModelScope.launch {
            _bookList.value = bookRemoteRepository.searchBooks(query)
        }
    }

    fun findBookByIsbn(isbn: String) {
        viewModelScope.launch {
            _foundBook.value = bookRemoteRepository.findBookByIsbn(isbn)
        }
    }

    /**
     * Tag
     */
    private fun getAllTags() {
        viewModelScope.launch {
            bookReportRepository.getAllTags().collect { tags ->
                _tagList.value = tags.map(TagEntity::asTag)
            }
        }
    }

    /**
     * BookReport
     */
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