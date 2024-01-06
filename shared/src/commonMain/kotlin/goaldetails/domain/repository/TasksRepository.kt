package goaldetails.domain.repository

import goaldetails.domain.model.GoalDetails
interface GoalDetailsRepository {

    suspend fun getGoalDetails(): Result<List<GoalDetails>>

}