package com.github.coutinhonobre.roboletric

import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class)
@LooperMode(LooperMode.Mode.PAUSED)
class MainActivityTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()



    private val testDispatcher = UnconfinedTestDispatcher()
//    private val testDispatcher = StandardTestDispatcher()


    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .start()
            .resume()
            .get()

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testClickTextView() = runTest {
        val button = activity.findViewById<Button>(R.id.button)

        button.performClick()

        advanceUntilIdle()
        runCurrent()

        shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        assertWithTimeout {
            val textView = activity.findViewById<TextView>(R.id.textView)
            assertEquals("Texto atualizado", textView.text.toString())
        }
    }


    @Test
    fun testClickTextView2() = runTest {
        val button = activity.findViewById<Button>(R.id.button)

        button.performClick()

        advanceUntilIdle()
        runCurrent()

        shadowOf(Looper.getMainLooper()).runToEndOfTasks()

        waitForCondition {
            val textView = activity.findViewById<TextView>(R.id.textView)
            textView.text.toString() == "Texto atualizado"
        }

        val textView = activity.findViewById<TextView>(R.id.textView)
        assertEquals("Texto atualizado", textView.text.toString())
    }

}

suspend fun waitForCondition(
    timeoutMillis: Long = 5000,
    conditionCheckIntervalMillis: Long = 100,
    condition: () -> Boolean
) {
    val startTime = System.currentTimeMillis()
    while (!condition()) {
        if (System.currentTimeMillis() - startTime > timeoutMillis) {
            throw Exception("Condition not met within $timeoutMillis milliseconds")
        }
        delay(conditionCheckIntervalMillis)
    }
}

suspend fun assertWithTimeout(
    timeoutMillis: Long = 5000,
    conditionCheckIntervalMillis: Long = 100,
    assertion: () -> Unit
) {
    val startTime = System.currentTimeMillis()
    while (true) {
        try {
            assertion()
            return
        } catch (e: AssertionError) {
            if (System.currentTimeMillis() - startTime > timeoutMillis) {
                throw AssertionError("Assertion failed after $timeoutMillis ms: ${e.message}", e)
            }
            delay(conditionCheckIntervalMillis)
        }
    }
}


