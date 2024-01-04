package home.di

import home.data.repository.GoalRepositoryImpl
import home.domain.repository.GoalRepository
import org.koin.dsl.module
import home.presentation.HomeViewModel
import utils.viewModelDefinition

fun goalModule() = module {

    single<GoalRepository> { GoalRepositoryImpl() }
    viewModelDefinition { HomeViewModel(get()) }

}