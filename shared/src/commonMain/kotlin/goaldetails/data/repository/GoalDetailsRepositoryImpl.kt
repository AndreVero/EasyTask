package goaldetails.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import goaldetails.data.remote.dto.GoalDetailsDto
import goaldetails.data.remote.dto.toGoalDetails
import goaldetails.domain.model.GoalDetails
import goaldetails.domain.repository.GoalDetailsRepository
import utils.SafeApiHandler

class GoalDetailsRepositoryImpl : GoalDetailsRepository {

    override suspend fun getGoalDetails(): Result<List<GoalDetails>> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("TodayTasks").get()
            val tasks = userResponse.documents.map<DocumentSnapshot, GoalDetailsDto> { it.data() }
            tasks.map { it.toGoalDetails() }
        }
    }

}