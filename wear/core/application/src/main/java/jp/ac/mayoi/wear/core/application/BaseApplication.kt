package jp.ac.mayoi.wear.core.application

import android.app.Application
import jp.ac.mayoi.wear.repository.implementations.CompassRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
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
                    repositoryKoinModule,
                    viewModelKoinModule,
                )
            )
        }
    }

    private val repositoryKoinModule = module {
        factory { CompassRepositoryImpl() }
    }

    private val viewModelKoinModule = module {

    }
}