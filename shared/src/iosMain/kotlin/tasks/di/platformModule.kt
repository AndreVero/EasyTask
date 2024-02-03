package tasks.di

import org.koin.core.module.Module
import org.koin.dsl.module
import utils.IosVibrationHandler
import utils.VibrationHandler

actual val platformModule: Module
    get() = module {
        single<VibrationHandler> { IosVibrationHandler() }
    }