package com.twitty.core.data

import android.content.Context
import com.twitty.model.Book
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

class AssetLoaderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AssetLoader {

    override fun getBooks(fileName: String): List<Book> {
        val jsonString = getJsonString(fileName) ?: return emptyList()
        return try {
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString<List<Book>>(jsonString).let { books ->
                Timber.tag("AssetLoader").d("${books.size}")
                books
            }
        } catch (e: Exception) {
            Timber.tag("AssetLoader").e("${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    private fun getJsonString(fileName: String): String? =
        kotlin.runCatching {
            loadAsset(fileName)
        }.getOrNull()


    private fun loadAsset(fileName: String): String {
        return context.assets.open(fileName).use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes)
        }
    }
}