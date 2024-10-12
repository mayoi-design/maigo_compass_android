package jp.ac.mayoi.wear.core.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

abstract class BaseApplication : Application() {

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

    }

    private val viewModelKoinModule = module {

    }
}