package com.abhishekn.techm.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
//import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.abhishekn.techm.R

@RunWith(AndroidJUnit4ClassRunner::class)
class ImageFragmentTest {
    @Test
    fun testImageFragment() {
        val scenarioRule = launchFragmentInContainer<ImageFragment>()
        scenarioRule.onFragment {
             val recyclerView =     it.activity?.findViewById<RecyclerView>(R.id.recycler)
            recyclerView?.let {
                onView(withId(R.id.recycler))
                    .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>( recyclerView.adapter!!.itemCount - 1 ));
            }
            onView(withId(R.id.recycler)).check(matches(isDisplayed()))
        }
    }

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun isFragment_Visible_OnLaunch() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12));
    }

    @Test
    fun isFragmentVisible() {
//        val scenario = launchFragmentInContainer<ImageFragment>()
//        onView(withId(R.id.recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12));
        onView(withId(R.id.swipe_items)).perform(swipeDown())
    }



}