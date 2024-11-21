package com.towitty.bookreport.ui

import com.towitty.bookreport.data.database.FakeTagLocalRepository
import com.towitty.bookreport.data.database.model.TagEntity
import com.towitty.bookreport.data.network.FakeBookRemoteRepository
import com.towitty.bookreport.data.network.model.BookItem
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
            BookItem(
                title = "Kotlin",
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "1234567890",
                description = ""
            ), BookItem(
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
        val bookRepository = FakeBookRemoteRepository(books)

        val tags = mutableListOf(
            TagEntity(
                id = 1, name = "Android", color = 0
            ), TagEntity(
                id = 2, name = "Kotlin", color = 0
            )
        )
        val tagRepository = FakeTagLocalRepository(tags)
        viewModel = BookReportViewModel(bookRepository, tagRepository)
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
        val book = viewModel.findBookByIsbn("1234567890")
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
        viewModel.addSelectedTag(1)

        assertEquals(1, viewModel.addedTagList.value.size)
        assertTrue(viewModel.addedTagList.value[0].id == 1 && viewModel.addedTagList.value[0].name == "Android")
    }

    @Test
    fun removeAddedTag_ShouldRemoveTagFromAddedTagList() = runTest {
        viewModel.addSelectedTag(1)
        assertEquals(1, viewModel.addedTagList.value.size)
        assertTrue( viewModel.addedTagList.value.any { it.id == 1 } )

        viewModel.removeAddedTag(1)
        assertEquals(0, viewModel.addedTagList.value.size)
        assertTrue( viewModel.addedTagList.value.none { it.id == 1 } )
    }

}