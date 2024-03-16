package com.jovita.startwarplanets

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jovita.startwarplanets.planetListing.MainActivity
import com.jovita.startwarplanets.ui.theme.StartwarPlanetsTheme
import org.junit.Rule
import org.junit.Test

class MainActivityComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun actionbarTitlePresent(){
        composeTestRule.setContent {
            StartwarPlanetsTheme {
                MainActivity()
            }
        }
        composeTestRule.onNodeWithText("Star Wars Planets").assertIsDisplayed()
    }
}