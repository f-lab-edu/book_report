package com.towitty.bookreport.data.network

import com.towitty.bookreport.model.BookItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookRemoteRepositoryTest {

    private lateinit var bookRemoteDataSource: IBookDataSource
    private lateinit var bookRemoteRepository: BookRemoteRepository

    @Before
    fun setUp() {
        bookRemoteDataSource = FakeBookRemoteDataSource()
        bookRemoteRepository = BookRemoteRepository(bookRemoteDataSource)
    }

    @Test
    fun getBookSearch_WhenSearchedBooksExist_ShouldReturnMatchingBooks() = runBlocking {
        val androidQuery = "안드로이드"
        val kotlinQuery = "코틀린"
        ((bookRemoteDataSource as FakeBookRemoteDataSource).book.bookList as MutableList<BookItem>).add(
            BookItem(
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
        ((bookRemoteDataSource as FakeBookRemoteDataSource).book.bookList as MutableList<BookItem>).add(
            BookItem(
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

        val androidBook = bookRemoteRepository.searchBooks(androidQuery)
        val kotlinBook = bookRemoteRepository.searchBooks(kotlinQuery)

        assert(androidBook.isEmpty().not())
        assert(kotlinBook.isEmpty().not())
        assertEquals(1, androidBook.size)
    }

    @Test
    fun getBookSearch_WhenNoMatchingSearchedBooks_ShouldReturnEmptyList() = runBlocking {
        val query = "Kotlin"
        val androidQuery = "안드로이드"
        val kotlinQuery = "코틀린"
        ((bookRemoteDataSource as FakeBookRemoteDataSource).book.bookList as MutableList<BookItem>).add(
            BookItem(
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
        ((bookRemoteDataSource as FakeBookRemoteDataSource).book.bookList as MutableList<BookItem>).add(
            BookItem(
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

        val book = bookRemoteRepository.searchBooks(query)

        assert(book.isEmpty())
    }
}