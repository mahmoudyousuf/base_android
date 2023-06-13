package com.example.rubikans.data.room.faveMatches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.rubikans.data.room.AppDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FaveTeamDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: FaveTeamDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.faveTeamDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFaveTeamEntity() = runTest {
        val faveTeamEntity = FaveTeamEntity(1)
        dao.insert(faveTeamEntity)
        val allShoppingItems = dao.all
        assertThat(allShoppingItems).contains(faveTeamEntity)
    }


}