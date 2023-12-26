package home.di

import home.data.repository.TaskRepositoryImpl
import home.domain.repository.TaskRepository
import org.koin.dsl.module
import home.presentation.HomeViewModel
import utils.viewModelDefinition

fun appModule() = module {

    single<TaskRepository> { TaskRepositoryImpl() }
    viewModelDefinition { HomeViewModel(get()) }

}