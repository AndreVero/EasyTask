package tasks.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import utils.AndroidVibrationHandler
import utils.VibrationHandler

internal actual val platformModule: Module
    get() = module {
        single<VibrationHandler> { AndroidVibrationHandler(androidContext()) }
    }