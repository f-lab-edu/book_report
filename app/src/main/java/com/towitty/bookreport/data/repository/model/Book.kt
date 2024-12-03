package com.towitty.bookreport.data.repository.model

import com.towitty.bookreport.data.database.model.BookEntity
import com.towitty.bookreport.data.network.model.NetworkBook

val emptyBook = Book(
    id = 0,
    isbn = 0,
    title = "",
    author = "",
    image = "",
    description = "",
    price = 0,
    link = "",
    publishedDate = "",
    publisher = "",
    isFavorite = false
)

data class Book(
    val id: Int,
    val isbn: Int,
    val title: String,
    val author: String,
    val image: String,
    val description: String,
    val price: Int,
    val link: String,
    val publishedDate: String,
    val publisher: String,
    val isFavorite: Boolean
)

fun Book.asEntity(): BookEntity {
    return BookEntity(
        id = id,
        isbn = isbn,
        title = title,
        author = author,
        price = price,
        publisher = publisher,
        publishedDate = publishedDate,
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
        price = price.toString(),
        publisher = publisher,
        pubDate = publishedDate,
        isbn = isbn.toString(),
        description = description
    )
}
