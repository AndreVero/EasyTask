package home.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import home.data.remote.dto.GoalDto
import home.domain.model.Goal
import home.data.remote.dto.toGoal
import home.domain.repository.GoalRepository
import utils.SafeApiHandler

class GoalRepositoryImpl : GoalRepository {

    override suspend fun getGoals(): Result<List<Goal>> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("Tasks").get()
            val tasks = userResponse.documents.map<DocumentSnapshot, GoalDto> { it.data() }
            tasks.map { it.toGoal() }
        }
    }

}