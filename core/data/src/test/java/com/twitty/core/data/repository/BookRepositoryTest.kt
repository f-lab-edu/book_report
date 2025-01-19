package com.twitty.core.data.repository

import com.twitty.core.data.testdoubles.FakeBookDao
import com.twitty.core.data.testdoubles.FakeBookRemoteDataSource
import com.twitty.model.Book
import com.twitty.model.BookSearchCriteria
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BookRepositoryTest {

    private lateinit var subject: BookRepository

    // remote source 는 src/main/asset/book.json 에서 가져옴
    private lateinit var bookRemoteDataSource: FakeBookRemoteDataSource
    private lateinit var bookDao: FakeBookDao

    @Before
    fun setUp() {
        bookRemoteDataSource = FakeBookRemoteDataSource()
        bookDao = FakeBookDao()
        subject = BookRepository(
            bookRemoteDataSource = bookRemoteDataSource, bookLocalDataSource = bookDao
        )
    }

    @Test
    fun `searchBooks 제목으로 검색하는 경우 제목이 포함된 값을 반환`() = runTest {
        val title = "Android"
        val bookSearchCriteria = BookSearchCriteria(title = title)
        val searchedBooks = subject.searchBooks(bookSearchCriteria).first()

        Assert.assertTrue(searchedBooks.isNotEmpty())
        Assert.assertTrue(searchedBooks.all { book ->
            book.title.contains(title, ignoreCase = true)
        })
        Assert.assertTrue(searchedBooks.size == 10)

    }

    @Test
    fun `searchBooks 아이디로 검색하는 경우 아이디가 일치하는 책 반환`() = runTest {
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
        subject.saveBook(book)

        val bookSearchCriteria = BookSearchCriteria(id = book.id)
        val searchedBooks = subject.searchBooks(bookSearchCriteria).first()

        Assert.assertTrue(searchedBooks.isNotEmpty())
        Assert.assertTrue(searchedBooks.size == 1)
        Assert.assertEquals(book, searchedBooks.first())
    }

    @Test
    fun `searchBooks ISBN으로 검색하는 경우 ISBN이 일치하는 책 반환`() = runTest {
        val book = Book(
            id = 2,
            title = "Android (App Development & Programming Guide: Learn In A Day!)",
            link = "https://search.shopping.naver.com/book/catalog/32510410432",
            image = "https://shopping-phinf.pstatic.net/main_3251041/32510410432.20220521201217.jpg",
            author = "",
            discount = "47740",
            publisher = "Lulu Press",
            pubdate = "20151209",
            isbn = "9781329652939",
            description = "Learn to Program Android Apps - in Only a Day! \n\nAndroid: Programming Guide: Android App Development - Learn in a Day teaches you everything you need to become an Android App Developer from scratch. It explains how you can get started by installing Android Studio and learning to use the Android SDK Manager.\n\nCan you really create an app in just a day?\n\nYes, you can! With Android: Programming Guide: Android App Development - Learn in a Day, you'll learn to create \"OMG Andriod\"."
        )
        subject.saveBook(book)

        val bookSearchCriteria = BookSearchCriteria(isbn = book.isbn)
        val searchedBooks = subject.searchBooks(bookSearchCriteria).first()

        Assert.assertTrue(searchedBooks.isNotEmpty())
        Assert.assertTrue(searchedBooks.contains(book))
    }

    @Test
    fun `saveBook 동일한 isbn이 존재하지 않는 경우 책이 추가됨`() = runTest {
        val book = Book(
            id = 2,
            title = "Android (App Development & Programming Guide: Learn In A Day!)",
            link = "https://search.shopping.naver.com/book/catalog/32510410432",
            image = "https://shopping-phinf.pstatic.net/main_3251041/32510410432.20220521201217.jpg",
            author = "",
            discount = "47740",
            publisher = "Lulu Press",
            pubdate = "20151209",
            isbn = "9781329652939",
            description = "Learn to Program Android Apps - in Only a Day! \n\nAndroid: Programming Guide: Android App Development - Learn in a Day teaches you everything you need to become an Android App Developer from scratch. It explains how you can get started by installing Android Studio and learning to use the Android SDK Manager.\n\nCan you really create an app in just a day?\n\nYes, you can! With Android: Programming Guide: Android App Development - Learn in a Day, you'll learn to create \"OMG Andriod\"."
        )
        val bookSearchCriteria = BookSearchCriteria(isbn = book.isbn)
        var savedBooks = subject.searchBooks(bookSearchCriteria).first()

        Assert.assertTrue(savedBooks.isEmpty())

        subject.saveBook(book)
        savedBooks = subject.searchBooks(bookSearchCriteria).first()

        Assert.assertTrue(savedBooks.isNotEmpty())

    }

    @Test
    fun `saveBook 동일한 isbn이 존재하는 경우 책의 값들을 업데이트`() = runTest {
        val oldBook = Book(
            id = 2,
            title = "Android (App Development & Programming Guide: Learn In A Day!)",
            link = "https://search.shopping.naver.com/book/catalog/32510410432",
            image = "https://shopping-phinf.pstatic.net/main_3251041/32510410432.20220521201217.jpg",
            author = "",
            discount = "47740",
            publisher = "Lulu Press",
            pubdate = "20151209",
            isbn = "9781329652939",
            description = "Learn to Program Android Apps - in Only a Day! \n\nAndroid: Programming Guide: Android App Development - Learn in a Day teaches you everything you need to become an Android App Developer from scratch. It explains how you can get started by installing Android Studio and learning to use the Android SDK Manager.\n\nCan you really create an app in just a day?\n\nYes, you can! With Android: Programming Guide: Android App Development - Learn in a Day, you'll learn to create \"OMG Andriod\"."
        )

        val newBook = Book(
            id = 2,
            title = "Update title",
            link = "Update link",
            image = "Update image",
            author = "Update author",
            discount = "Update discount",
            publisher = "Update publisher",
            pubdate = "Update pubdate",
            isbn = "9781329652939",
            description = "This has been updated."
        )

        subject.saveBook(oldBook)
        val oldBookSearchCriteria = BookSearchCriteria(isbn = oldBook.isbn)
        val oldBooks = subject.searchBooks(oldBookSearchCriteria).first()

        Assert.assertTrue(oldBooks.size == 1)
        Assert.assertEquals(oldBook, oldBooks.first())

        subject.saveBook(newBook)
        val updateBookCriteria = BookSearchCriteria(isbn = newBook.isbn)
        val updatedBooks = subject.searchBooks(updateBookCriteria).first()

        Assert.assertTrue(updatedBooks.size == 1)
        Assert.assertEquals(newBook, updatedBooks.first())
        Assert.assertEquals(oldBook.id, updatedBooks.first().id)

        Assert.assertFalse(oldBook.title == updatedBooks.first().title)
        Assert.assertFalse(oldBook.link == updatedBooks.first().link)
        Assert.assertFalse(oldBook.image == updatedBooks.first().image)
        Assert.assertFalse(oldBook.author == updatedBooks.first().author)
        Assert.assertFalse(oldBook.discount == updatedBooks.first().discount)
        Assert.assertFalse(oldBook.publisher == updatedBooks.first().publisher)
        Assert.assertFalse(oldBook.title == updatedBooks.first().title)
        Assert.assertFalse(oldBook.pubdate == updatedBooks.first().pubdate)
        Assert.assertFalse(oldBook.description == updatedBooks.first().description)

    }
}