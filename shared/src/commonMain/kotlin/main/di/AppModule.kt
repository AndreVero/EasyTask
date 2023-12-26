package main.di

import main.data.repository.TaskRepositoryImpl
import main.domain.repository.TaskRepository
import org.koin.dsl.module
import main.presentation.AppViewModel
import utils.viewModelDefinition

fun appModule() = module {
    single<TaskRepository> { TaskRepositoryImpl() }
    viewModelDefinition { AppViewModel(get()) }
}