package com.twitty.core.data.model

import com.twitty.database.model.BookReportEntity
import com.twitty.model.Book
import com.twitty.model.BookReport
import com.twitty.model.Tag
import com.twitty.model.emptyBook
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BookReportTest {
    lateinit var bookReport: BookReport
    lateinit var bookReportEntity: BookReportEntity
    lateinit var book: Book
    lateinit var tags: List<Tag>

    @Before
    fun setUp() {
        book = Book(
            id = 321L,
            isbn = "1234567890",
            title = "Android Developer",
            author = "kim young june",
            publisher = "google",
            pubdate = "1996-03-29",
            description = "Android developers thinking about what they need",
            image = "https://example.com/image.jpg",
            link = "https://example.com/link",
            discount = "99.99",
            isFavorite = false
        )

        tags = listOf(
            Tag(1, "Android", 110000),
            Tag(2, "Kotlin", 220000),
        )

        bookReport = BookReport(
            id = 123L,
            book = book,
            title = "leaning Android",
            content = "test code with Android",
            date = "2025-01-01",
            tags = tags,
            isFavorite = true
        )

        bookReportEntity = BookReportEntity(
            id = 123L,
            title = "leaning Android",
            content = "test code with Android",
            date = "2025-01-01",
            isFavorite = true,
            bookId = 321L,
            tagIds = tags.map { it.id }
        )
    }

    @Test
    fun bookReportMapsToEntity() {
        val bookReportEntity = bookReport.asEntity()

        Assert.assertEquals(123L, bookReportEntity.id)
        Assert.assertEquals("leaning Android", bookReportEntity.title)
        Assert.assertEquals("test code with Android", bookReportEntity.content)
        Assert.assertEquals("2025-01-01", bookReportEntity.date)
        Assert.assertEquals(true, bookReportEntity.isFavorite)
        Assert.assertEquals(321L, bookReportEntity.bookId)

        Assert.assertNotEquals(tags, bookReportEntity.tagIds)
        val tags = bookReport.tags.map { it.id }
        Assert.assertEquals(tags, bookReportEntity.tagIds)

    }

    @Test
    fun bookEntityMapsToBookReport() {
        var bookReport = bookReportEntity.asBookReport(book, tags)

        Assert.assertEquals(123L, bookReport.id)
        Assert.assertEquals("leaning Android", bookReport.title)
        Assert.assertEquals("test code with Android", bookReport.content)
        Assert.assertEquals("2025-01-01", bookReport.date)
        Assert.assertEquals(true, bookReport.isFavorite)
        Assert.assertEquals(book, bookReport.book)
        Assert.assertEquals(tags, bookReport.tags)

        bookReport = bookReportEntity.asBookReport()
        val emptyTags = emptyList<Tag>()
        Assert.assertEquals(emptyBook, bookReport.book)
        Assert.assertEquals(emptyTags, bookReport.tags)
    }

}