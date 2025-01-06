package com.twitty.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val bookReportDispatcher: BookReportDispatchers)

enum class BookReportDispatchers {
    Default,
    IO,
}