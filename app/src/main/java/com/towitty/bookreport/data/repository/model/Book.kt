package com.towitty.bookreport.data.repository.model

import com.towitty.bookreport.data.database.model.BookEntity
import com.towitty.bookreport.data.network.model.NetworkBook
import kotlinx.serialization.Serializable

val emptyBook = Book(
    id = 0,
    isbn = "0",
    title = "제목",
    author = "작가",
    image = "-",
    description = "-",
    price = "-",
    link = "-",
    pubDate = "-",
    publisher = "출판사",
    isFavorite = false
)

@Serializable
data class Book(
    val id: Int,
    val isbn: String,
    val title: String,
    val author: String,
    val image: String,
    val description: String,
    val price: String,
    val link: String,
    val pubDate: String,
    val publisher: String,
    val isFavorite: Boolean = false
)

fun Book.asEntity(): BookEntity {
    return BookEntity(
        id = id,
        isbn = isbn,
        title = title,
        author = author,
        price = price,
        publisher = publisher,
        pubDate = pubDate,
        description = description,
        image = image,
        link = link,
        isFavorite = isFavorite
    )
}

fun Book.asNetworkBook(): NetworkBook {
    return NetworkBook(
        title = title,
        link = link,
        image = image,
        author = author,
        price = price,
        publisher = publisher,
        pubDate = pubDate,
        isbn = isbn,
        description = description
    )
}
