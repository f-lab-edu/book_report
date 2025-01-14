package com.twitty.feature.settings.tag

import androidx.lifecycle.ViewModel
import com.twitty.core.data.repository.ITagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val tagRepository: ITagRepository
) : ViewModel() {

}