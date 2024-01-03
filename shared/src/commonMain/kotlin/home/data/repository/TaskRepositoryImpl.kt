package home.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import home.domain.model.TaskDto
import home.domain.model.Task
import home.domain.model.toTaskUiModel
import home.domain.repository.TaskRepository
import utils.SafeApiHandler

class TaskRepositoryImpl : TaskRepository {

    override suspend fun getTasks(): Result<List<Task>> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("Tasks").get()
            val tasks = userResponse.documents.map<DocumentSnapshot, TaskDto> { it.data() }
            tasks.map { it.toTaskUiModel() }
        }
    }

}