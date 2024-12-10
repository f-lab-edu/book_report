package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.BookDao
import com.towitty.bookreport.data.network.FakeBookDao
import com.towitty.bookreport.data.network.FakeBookRemoteDataSource
import com.towitty.bookreport.data.network.IBookDataSource
import com.towitty.bookreport.data.network.model.NetworkBook
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookRepositoryTest {

    private lateinit var bookRemoteDataSource: IBookDataSource
    private lateinit var bookDao: BookDao
    private lateinit var bookRepository: BookRepository

    @Before
    fun setUp() {
        bookRemoteDataSource = FakeBookRemoteDataSource()
        bookDao = FakeBookDao()
        bookRepository = BookRepository(
            bookRemoteDataSource = bookRemoteDataSource,
            bookDao = bookDao
        )
    }

    @Test
    fun getBookSearch_WhenSearchedBooksExist_ShouldReturnMatchingBooks() = runBlocking {
        val androidQuery = "안드로이드"
        val kotlinQuery = "코틀린"
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkSearchBook.bookList as MutableList<NetworkBook>).add(
            NetworkBook(
                title = androidQuery,
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "1234",
                description = ""
            )
        )
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkSearchBook.bookList as MutableList<NetworkBook>).add(
            NetworkBook(
                title = kotlinQuery,
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "5678",
                description = ""
            )
        )

        val androidBook = bookRepository.searchBooks(androidQuery)
        val kotlinBook = bookRepository.searchBooks(kotlinQuery)

        assert(androidBook.isEmpty().not())
        assert(kotlinBook.isEmpty().not())
        assertEquals(1, androidBook.size)
    }

    @Test
    fun getBookSearch_WhenNoMatchingSearchedBooks_ShouldReturnEmptyList() = runBlocking {
        val query = "Kotlin"
        val androidQuery = "안드로이드"
        val kotlinQuery = "코틀린"
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkSearchBook.bookList as MutableList<NetworkBook>).add(
            NetworkBook(
                title = androidQuery,
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "1234",
                description = ""
            )
        )
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkSearchBook.bookList as MutableList<NetworkBook>).add(
            NetworkBook(
                title = kotlinQuery,
                link = "",
                image = "",
                author = "",
                price = "",
                publisher = "",
                pubDate = "",
                isbn = "5678",
                description = ""
            )
        )

        val book = bookRepository.searchBooks(query)

        assert(book.isEmpty())
    }
}