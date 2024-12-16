package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.network.FakeBookDao
import com.towitty.bookreport.data.network.FakeBookRemoteDataSource
import com.twitty.core.data.repository.BookRepository
import com.twitty.network.model.NetworkBook
import com.twitty.network.retrofit.IBookDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class BookRepositoryTest {

    private lateinit var bookRemoteDataSource: IBookDataSource
    private lateinit var bookDao: com.twitty.database.dao.BookDao
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
    fun getBookSearch_WhenSearchedBooksExist_ShouldReturnMatchingBooks(): Unit = runBlocking {
        val androidQuery = "안드로이드"
        val kotlinQuery = "코틀린"
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkBookContainer.bookList as MutableList<NetworkBook>).add(
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
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkBookContainer.bookList as MutableList<NetworkBook>).add(
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

//        val androidBook = bookRepository.searchBooks()
//        val kotlinBook = bookRepository.searchBooks(kotlinQuery)
//
//        assert(androidBook.isEmpty().not())
//        assert(kotlinBook.isEmpty().not())
//        assertEquals(1, androidBook.size)
    }

    @Test
    fun getBookSearch_WhenNoMatchingSearchedBooks_ShouldReturnEmptyList(): Unit = runBlocking {
        val query = "Kotlin"
        val androidQuery = "안드로이드"
        val kotlinQuery = "코틀린"
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkBookContainer.bookList as MutableList<NetworkBook>).add(
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
        ((bookRemoteDataSource as FakeBookRemoteDataSource).networkBookContainer.bookList as MutableList<NetworkBook>).add(
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

//        val book = bookRepository.searchBooks(query)
//
//        assert(book.isEmpty())
    }
}