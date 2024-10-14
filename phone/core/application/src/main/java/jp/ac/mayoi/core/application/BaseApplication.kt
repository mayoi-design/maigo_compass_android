package jp.ac.mayoi.core.application

import android.app.Application
import jp.ac.mayoi.service.interfaces.HealthService
import jp.ac.mayoi.service.interfaces.RankingService
import jp.ac.mayoi.service.interfaces.SpotService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

abstract class BaseApplication : Application() {
    private val retrofit: Retrofit by inject()

    override fun onCreate() {
        super.onCreate()

        koinStarter()
    }

    private fun koinStarter() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            androidFileProperties()

            modules(
                listOf(
                    coreKoinModule,
                    serviceKoinModule,
                    repositoryKoinModule,
                    viewModelKoinModule,
                )
            )
        }
    }

    private val coreKoinModule = module {
        single {
            // UAなどの設定も後からBuilderに追加する
            Retrofit.Builder()
                .baseUrl(BuildConfig.MAIGO_COMPASS_API_URL)
                .addConverterFactory(
                    Json.asConverterFactory(
                        "application/json".toMediaType()
                    )
                )
                .build()
        }
    }

    private val serviceKoinModule = module {
        factory { retrofit.create(HealthService::class.java) }
        factory { retrofit.create(RankingService::class.java) }
        factory { retrofit.create(SpotService::class.java) }
    }

    private val repositoryKoinModule = module {

    }

    private val viewModelKoinModule = module {

    }
}