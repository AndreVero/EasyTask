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

    override suspend fun getGoalDetails(id: String): Result<GoalDetails> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("Goals").document(id).get()
            userResponse.data<GoalDetailsDto>().toGoalDetails()
        }
    }

}