package rubikans.rubik.doctor.di


import android.content.Context
import android.content.pm.PackageInfo
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
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor = HttpLoggingInterceptor()

    init {
        if (true /* BuildConfig.DEBUG*/) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
    }


    @Singleton
    @Provides
    fun providePackageInfo(@ApplicationContext context: Context): PackageInfo =
        context.packageManager.getPackageInfo(context.packageName, 0);


    @Singleton
    @Provides
    @Named("VersionName")
    fun provideVersionName(packageInfo: PackageInfo): String =
        packageInfo.versionName

    @Singleton
    @Provides
    fun provideDataManager(@ApplicationContext context: Context): DataManager =
        (Contexts.getApplication(context) as BaseApplication).dataManager!!



    @Provides
    @Singleton
    fun provideOkHttpClient(
        dataManager: DataManager
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                var lan = "1"

                if(dataManager.lang == "ar"){
                    lan = "2"
                }else{
                    lan = "1"
                }
                val request = chain.request().newBuilder()
                    .header("App-Platform", "android")
                    .header("Authorization", "Bearer ${dataManager.token}")
                    .header("lang", lan)
                    .build()
                chain.proceed(request)
            })
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
//        .baseUrl("https://devdevartlabcrm.com")
//        .baseUrl("https://test.rubikcare.com")
//        .baseUrl("https://testapi.rubikcare.com")
        .baseUrl("https://stage.rubikcare.com")
//        .baseUrl("https://uat.rubikcare.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}