package goaldetails.domain.repository

import goaldetails.domain.model.GoalDetails
interface GoalDetailsRepository {

    suspend fun getGoalDetails(id: String): Result<GoalDetails>

}