package rubikans.rubik.doctor.di


import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import rubikans.rubik.doctor.BuildConfig
import rubikans.rubik.doctor.base.BaseApplication
 import rubikans.rubik.doctor.data.shared.DataManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor = HttpLoggingInterceptor()

    init {
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
    }


    @Singleton
    @Provides
    fun provideDataManager(@ApplicationContext context: Context): DataManager =
        (Contexts.getApplication(context) as BaseApplication).dataManager!!

    @Singleton
    @Provides
    internal fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        dataManager: DataManager,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("X-Auth-Token", "${dataManager.apiKey}")
                    .build()
                chain.proceed(request)
            })
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckerInterceptor)
        }

        return builder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.football-data.org/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()




}