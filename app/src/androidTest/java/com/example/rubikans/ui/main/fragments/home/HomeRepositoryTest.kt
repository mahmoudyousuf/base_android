package com.example.rubikans.ui.main.fragments.home

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.appmattus.kotlinfixture.Fixture
import com.appmattus.kotlinfixture.kotlinFixture
import com.example.rubikans.data.retrofit.ApiServices
import com.example.rubikans.data.room.faveMatches.FaveTeamDao
import com.example.rubikans.data.shared.DataManager
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryTest {

    @Mock
    lateinit var apiServices: ApiServices

    @Mock
    lateinit var dataManager: DataManager

    @Mock
    lateinit var favDao: FaveTeamDao


    lateinit var instrumentationContext: Context


    lateinit var useCase: HomeRepository
    private lateinit var fixture: Fixture


    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context

        useCase = HomeRepository(
            context = instrumentationContext,
            api = apiServices,
            dataManager = dataManager,
            favDao = favDao
        )
        fixture = kotlinFixture()
    }

    @Test
    fun testMarkAllFeedsAsRead() {
        val response: ResponseBody = fixture()
        runBlocking {
            val expected = Result.success(response)
            val actual = useCase.getMatches()
            Assert.assertEquals(expected, actual)
        }
    }
}


