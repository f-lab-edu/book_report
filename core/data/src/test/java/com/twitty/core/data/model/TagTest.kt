package com.twitty.core.data.model

import com.twitty.database.model.TagEntity
import com.twitty.model.Tag
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TagTest {
    lateinit var tag: Tag
    lateinit var tagEntity: TagEntity

    @Before
    fun setUp() {
        tag = Tag(1, "Model", 110000)
        tagEntity = TagEntity(2, "Entity", 220000)
    }

    @Test
    fun tagMapsToTagEntity() {
        val tagEntity = tag.asEntity()

        Assert.assertEquals(tag.id, tagEntity.id)
        Assert.assertEquals(tag.name, tagEntity.name)
        Assert.assertEquals(tag.color, tagEntity.color)
    }

    @Test
    fun tagEntityMapsToTag() {
        val tag = tagEntity.asTag()

        Assert.assertEquals(tagEntity.id, tag.id)
        Assert.assertEquals(tagEntity.name, tag.name)
        Assert.assertEquals(tagEntity.color, tag.color)
    }
}