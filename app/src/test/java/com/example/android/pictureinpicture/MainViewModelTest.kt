package com.example.android.pictureinpicture

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.pictureinpicture.data.StopwatchPreference
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val stopwatchRepo = mockk<StopwatchRepository>()
    private val clockRepo = mockk<ClockRepository>()
    private lateinit var testViewModel: MainViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)
        mockkStatic(SystemClock::class)
        every { clockRepo.getUptimeMillis() } returns 0L
        coEvery { stopwatchRepo.fetchOnStartPreferences() } returns StopwatchPreference(0L)
        testViewModel = MainViewModel(stopwatchRepo, clockRepo)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun stopOrPause_starts_the_clock_if_its_not_running() {
        // Given: clock is not running
        Assert.assertNotNull(testViewModel.started.value)
        Assert.assertFalse(testViewModel.started.value!!)
        // When: we execute stopOrPause()
        testViewModel.startOrPause()
        // Then: clock is started
        Assert.assertTrue(testViewModel.started.value!!)
    }

    @Test
    fun stopOrPause_stops_the_clock_if_its_running() {
        // Given: clock is running
        testViewModel.startOrPause()
        Assert.assertNotNull(testViewModel.started.value)
        Assert.assertTrue(testViewModel.started.value!!)
        // When: we execute startOrPause()
        testViewModel.startOrPause()
        // Then: clock is stopped
        Assert.assertFalse(testViewModel.started.value!!)
    }

    @Ignore
    @Test
    fun clear_resets_the_timer() {
        // Setup: Start the stopwatch, verify the clock is running
        testViewModel.startOrPause()
        Assert.assertNotNull(testViewModel.started.value)
        Assert.assertTrue(testViewModel.started.value!!)
        // Fixme: At the time of test, time is null
        Assert.assertFalse(testViewModel.time.value == "00:00:00")
        // When: we execute clear()
        testViewModel.clear()
        // Then: clock is reset
        // Fixme: At the time of test, time is null
        Assert.assertTrue(testViewModel.time.value == "00:00:00")
    }
}
