package com.jovita.startwarplanets

import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jovita.startwarplanets.planetListing.MainActivity
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jovita.startwarplanets", appContext.packageName)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun actionbarTitlePresent() = runTest{

        composeTestRule.activity.setContent {
               composeTestRule.activity.BaseContent()
            composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
        }
    }
}