package com.towitty.bookreport.ui

import com.towitty.bookreport.data.database.FakeBookReportDao
import com.towitty.bookreport.data.database.FakeTagDao
import com.towitty.bookreport.data.database.model.BookReportEntity
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.network.FakeBookDao
import com.towitty.bookreport.data.repository.FakeBooKReportRepository
import com.towitty.bookreport.data.repository.FakeBookRemoteRepository
import com.towitty.bookreport.data.repository.model.Book
import com.towitty.bookreport.data.repository.model.asEntity
import com.towitty.bookreport.presentation.ui.BookReportViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BookReportViewModelTest {

    private lateinit var viewModel: BookReportViewModel

    @Before
    fun setup() {
        val books = mutableListOf(
            Book(
                id = 0,
                title = "Kotlin",
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "1234567890",
                description = ""
            ), Book(
                id = 1,
                title = "Kotlin in Action",
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "0987654321",
                description = ""
            )
        )

        val tags = mutableListOf(
            TagEntity(
                id = 1, name = "Android", color = 0
            ), TagEntity(
                id = 2, name = "Kotlin", color = 0
            )
        )

        val bookReports = mutableListOf(
            BookReportEntity(
                id = 0,
                title = "Kotlin",
                content = "",
                date = "",
                isFavorite = false,
                bookId = 0,
                tagIds = mutableListOf(1)
            ), BookReportEntity(
                id = 1,
                title = "Kotlin in Action",
                content = "",
                date = "",
                isFavorite = false,
                bookId = 1,
                tagIds = mutableListOf(1, 2)
            )
        )

        val bookRepository = FakeBookRemoteRepository(books)
        val bookEntities = books.map { it.asEntity() }.toMutableList()
        val fakeBookReportRepository = FakeBooKReportRepository(
            tagDao = FakeTagDao(tags),
            bookDao = FakeBookDao(bookEntities),
            bookReportDao = FakeBookReportDao(bookReports),
        )

        viewModel = BookReportViewModel(
            bookRemoteRepository = bookRepository,
            bookReportRepository = fakeBookReportRepository
        )
    }

    @Test
    fun searchBooks_ShouldUpdateBookList() = runTest {
        viewModel.searchBooks("Kotlin")

        assertEquals(2, viewModel.bookList.value.size)

        assertTrue(viewModel.bookList.value[0].title.contains("Kotlin"))
        assertTrue("Kotlin", viewModel.bookList.value[1].title.contains("Kotlin"))
    }

    @Test
    fun searchBooks_ShouldNotUpdateBookList() = runTest {
        viewModel.searchBooks("Java")

        assertEquals(0, viewModel.bookList.value.size)
    }

    @Test
    fun findBookByIsbn_ShouldReturnCorrectBook() {
        viewModel.findBookByIsbn("1234567890")
        val book = viewModel.foundBook.value
        assertEquals("Kotlin", book.title)
        assertEquals("1234567890", book.isbn)
    }

    @Test
    fun getAllTags_ShouldUpdateTagList() = runTest {

        assertEquals(2, viewModel.tagList.value.size)
        assertTrue(viewModel.tagList.value[0].id == 1 && viewModel.tagList.value[0].name == "Android")
        assertTrue(viewModel.tagList.value[1].id == 2 && viewModel.tagList.value[1].name == "Kotlin")
    }

    @Test
    fun addSelectedTag_ShouldAddTagToAddedTagList() = runTest {
        viewModel.addBookReportTag(1)
    }

    @Test
    fun removeAddedTag_ShouldRemoveTagFromBookReportTagList() = runTest {
        viewModel.addBookReportTag(1)

    }

}