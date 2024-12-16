package com.twitty.core.data.model

import com.twitty.database.model.TagEntity
import com.twitty.model.Tag

fun Tag.asEntity(): TagEntity =
    TagEntity(
        id = id,
        name = name,
        color = color
    )

fun TagEntity.asTag(): Tag =
    Tag(
        id = id,
        name = name,
        color = color
    )