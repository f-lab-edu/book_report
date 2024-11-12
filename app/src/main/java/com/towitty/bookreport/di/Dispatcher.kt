package com.towitty.bookreport.di

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val bookReportDispatcher: BookReportDispatchers)

enum class BookReportDispatchers {
    Default,
    IO,
}