package statistic.di

import org.koin.dsl.module
import statistic.data.repository.StatisticRepositoryImpl
import statistic.domain.repository.StatisticRepository
import statistic.presentation.StatisticViewModel
import utils.viewModelDefinition

fun statistic() = module {

    single<StatisticRepository> { StatisticRepositoryImpl() }
    viewModelDefinition { StatisticViewModel(get()) }

}