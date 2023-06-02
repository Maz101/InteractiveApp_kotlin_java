package com.example.interactivebible;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashscreenactivityTest {

    @Rule
    public ActivityScenarioRule<Splashscreenactivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(Splashscreenactivity.class);

    @Test
    public void splashscreenactivityTest() {
    }
}
