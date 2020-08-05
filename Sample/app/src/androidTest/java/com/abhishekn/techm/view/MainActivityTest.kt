package com.abhishekn.techm.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.abhishekn.techm.R
import kotlinx.android.synthetic.main.activity_main.view.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{
    @Test
    fun testActivityMainInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun testVisibilityFragment() {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.titleFragment)).check(matches(withEffectiveVisibility(VISIBLE)))
        onView(withId(R.id.titleFragment)).check(matches(isDisplayed()))
    }
}