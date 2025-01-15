package com.towitty.bookreport.data.repository

import com.towitty.bookreport.data.database.FakeTagDao
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TagLocalRepositoryTest {

    private lateinit var fakeTagDao: FakeTagDao
    private val tagDatabase = mutableListOf<com.twitty.database.model.TagEntity>()

    @Before
    fun setUp() {
        fakeTagDao = FakeTagDao(tagDatabase)
    }

    @Test
    fun insertTag_WhenTagAdded_ShouldRetrieveSameTag() = runTest {
        tagDatabase.clear()

        val tag = com.twitty.database.model.TagEntity(10, "", 0)

        fakeTagDao.insertTag(tag)

        val getTag = fakeTagDao.fetchTag(10).firstOrNull()
        assertEquals(tag, getTag)
    }

    @Test
    fun fetchTags() = runTest {
        fakeTagDao.tagDatabase.clear()
        fakeTagDao.tagDatabase.add(com.twitty.database.model.TagEntity(1, "", 0))
        fakeTagDao.tagDatabase.add(com.twitty.database.model.TagEntity(2, "", 0))
        fakeTagDao.tagDatabase.add(com.twitty.database.model.TagEntity(3, "", 0))

        val tags = fakeTagDao.fetchAllTags().firstOrNull() ?: emptyList()

        assertEquals(3, tags.size)
        assertTrue(tags.contains(com.twitty.database.model.TagEntity(1, "", 0)))
        assertTrue(tags.contains(com.twitty.database.model.TagEntity(2, "", 0)))
        assertTrue(tags.contains(com.twitty.database.model.TagEntity(3, "", 0)))
    }

    @Test
    fun deleteTagById_WhenTagExists_ShouldRemoveTagFromDatabase() = runTest {
        fakeTagDao.tagDatabase.clear()
        fakeTagDao.tagDatabase.add(com.twitty.database.model.TagEntity(1, "", 0))
        fakeTagDao.deleteTag(com.twitty.database.model.TagEntity(1, "", 0))

        val deletedTag = fakeTagDao.fetchTag(1).firstOrNull() ?: com.twitty.database.model.emptyTagEntity

        assertEquals(deletedTag, com.twitty.database.model.emptyTagEntity)
        assertFalse(fakeTagDao.tagDatabase.contains(com.twitty.database.model.TagEntity(1, "", 0)))
    }
}