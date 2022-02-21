package com.techholding.hiltdemo

import android.view.View
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.techholding.hiltdemo.ui.main.view.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private var decorView: View? = null

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun loadDecorView() {
        activityScenarioRule.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.techholding.hiltdemo", appContext.packageName)
    }

    @Test
    fun testRecyclerView_itemCorrect() {
        val scenario = launchActivity<MainActivity>()
        Thread.sleep(2000)
        onView(RecyclerViewMatcher(R.id.recyclerView).atPositionOnView(5, R.id.textViewUserName))
            .check(matches(withText("Kolby Orn")))
            .perform(click())
    }

    @Test
    fun testRecyclerView_itemToast(){
        testRecyclerView_itemCorrect()
        //Modify toast_msg to your own string resource
        onView(withText("Kolby Orn")).inRoot(withDecorView(not(decorView)))
            .check(matches(isDisplayed()));
    }
}