package utils

import goaldetails.di.goalDetails
import home.di.goalModule
import org.koin.core.context.startKoin
import statistic.di.statistic
import tasks.di.platformModule
import tasks.di.tasksModule

object HelperKoin {
    fun initKoin() {
        startKoin {
            modules(goalModule() + tasksModule() + goalDetails() + statistic() + platformModule)
        }
    }
}
