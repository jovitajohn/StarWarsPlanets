package com.jovita.startwarplanets

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jovita.startwarplanets.planetListing.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun viewsPresent(){
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.star_wars_planets)).assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.loading_contents)).assertExists()
    }

    @Test
    fun itemClickCheck(){
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.planetitem)).performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.snackbar)).assertIsDisplayed()
    }
}