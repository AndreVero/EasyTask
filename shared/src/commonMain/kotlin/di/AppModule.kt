package di

import org.koin.dsl.module
import presentation.AppViewModel
import presentation.viewModelDefinition

fun appModule() = module {
    viewModelDefinition { AppViewModel()}
}