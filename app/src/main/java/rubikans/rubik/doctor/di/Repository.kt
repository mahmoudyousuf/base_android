package rubikans.rubik.doctor.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rubikans.rubik.doctor.data.retrofit.ApiServices
import rubikans.rubik.doctor.data.shared.DataManager
import rubikans.rubik.doctor.ui.auth.forgetPassword.ForgetPasswordRepository
import rubikans.rubik.doctor.ui.auth.forgetPasswordConfirmNewPassword.ConfirmForgetPasswordRepository
import rubikans.rubik.doctor.ui.auth.forgetPasswordOtpCheck.ForgetPasswordOtpCheckRepository
import rubikans.rubik.doctor.ui.auth.signIn.SignInRepository
import rubikans.rubik.doctor.ui.bottomSheets.changeLangauge.LangRepository
import rubikans.rubik.doctor.ui.bottomSheets.signOut.SignOutRepository
import rubikans.rubik.doctor.ui.main.clinicBraches.ClinicBranchesRepository
import rubikans.rubik.doctor.ui.main.changePassword.ChangePasswordRepository
import rubikans.rubik.doctor.ui.main.home.HomeRepository
import rubikans.rubik.doctor.ui.main.home.appointmentDetails.AppointmentDetailsRepository
import rubikans.rubik.doctor.ui.main.makeAppointment.MakeAppointmentRepository
import rubikans.rubik.doctor.ui.main.patients.PatientsRepository
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


    @Singleton
    @Provides
    fun forgetPasswordRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): ForgetPasswordRepository =
        ForgetPasswordRepository(appContext, dataManager,api)


    @Singleton
    @Provides
    fun forgetPasswordOtpCheckRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): ForgetPasswordOtpCheckRepository =
        ForgetPasswordOtpCheckRepository(appContext, dataManager,api)


    @Singleton
    @Provides
    fun confirmForgetPasswordRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): ConfirmForgetPasswordRepository =
        ConfirmForgetPasswordRepository(appContext, dataManager,api)

    @Singleton
    @Provides
    fun changePasswordRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): ChangePasswordRepository =
        ChangePasswordRepository(appContext, dataManager,api)



    @Singleton
    @Provides
    fun signOutRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): SignOutRepository =
        SignOutRepository(appContext, dataManager,api)



    @Singleton
    @Provides
    fun clinicBranchesRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): ClinicBranchesRepository =
        ClinicBranchesRepository(appContext, dataManager,api)


    @Singleton
    @Provides
    fun langRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): LangRepository =
        LangRepository(appContext, dataManager,api)


    @Singleton
    @Provides
    fun homeRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): HomeRepository =
        HomeRepository(appContext, dataManager,api)



    @Singleton
    @Provides
    fun appointmentDetailsRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): AppointmentDetailsRepository =
        AppointmentDetailsRepository(appContext, dataManager,api)




    @Singleton
    @Provides
    fun makeAppointmentRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): MakeAppointmentRepository =
        MakeAppointmentRepository(appContext, dataManager,api)




    @Singleton
    @Provides
    fun patientsRepository(
        @ApplicationContext appContext: Context,
        dataManager: DataManager,
        api: ApiServices,
    ): PatientsRepository =
        PatientsRepository(appContext, dataManager,api)


}