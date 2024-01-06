package goaldetails.di

import goaldetails.data.repository.GoalDetailsRepositoryImpl
import goaldetails.domain.repository.GoalDetailsRepository
import goaldetails.presentation.GoalDetailsViewModel
import org.koin.dsl.module
import utils.viewModelDefinition

fun goalDetails() = module {

    single<GoalDetailsRepository> { GoalDetailsRepositoryImpl() }
    viewModelDefinition { GoalDetailsViewModel(get()) }

}