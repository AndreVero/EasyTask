package tasks.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import utils.AndroidVibrationHandler
import utils.VibrationHandler

actual val platformModule: Module
    get() = module {
        single<VibrationHandler> { AndroidVibrationHandler(androidContext()) }
    }