package com.towitty.bookreport.presentation.ui

import androidx.lifecycle.ViewModel
import com.towitty.bookreport.data.repository.ITagRepository
import javax.inject.Inject


class TagViewModel @Inject constructor(
    private val tagLocalRepository: ITagRepository
) : ViewModel() {

    /**
     * 1. 확장 함수 한번 확인 // OK
     * 2. TagViewModel 구현 //
     * 3. Repository 에 Dispatcher IO 전달 방법 검색 해보기 (ViewModel 에서 전달 ? 아니면 Repository 에서 주입 받음?)
     * 4. TagViewModel 사용 하도록 Composable 수저
     * 5.
     */
}