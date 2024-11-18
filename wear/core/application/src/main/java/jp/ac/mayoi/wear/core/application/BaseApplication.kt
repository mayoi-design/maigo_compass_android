package jp.ac.mayoi.wear.core.application

import android.app.Application
import android.hardware.SensorManager
import jp.ac.mayoi.wear.features.traveling.TravelingViewModel
import jp.ac.mayoi.wear.repository.implementations.CompassRepositoryImpl
import jp.ac.mayoi.wear.repository.implementations.LocationRepositoryImpl
import jp.ac.mayoi.wear.repository.implementations.TravelingRepositoryImpl
import jp.ac.mayoi.wear.repository.interfaces.CompassRepository
import jp.ac.mayoi.wear.repository.interfaces.LocationRepository
import jp.ac.mayoi.wear.repository.interfaces.TravelingRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

abstract class BaseApplication : Application() {
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
                    repositoryKoinModule,
                    viewModelKoinModule,
                )
            )
        }
    }

    private val coreKoinModule = module {
        single {
            this@BaseApplication.getSystemService(SENSOR_SERVICE) as SensorManager
        }
    }

    private val repositoryKoinModule = module {
        factory<CompassRepository> { CompassRepositoryImpl() }
        factory<LocationRepository> { LocationRepositoryImpl() }
        factory<TravelingRepository> { TravelingRepositoryImpl() }
    }

    private val viewModelKoinModule = module {
        viewModel { parameters ->
            TravelingViewModel(get(), get(), get(), get(), destination = parameters.get())
        }
    }
}