package com.example.bakingapp2;

import android.os.Bundle;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.bakingapp2.Activity.MainBakingActivity;
import com.example.bakingapp2.Fragment.BakingFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)

public class EspressoTest {

    @Rule
    public ActivityTestRule<MainBakingActivity> activityTestRule = new ActivityTestRule<>(MainBakingActivity.class);

    @Before
    public void yourSetUPFragment() {

        BakingFragment bakingFragment = new BakingFragment();
        Bundle dataBundle = new Bundle();
        dataBundle.putString("Test", "Test");
        bakingFragment.setArguments(dataBundle);
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, bakingFragment)
                .commit();
    }

    @Test
    public void buttonShouldUpdateText() {
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withId(R.id.list)).perform(click());
    }
}