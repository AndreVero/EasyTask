package home.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import home.domain.model.Task
import home.domain.repository.TaskRepository
import utils.SafeApiHandler

class TaskRepositoryImpl : TaskRepository {

    override suspend fun getTasks(): Result<List<Task>> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("Tasks").get()
            userResponse.documents.map { it.data() }
        }
    }

}