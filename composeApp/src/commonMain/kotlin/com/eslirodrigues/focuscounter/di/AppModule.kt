package com.eslirodrigues.focuscounter.di

import com.eslirodrigues.focuscounter.datastore.DataStoreProvider
import com.eslirodrigues.focuscounter.counter.FocusCounterViewModel
import com.eslirodrigues.focuscounter.database.AppDatabase
import com.eslirodrigues.focuscounter.database.FocusSessionRepository
import com.eslirodrigues.focuscounter.database.getRoomDatabase
import com.eslirodrigues.focuscounter.history.HistoryViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {
    singleOf(::DataStoreProvider)
    single { getRoomDatabase(get()) }
    single { get<AppDatabase>().getFocusSessionDao() }
    singleOf(::FocusSessionRepository)
}

val viewModelModule = module {
    viewModelOf(::FocusCounterViewModel)
    viewModelOf(::HistoryViewModel)
}

val appModule = module {
    includes(dataModule, viewModelModule, platformModule)
}
