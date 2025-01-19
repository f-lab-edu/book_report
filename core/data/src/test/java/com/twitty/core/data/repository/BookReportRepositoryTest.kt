package com.twitty.core.data.repository

import com.twitty.core.data.model.asEntity
import com.twitty.core.data.model.asTag
import com.twitty.core.data.testdoubles.FakeBookDao
import com.twitty.core.data.testdoubles.FakeBookRemoteDataSource
import com.twitty.core.data.testdoubles.FakeBookReportDao
import com.twitty.core.data.testdoubles.FakeBookRepository
import com.twitty.core.data.testdoubles.FakeTagDao
import com.twitty.database.model.TagEntity
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.Tag
import com.twitty.model.emptyBook
import com.twitty.model.emptyBookReport
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BookReportRepositoryTest {

    lateinit var subject: BookReportRepository

    lateinit var bookRepository: FakeBookRepository
    lateinit var bookReportDao: FakeBookReportDao
    lateinit var tagDao: FakeTagDao

    @Before
    fun setUp() {
        bookRepository = FakeBookRepository(
            bookRemoteDataSource = FakeBookRemoteDataSource(),
            bookDao = FakeBookDao()
        )
        bookReportDao = FakeBookReportDao()
        tagDao = FakeTagDao()

        subject = BookReportRepository(bookRepository, tagDao, bookReportDao)
    }

    @Test
    fun `saveBookReport 동일한 ID가 존재하지 않는 경우 독후감 추가`() = runTest {
        val book = Book(
            id = 123L,
            isbn = "1234567890",
            title = "Android Developer",
            author = "kim young june",
            publisher = "google",
            pubdate = "1996-03-29",
            description = "Android developers thinking about what they need",
            image = "https://example.com/image.jpg",
            link = "https://example.com/link",
            discount = "99.99",
            isFavorite = true
        )
        bookRepository.saveBook(book)

        val tags = listOf(
            Tag(1, "Good", 100),
            Tag(2, "Bad", 200),
        )
        tags.forEach {
            tagDao.insertTag(it.asEntity())
        }

        val bookReport = BookReport(
            id = 1010L,
            book = book,
            title = "Learning Android",
            content = "This is a great book",
            tags = tags,
            date = "2025-01-01",
            isFavorite = true
        )

        var fetchedBookReport = subject.fetchBookReport(bookReport.id).first()

        Assert.assertEquals(emptyBookReport, fetchedBookReport)

        subject.saveBookReport(bookReport)
        fetchedBookReport = subject.fetchBookReport(bookReport.id).first()

        Assert.assertEquals(bookReport, fetchedBookReport)
    }

    @Test
    fun `saveBookReport 동일한 ID가 존재하는 경우 독후감 업데이트`() = runTest {
        val book = Book(
            id = 123L,
            isbn = "1234567890",
            title = "Android Developer",
            author = "kim young june",
            publisher = "google",
            pubdate = "1996-03-29",
            description = "Android developers thinking about what they need",
            image = "https://example.com/image.jpg",
            link = "https://example.com/link",
            discount = "99.99",
            isFavorite = true
        )
        bookRepository.saveBook(book)

        val tags = listOf(
            Tag(1, "Good", 100),
            Tag(2, "Bad", 200),
        )
        tags.forEach {
            tagDao.insertTag(it.asEntity())
        }

        val oldBookReport = BookReport(
            id = 1010L,
            book = book,
            title = "Learning Android",
            content = "This is a great book",
            tags = tags,
            date = "2025-01-01",
            isFavorite = true
        )

        val newBookReport = BookReport(
            id = 1010L,
            book = emptyBook,
            title = "Master Android",
            content = "This is a hard book",
            tags = emptyList(),
            date = "2025-01-02",
            isFavorite = false
        )

        subject.saveBookReport(oldBookReport)
        var fetchedBookReport = subject.fetchBookReport(oldBookReport.id).first()

        Assert.assertTrue(oldBookReport.id == fetchedBookReport.id)
        Assert.assertEquals(oldBookReport, fetchedBookReport)

        subject.saveBookReport(newBookReport)
        fetchedBookReport = subject.fetchBookReport(oldBookReport.id).first()

        Assert.assertEquals(oldBookReport.id, fetchedBookReport.id)
        Assert.assertEquals(newBookReport, fetchedBookReport)
    }

    @Test
    fun `fetchBookReport ID와 일치하는 독후감이 존재하는 경우 ID가 일치하는 독후감 반환`() = runTest {
        val bookReport = BookReport(
            id = 1010L,
            book = emptyBook,
            title = "Master Android",
            content = "This is a hard book",
            tags = emptyList(),
            date = "2025-01-02",
            isFavorite = false
        )

        subject.saveBookReport(bookReport)
        val fetchedBookReport = subject.fetchBookReport(bookReport.id).first()

        Assert.assertEquals(bookReport, fetchedBookReport)
    }

    @Test
    fun `fetchBookReport ID와 일치하는 독후감이 존재하지 않는 경우 빈 독후감 반환`() = runTest {
        val bookReport = BookReport(
            id = 1010L,
            book = emptyBook,
            title = "Master Android",
            content = "This is a hard book",
            tags = emptyList(),
            date = "2025-01-02",
            isFavorite = false
        )

        val fetchedBookReport = subject.fetchBookReport(bookReport.id).first()

        Assert.assertEquals(emptyBookReport, fetchedBookReport)
    }

    @Test
    fun `fetchBookReports 저장되어 있는 모든 독후감 반환`() = runTest {
        val firstBookReport = BookReport(
            id = 1L,
            book = emptyBook,
            title = "First",
            content = "first",
            tags = emptyList(),
            date = "2025-01-01",
            isFavorite = false
        )

        val secondBookReport = BookReport(
            id = 2L,
            book = emptyBook,
            title = "Second",
            content = "second",
            tags = emptyList(),
            date = "2025-01-01",
            isFavorite = false
        )

        subject.saveBookReport(firstBookReport)
        subject.saveBookReport(secondBookReport)
        val fetchedBookReports = subject.fetchBookReports().first()

        Assert.assertTrue(fetchedBookReports.size == 2)
        Assert.assertTrue(fetchedBookReports.contains(firstBookReport))
        Assert.assertTrue(fetchedBookReports.contains(secondBookReport))
    }

    @Test
    fun `fetchFavoriteBookReports 즐겨찾기한 독후감 모두 반환`() = runTest {
        val favoriteBookReport = BookReport(
            id = 1L,
            book = emptyBook,
            title = "First",
            content = "first",
            tags = emptyList(),
            date = "2025-01-01",
            isFavorite = true
        )

        val notFavoriteBookReport = BookReport(
            id = 2L,
            book = emptyBook,
            title = "Second",
            content = "second",
            tags = emptyList(),
            date = "2025-01-01",
            isFavorite = false
        )

        subject.saveBookReport(favoriteBookReport)
        subject.saveBookReport(notFavoriteBookReport)
        val fetchedBookReports = subject.fetchFavoriteBookReports().first()

        Assert.assertTrue(fetchedBookReports.size == 1)
        Assert.assertTrue(fetchedBookReports.contains(favoriteBookReport))
        Assert.assertFalse(fetchedBookReports.contains(notFavoriteBookReport))
    }

    @Test
    fun `fetchAllTags 모든 태그 반환`() = runTest {
        val tagEntities = listOf(
            TagEntity(1, "Good", 100),
            TagEntity(2, "Bad", 200),
        )
        val tag1 = tagEntities[0].asTag()
        val tag2 = tagEntities[1].asTag()

        tagEntities.forEach {
            tagDao.insertTag(it)
        }

        val fetchedTags = subject.fetchAllTags().first()

        Assert.assertTrue(fetchedTags.size == 2)
        Assert.assertTrue(fetchedTags.contains(tag1))
        Assert.assertTrue(fetchedTags.contains(tag2))
    }
}