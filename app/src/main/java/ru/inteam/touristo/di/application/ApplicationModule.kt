package ru.inteam.touristo.di.application

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
    factory { androidContext().contentResolver }
}
