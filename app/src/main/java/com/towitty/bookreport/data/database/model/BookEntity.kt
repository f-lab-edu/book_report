package com.towitty.bookreport.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.towitty.bookreport.data.network.model.NetworkBook
import com.towitty.bookreport.data.repository.model.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isbn: Int,
    val title: String,
    val author: String,
    val link: String,
    val image: String,
    val description: String,
    val price: Int,
    val publisher: String,
    val pubDate: String,
    val isFavorite: Boolean,
)

fun BookEntity.asBook(): Book {
    return Book(
        id = id,
        isbn = isbn,
        title = title,
        author = author,
        publisher = publisher,
        pubDate = pubDate,
        description = description,
        image = image,
        link = link,
        price = price,
        isFavorite = isFavorite
    )
}

fun BookEntity.asNetworkBook(): NetworkBook {
    return NetworkBook(
        title = title,
        link = link,
        image = image,
        author = author,
        price = price.toString(),
        publisher = publisher,
        pubDate = pubDate,
        isbn = isbn.toString(),
        description = description
    )
}
