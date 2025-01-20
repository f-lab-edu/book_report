package com.twitty.core.data.repository

import com.twitty.core.data.model.asTag
import com.twitty.core.data.testdoubles.FakeTagDao
import com.twitty.database.model.emptyTagEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TagRepositoryTest {

    lateinit var subject: TagRepository
    lateinit var tagDao: FakeTagDao

    @Before
    fun setUp() {
        tagDao = FakeTagDao()
        subject = TagRepository(tagDao)
    }

    @Test
    fun `insertTag 동일한 이름과 색상이 존재하는 경우 저장되지 않음`() = runTest {

        subject.insertTag("tag1", 0)
        subject.insertTag("tag1", 0)
        subject.insertTag("tag1", 0)

        val tags = subject.fetchAllTags().first()
        Assert.assertEquals(1, tags.size)
    }

    @Test
    fun `fetchTag 존재하지 않는 ID로 태그 조회 시 빈 태그 반환`() = runTest {
        val emptyTag = emptyTagEntity.asTag()

        val tag = subject.fetchTag(123)

        Assert.assertNotNull(tag)
        Assert.assertEquals(emptyTag, tag)
    }

    @Test
    fun `fetchAllTags 태그가 하나도 존재하지 않으면 빈 리스트 반환`() = runTest {
        val tags = subject.fetchAllTags().first()

        Assert.assertNotNull(tags)
        Assert.assertTrue(tags.isEmpty())
    }
}