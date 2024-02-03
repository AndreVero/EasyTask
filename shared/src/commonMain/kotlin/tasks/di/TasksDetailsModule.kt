package tasks.di

import org.koin.core.module.Module
import org.koin.dsl.module
import tasks.data.repository.TasksRepositoryImpl
import tasks.domain.repository.TasksRepository
import tasks.presentation.TasksViewModel
import utils.viewModelDefinition

fun tasksModule() = module {

    single<TasksRepository> { TasksRepositoryImpl() }
    viewModelDefinition { TasksViewModel(get(), get()) }

}

expect val platformModule: Module