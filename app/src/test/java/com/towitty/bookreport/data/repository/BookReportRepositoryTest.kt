package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.FakeBookReportDao
import com.towitty.bookreport.data.database.FakeTagDao
import com.towitty.bookreport.data.network.FakeBookDao
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.BookReport
import com.towitty.bookreport.data.repository.model.Tag
import com.towitty.bookreport.data.repository.model.emptyBook
import com.towitty.bookreport.util.getCurrentDateTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BookReportRepositoryTest {

    private lateinit var bookDao: FakeBookDao
    private lateinit var tagDao: FakeTagDao
    private lateinit var bookReportDao: FakeBookReportDao
    private lateinit var bookReportRepository: BookReportRepository

    @Before
    fun setUp() {
        bookDao = FakeBookDao(mutableListOf())
        tagDao = FakeTagDao(mutableListOf())
        bookReportDao = FakeBookReportDao(mutableListOf())

        bookReportRepository = BookReportRepository(
            bookDao = bookDao,
            tagDao = tagDao,
            bookReportDao = bookReportDao
        )
    }

    @Test
    fun fetchBookReport_shouldFetchBookReport() = runTest {
        // Given
        val bookReport = BookReport(
            id = 1,
            title = "Test Title",
            content = "Test Content",
            date = getCurrentDateTime(),
            isFavorite = false,
            book = emptyBook,
            tags = listOf()
        )
        // TODO -yjkim 2024-12-06 00:10

        bookReportRepository.fetchBookReport(1)

        // When
        val fetchedBookReport = bookReportRepository.fetchBookReport(1)

        // Then
        assertEquals(bookReport, fetchedBookReport)
    }

    @Test
    fun saveBookReport_shouldSaveBookReport() = runTest {
        // Given
        val bookReport = BookReport(
            id = 3,
            title = "Test Title",
            content = "Test Content",
            date = getCurrentDateTime(),
            isFavorite = false,
            book = emptyBook,
            tags = listOf()
        )

        // When
        bookReportRepository.saveBookReport(bookReport)

        // Then
        val fetchedBookReport = bookReportRepository.fetchBookReport(3)
        assertEquals(bookReport, fetchedBookReport)
    }

    @Test
    fun saveBook_shouldSaveBook() = runTest {
        // Given
        val book = Book(
            id =1,
            isbn = "Test isbn",
            title = "Test title",
            author = "Test author",
            link = "Test link",
            image = "Test image",
            description = "Test description",
            price = "Test price",
            pubDate = "Test pubDate",
            publisher = "Test publisher",
            isFavorite = false
        )

        //When
        bookReportRepository.saveBook(book)

        // Then
        val fetchedBook = bookDao.fetchBookById(3)
        assertEquals(book, fetchedBook)
    }

    @Test
    fun addTag_shouldAddTagToBookReport() {
        // Given
        val bookReport = BookReport(
            id = 3,
            title = "Test Title",
            content = "Test Content",
            date = getCurrentDateTime(),
            isFavorite = false,
            book = Book(
                id =1,
                isbn = "Test isbn",
                title = "Test title",
                author = "Test author",
                link = "Test link",
                image = "Test image",
                description = "Test description",
                price = "Test price",
                pubDate = "Test pubDate",
                publisher = "Test publisher",
                isFavorite = false
            ),
            tags = listOf(Tag(1, "Test Tag", -1))
        )
        val newTag = Tag(2, "New Tag", -1)

        // When
        val updatedBookReport = bookReportRepository.addTag(bookReport, newTag)

        // Then
        assertEquals(bookReport.tags + newTag, updatedBookReport.tags)
    }

    @Test
    fun removeTag_shouldRemoveTagFromBookReport() {
        // Given
        val tag = Tag(1, "Test Tag", -1)
        val bookReport = BookReport(
            id = 1,
            title = "Test Title",
            content = "Test Content",
            date = getCurrentDateTime(),
            isFavorite = false,
            book = Book(
                id =1,
                isbn = "Test isbn",
                title = "Test title",
                author = "Test author",
                link = "Test link",
                image = "Test image",
                description = "Test description",
                price = "Test price",
                pubDate = "Test pubDate",
                publisher = "Test publisher",
                isFavorite = false
            ),
            tags = listOf(tag)
        )

        // When
        val updatedBookReport = bookReportRepository.removeTag(bookReport, tag.id)

        // Then
        assertTrue(updatedBookReport.tags.find { it.id == tag.id } == null)
    }
}