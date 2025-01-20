package com.twitty.core.data.testdoubles

import com.twitty.core.data.AssetLoader
import com.twitty.model.Book
import kotlinx.serialization.json.Json
import java.io.File

class FakeAssetLoader : AssetLoader {

    override fun getBooks(fileName: String): List<Book> {
        val jsonFile = File("src/main/assets/$fileName")
        val jsonString = jsonFile.readText()

        return Json.decodeFromString(jsonString)
    }
}