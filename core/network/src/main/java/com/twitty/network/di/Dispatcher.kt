package com.twitty.network.di

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val bookReportDispatcher: BookReportDispatchers)

enum class BookReportDispatchers {
    Default,
    IO,
}