package rubikans.rubik.doctor.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager

import rubikans.rubik.doctor.ui.auth.signIn.SignInRepository




import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Repository {


    @Singleton
    @Provides
    fun signInRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): SignInRepository =
        SignInRepository(appContext, dataManager, api)
















}