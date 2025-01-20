package com.twitty.core.data.repository

import com.twitty.core.data.model.asEntity
import com.twitty.core.data.testdoubles.FakeBookDao
import com.twitty.core.data.testdoubles.FakeBookReportDao
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.emptyBook
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FavoritesRepositoryTest {

    lateinit var subject: FavoritesRepository
    lateinit var bookDao: FakeBookDao
    lateinit var bookReportDao: FakeBookReportDao

    @Before
    fun setUp() {
        bookDao = FakeBookDao()
        bookReportDao = FakeBookReportDao()
        subject = FavoritesRepository(bookDao, bookReportDao)
    }

    @Test
    fun `fetchFavoriteBooks 즐겨찾기한 책들만 반환`() = runTest {
        val books = listOf(
            Book(
                id = 1,
                title = "title",
                link = "link",
                author = "author",
                description = "description",
                image = "image",
                discount = "discount",
                pubdate = "pubdate",
                publisher = "publisher",
                isbn = "isbn",
                isFavorite = true,
            ),
            Book(
                id = 2,
                title = "second title",
                link = "second link",
                author = "second author",
                description = "second description",
                image = "second image",
                discount = "second discount",
                pubdate = "second pubdate",
                publisher = "second publisher",
                isbn = "second isbn",
                isFavorite = false,
            )
        )

        books.forEach { book ->
            bookDao.insertBook(book.asEntity())
        }

        val favoriteBooks = subject.fetchFavoriteBooks().first()

        Assert.assertTrue(favoriteBooks.size == 1)
        Assert.assertFalse(favoriteBooks.contains(books[1]))
        Assert.assertEquals(books[0], favoriteBooks[0])
    }

    @Test
    fun `fetchFavoriteBookReports 즐겨찾기 하지 않은 독후감은 반환하지 않음`() = runTest {
        val bookReports = listOf(
            BookReport(
                id = 1,
                book = emptyBook,
                title = "title",
                content = "content",
                date = "date",
                tags = emptyList(),
                isFavorite = true,
            ),
            BookReport(
                id = 2,
                book = emptyBook,
                title = "second title",
                content = "second content",
                date = "second date",
                tags = emptyList(),
                isFavorite = false,
            )
        )

        bookReports.forEach { bookReport ->
            bookReportDao.insertBookReport(bookReport.asEntity())
        }

        val favoriteBookReports = subject.fetchFavoriteBookReports().first()

        Assert.assertTrue(favoriteBookReports.size == 1)
        Assert.assertFalse(favoriteBookReports.contains(bookReports[1]))
        Assert.assertEquals(favoriteBookReports[0], bookReports[0])
    }

}