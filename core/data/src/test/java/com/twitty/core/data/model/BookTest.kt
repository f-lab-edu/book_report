package com.twitty.core.data.model

import com.twitty.database.model.BookEntity
import com.twitty.model.Book
import com.twitty.network.model.NetworkBook
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class BookTest {
    lateinit var book: Book
    lateinit var bookEntity: BookEntity
    lateinit var networkBook: NetworkBook

    @Before
    fun setUp() {
        book = Book(
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

        bookEntity = BookEntity(
            id = 123L,
            isbn = "1234567890",
            title = "Android Developer",
            author = "kim young june",
            publisher = "google",
            pubDate = "1996-03-29",
            description = "Android developers thinking about what they need",
            image = "https://example.com/image.jpg",
            link = "https://example.com/link",
            price = "99.99",
            isFavorite = true
        )

        networkBook = NetworkBook(
            isbn = "1234567890",
            title = "Android Developer",
            author = "kim young june",
            publisher = "google",
            pubDate = "1996-03-29",
            description = "Android developers thinking about what they need",
            image = "https://example.com/image.jpg",
            link = "https://example.com/link",
            price = "99.99",
        )
    }

    @Test
    fun bookMapsToNetworkBook() {
        val networkBook = book.asNetworkBook()

        Assert.assertEquals("Android Developer", networkBook.title)
        Assert.assertEquals("https://example.com/link", networkBook.link)
        Assert.assertEquals("https://example.com/image.jpg", networkBook.image)
        Assert.assertEquals("kim young june", networkBook.author)
        Assert.assertEquals("99.99", networkBook.price)
        Assert.assertEquals("google", networkBook.publisher)
        Assert.assertEquals("1996-03-29", networkBook.pubDate)
        Assert.assertEquals("1234567890", networkBook.isbn)
        Assert.assertEquals(
            "Android developers thinking about what they need",
            networkBook.description
        )
    }

    @Test
    fun bookMapsToBookEntity() {
        val bookEntity = book.asEntity()

        Assert.assertEquals(123L, bookEntity.id)
        Assert.assertEquals("Android Developer", bookEntity.title)
        Assert.assertEquals("https://example.com/link", this.bookEntity.link)
        Assert.assertEquals("https://example.com/image.jpg", this.bookEntity.image)
        Assert.assertEquals("kim young june", this.bookEntity.author)
        Assert.assertEquals("99.99", this.bookEntity.price)
        Assert.assertEquals("google", this.bookEntity.publisher)
        Assert.assertEquals("1996-03-29", this.bookEntity.pubDate)
        Assert.assertEquals("1234567890", this.bookEntity.isbn)
        Assert.assertEquals(
            "Android developers thinking about what they need",
            this.bookEntity.description
        )
        Assert.assertEquals(true, bookEntity.isFavorite)
    }

    @Test
    fun networkBookMapsToBook() {
        var book = networkBook.asBook()

        Assert.assertEquals(0L, book.id)

        Assert.assertEquals("Android Developer", book.title)
        Assert.assertEquals("https://example.com/link", book.link)
        Assert.assertEquals("https://example.com/image.jpg", book.image)
        Assert.assertEquals("kim young june", book.author)
        Assert.assertEquals("99.99", book.discount)
        Assert.assertEquals("google", book.publisher)
        Assert.assertEquals("1996-03-29", book.pubdate)
        Assert.assertEquals("1234567890", book.isbn)
        Assert.assertEquals(
            "Android developers thinking about what they need",
            this.bookEntity.description
        )

        Assert.assertEquals(false, book.isFavorite)

        book = networkBook.asBook(isFavorite = true)
        Assert.assertEquals(true, book.isFavorite)
    }

    @Test
    fun bookEntityMapsToBook() {
        val book = bookEntity.asBook()

        Assert.assertEquals(123L, book.id)
        Assert.assertEquals("Android Developer", book.title)
        Assert.assertEquals("https://example.com/link", book.link)
        Assert.assertEquals("https://example.com/image.jpg", book.image)
        Assert.assertEquals("kim young june", book.author)
        Assert.assertEquals("99.99", book.discount)
        Assert.assertEquals("google", book.publisher)
        Assert.assertEquals("1996-03-29", book.pubdate)
        Assert.assertEquals("1234567890", book.isbn)
        Assert.assertEquals(
            "Android developers thinking about what they need",
            this.bookEntity.description
        )
    }
}