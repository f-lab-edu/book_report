package com.twitty.core.data.repository

import com.twitty.core.data.model.asEntity
import com.twitty.core.data.model.asTag
import com.twitty.database.dao.TagDao
import com.twitty.database.model.emptyTagEntity
import com.twitty.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagLocalRepository @Inject constructor(
    private val tagDao: TagDao,
) : ITagRepository {

    override suspend fun insertTag(name: String, color: Int) {
        val tag = Tag(
            id = 0,
            name = name,
            color = color
        )
        tagDao.insertTag(tag.asEntity())
    }

    override suspend fun deleteTag(tag: Tag) = tagDao.deleteTag(tag.asEntity())

    override suspend fun fetchTag(id: Int): Tag =
        tagDao.fetchTag(id).firstOrNull()?.asTag() ?: emptyTagEntity.asTag()

    override fun fetchAllTags(): Flow<List<Tag>> =
        tagDao.fetchAllTags().map { tags -> tags.map { it.asTag() } }

}

