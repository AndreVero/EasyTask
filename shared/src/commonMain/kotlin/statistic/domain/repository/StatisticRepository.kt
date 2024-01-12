package statistic.domain.repository

import statistic.domain.model.Statistic

interface StatisticRepository {

    suspend fun getStatistic() : Result<List<Statistic>>

}