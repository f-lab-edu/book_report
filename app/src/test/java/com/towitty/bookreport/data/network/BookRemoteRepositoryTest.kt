package com.towitty.bookreport.data.network

import org.junit.Before
import org.junit.Test

class BookRemoteRepositoryTest {

    @Before
    fun setUp() {}

    @Test
    fun findBookByIsbn_passEmptyBookList_returnEmptyBookItem() {}

    @Test
    fun findBookByIsbn_noMatchIsbn_returnEmptyBookItem() {}

    @Test
    fun findBookByIsbn_matchIsbn_returnBookItem() {}
}