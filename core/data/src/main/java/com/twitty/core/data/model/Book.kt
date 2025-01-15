package com.twitty.core.data.model

import com.twitty.database.model.BookEntity
import com.twitty.model.Book
import com.twitty.network.model.NetworkBook

fun Book.asEntity(): BookEntity {
    return BookEntity(
        id = id,
        isbn = isbn,
        title = title,
        author = author,
        price = discount,
        publisher = publisher,
        pubDate = pubdate,
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
        price = discount,
        publisher = publisher,
        pubDate = pubdate,
        isbn = isbn,
        description = description
    )
}

fun BookEntity.asBook(): Book {
    return Book(
        id = id,
        isbn = isbn,
        title = title,
        author = author,
        publisher = publisher,
        pubdate = pubDate,
        description = description,
        image = image,
        link = link,
        discount = price,
        isFavorite = isFavorite
    )
}

fun BookEntity.asNetworkBook(): NetworkBook {
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

fun NetworkBook.asBook(isFavorite: Boolean = false) = Book(
    id = 0,
    isbn = isbn,
    title = title,
    author = author,
    publisher = publisher,
    pubdate = pubDate,
    description = description,
    image = image,
    discount = price,
    link = link,
    isFavorite = isFavorite,
)

fun NetworkBook.asBookEntity(isFavorite: Boolean) = BookEntity(
    isbn = isbn,
    title = title,
    author = author,
    link = link,
    image = image,
    description = description,
    price = price,
    publisher = publisher,
    pubDate = pubDate,
    isFavorite = isFavorite,
)