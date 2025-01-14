package com.twitty.core.data.repository

import com.twitty.core.data.model.asEntity
import com.twitty.core.data.model.asTag
import com.twitty.database.dao.TagDao
import com.twitty.database.model.emptyTagEntity
import com.twitty.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TagLocalRepository @Inject constructor(
    private val tagDao: TagDao
) : ITagRepository {

    override suspend fun insertTag(tag: Tag) = tagDao.insertTag(tag.asEntity())

    override suspend fun updateTag(tag: Tag) = tagDao.updateTag(tag.asEntity())

    override suspend fun deleteTag(tag: Tag) = tagDao.deleteTag(tag.asEntity())

    override suspend fun getTag(id: Int): Tag =
        tagDao.fetchTag(id).firstOrNull()?.asTag() ?: emptyTagEntity.asTag()

    override fun fetchAllTags(): Flow<List<Tag>> =
        flow {
            tagDao.fetchAllTags().collect { tagEntities -> tagEntities.map { it.asTag() } }
        }
}