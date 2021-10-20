package com.carkzis.pomona.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FruitDaoTest {

    private lateinit var database: PomonaDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PomonaDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun insertAllFruit_insertEmptyList_retrieveEmptyList() = runBlockingTest {
        // Given an empty list of DatabaseFruit.
        val fruit = mutableListOf<DatabaseFruit>()

        // When the fruit (or lack thereof) are inserted into the database.
        database.fruitDao().insertAllFruit(fruit)

        // Retrieve the first item emitted into a list.
        val flowList = async {
            // The (empty) list of fruit will be the first (and only) item in the list of lists.
            database.fruitDao().getAllFruit().take(1).toList()[0]
        }

        // Assert that the fruit list (first item in the list of lists) is also empty.
        assertThat(flowList.await().size, `is`(0))

    }

    @Test
    fun insertAllFruit_insertTestList_retrieveSameTestList() = runBlockingTest {
        // Given a test list of DatabaseFruit.
        val fruit = mutableListOf(
            DatabaseFruit("apple", 1000, 5000),
            DatabaseFruit("pear", 2000, 8000),
            DatabaseFruit("lemon", 3000, 10000)
        )

        // When the fruit are inserted into the database.
        database.fruitDao().insertAllFruit(fruit)

        // Retrieve the first item emitted into a list.
        val flowList = async {
            // The list of fruit will be the first (and only) item in the list of lists.
            database.fruitDao().getAllFruit().take(1).toList()[0]
        }

        // Assert that we collect the same list of DatabaseFruit we inserted.
        assertThat(flowList.await().size, `is`(3))
        assertThat(flowList.await()[0].type, `is`("apple"))
        assertThat(flowList.await()[1].price, `is`(2000))
        assertThat(flowList.await()[2].weight, `is`(10000))
    }

    @Test
    fun insertAllFruit_insertTwoListsWithSamePrimaryKeys_retrieveLatterList() = runBlockingTest {
        // Given two test lists of Database fruit, both of which use same primary keys (type).
        val fruitFormer = mutableListOf(
            DatabaseFruit("apple", 1000, 5000),
            DatabaseFruit("pear", 2000, 8000)
        )
        val fruitLatter = mutableListOf(
            DatabaseFruit("apple", 50, 100),
            DatabaseFruit("pear", 20, 80)
        )

        // When the fruit are inserted into the database, first fruitFormer, then fruitLatter.
        database.fruitDao().insertAllFruit(fruitFormer)
        database.fruitDao().insertAllFruit(fruitLatter)

        val flowList = async {
            // Retrieve the list by collecting from Flow.
            database.fruitDao().getAllFruit().take(1).toList()[0]
        }

        /*
         Assert that we collect the latter list.
         This is because one set will overwrite the other, as they use the same primary keys.
         */
        assertThat(flowList.await().size, `is`(2))
        assertThat(flowList.await()[0].type, `is`("apple"))
        assertThat(flowList.await()[0].price, `is`(50))
        assertThat(flowList.await()[0].weight, `is`(100))
        assertThat(flowList.await()[1].type, `is`("pear"))
        assertThat(flowList.await()[1].price, `is`(not(2000)))
        assertThat(flowList.await()[1].weight, `is`(not(8000)))
    }

}