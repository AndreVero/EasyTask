package statistic.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import statistic.data.remote.dto.StatisticDto
import statistic.data.remote.dto.toStatistic
import statistic.domain.model.Statistic
import statistic.domain.repository.StatisticRepository
import utils.SafeApiHandler

class StatisticRepositoryImpl : StatisticRepository {

    override suspend fun getStatistic(): Result<List<Statistic>> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("Statistic").get()
            val statistics = userResponse.documents.map<DocumentSnapshot, StatisticDto> { it.data() }
            statistics.map { it.toStatistic() }
        }
    }

}