package com.eslirodrigues.focuscounter.di

import com.eslirodrigues.focuscounter.database.getDatabaseBuilder
import com.eslirodrigues.focuscounter.datastore.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single { createDataStore(androidContext()) }
    single { getDatabaseBuilder(androidContext()) }
}
