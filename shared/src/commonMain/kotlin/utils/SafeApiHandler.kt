package utils

import kotlinx.coroutines.CancellationException

object SafeApiHandler {

    suspend fun <T> safeApiCall(func : suspend () -> T) : Result<T> {
        return try {
            val result = func()
            Result.success(result)
        } catch (e: CancellationException) {
            e.printStackTrace()
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}