package com.eslirodrigues.focuscounter.di

import com.eslirodrigues.focuscounter.datastore.DataStoreProvider
import com.eslirodrigues.focuscounter.counter.FocusCounterViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {
    singleOf(::DataStoreProvider)
}

val viewModelModule = module {
    viewModelOf(::FocusCounterViewModel)
}

val appModule = module {
    includes(dataModule, viewModelModule, platformModule)
}
