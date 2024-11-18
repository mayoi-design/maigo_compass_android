package jp.ac.mayoi.maigocompass

import jp.ac.mayoi.core.application.BaseApplication
import jp.ac.mayoi.maigocompass.mock.DevelopmentMemoryRepository
import jp.ac.mayoi.maigocompass.mock.DevelopmentRankingRepository
import jp.ac.mayoi.maigocompass.mock.DevelopmentTravelingRepository
import jp.ac.mayoi.repository.interfaces.MemoryRepository
import jp.ac.mayoi.repository.interfaces.RankingRepository
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class DevelopmentApplication : BaseApplication() {
    override fun koinStarter() {
        super.koinStarter()

        loadKoinModules(
            listOf(
                module {
                    factory<MemoryRepository> { DevelopmentMemoryRepository() }
                    factory<RankingRepository> { DevelopmentRankingRepository() }
                    factory<TravelingRepository> { DevelopmentTravelingRepository() }
                }
            )
        )
    }
}