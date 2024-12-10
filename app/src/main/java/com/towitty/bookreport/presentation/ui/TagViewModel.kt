package com.towitty.bookreport.presentation.ui

import androidx.lifecycle.ViewModel
import com.towitty.bookreport.data.repository.ITagRepository
import javax.inject.Inject


class TagViewModel @Inject constructor(
    private val tagLocalRepository: ITagRepository
) : ViewModel() {

}