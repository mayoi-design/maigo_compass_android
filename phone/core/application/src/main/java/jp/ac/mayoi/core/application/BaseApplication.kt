package jp.ac.mayoi.core.application

import android.app.Application
import android.util.Log
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import jp.ac.mayoi.core.datastore.UserInfoDataStoreWrapper
import jp.ac.mayoi.onboarding.OnboardingViewModel
import jp.ac.mayoi.repository.implementations.RankingRepositoryImpl
import jp.ac.mayoi.repository.implementations.TravelingRepositoryImpl
import jp.ac.mayoi.repository.interfaces.RankingRepository
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import jp.ac.mayoi.service.interfaces.HealthService
import jp.ac.mayoi.service.interfaces.ImageService
import jp.ac.mayoi.service.interfaces.RankingService
import jp.ac.mayoi.service.interfaces.SpotService
import jp.ac.mayoi.traveling.TravelingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.UUID

abstract class BaseApplication : Application() {
    private val retrofit: Retrofit by inject()

    // Context Leakをしていることに注意
    private val userInfoDataStoreWrapper = UserInfoDataStoreWrapper(this)
    private var applicationUserId: String = ""

    override fun onCreate() {
        super.onCreate()

        // RetrofitのbuildにUserIdが必要なのでここで先にUserIdを作る
        uniqueUserIdStarter()
        koinStarter()
    }

    protected open fun koinStarter() {
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

    private fun uniqueUserIdStarter() {
        runBlocking(Dispatchers.IO) {
            val userId: String = userInfoDataStoreWrapper.getUserId().first()

            applicationUserId = if (userId == "") {
                Log.d("UserID", "User ID Not Found.")
                val uuid = UUID.randomUUID().toString()
                userInfoDataStoreWrapper.setUserId(uuid)

                uuid
            } else {
                Log.d("UserID", "User ID Found.")
                userId
            }
            Log.d("UserID", "Current User ID: $applicationUserId")
        }
    }

    private val coreKoinModule = module {
        single {
            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(
                    Interceptor { chain ->
                        val userId = runBlocking { userInfoDataStoreWrapper.getUserId().last() }
                        val apiKey = BuildConfig.MAIGO_COMPASS_API_KEY
                        val request: Request = chain.request()
                            .newBuilder()
                            .header("x-userid", userId)
                            .header("x-client-secret", apiKey)
                            .build()
                        chain.proceed(request)
                    }
                )
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
                )

            Retrofit.Builder()
                .baseUrl(BuildConfig.MAIGO_COMPASS_API_URL)
                .addConverterFactory(
                    Json.asConverterFactory(
                        "application/json".toMediaType()
                    )
                )
                .client(okHttpClient.build())
                .build()
        }
        single {
            // 多分これよくないんだろうなと思ってやってる
            userInfoDataStoreWrapper
        }
        single<MessageClient> { Wearable.getMessageClient(this@BaseApplication) }
        single<CapabilityClient> { Wearable.getCapabilityClient(this@BaseApplication) }
    }

    private val serviceKoinModule = module {
        factory<HealthService> { retrofit.create(HealthService::class.java) }
        factory<RankingService> { retrofit.create(RankingService::class.java) }
        factory<SpotService> { retrofit.create(SpotService::class.java) }
        factory<ImageService> { retrofit.create(ImageService::class.java) }
    }

    private val repositoryKoinModule = module {
        factory<RankingRepository> { RankingRepositoryImpl(get()) }
        factory<TravelingRepository> { TravelingRepositoryImpl(get()) }
    }

    private val viewModelKoinModule = module {
        viewModel { OnboardingViewModel() }
        viewModel { TravelingViewModel(get()) }
    }
}