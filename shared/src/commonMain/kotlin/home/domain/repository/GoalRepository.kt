package home.domain.repository

import home.domain.model.Goal

interface GoalRepository {

    suspend fun getGoals() : Result<List<Goal>>

}