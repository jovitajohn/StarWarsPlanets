package com.jovita.startwarplanets

import android.content.Context
import android.content.Intent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.jovita.startwarplanets.data.RootPlanetItem
import com.jovita.startwarplanets.planetDetail.PlanetDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PlanetDetailComposeTest {
    private lateinit var scenario: ActivityScenario<PlanetDetailActivity>
    val context: Context = InstrumentationRegistry
        .getInstrumentation()
        .targetContext

    /* @get: Rule
     val composeTestRule = createAndroidComposeRule<PlanetDetailActivity>()*/

    @get: Rule
    val composeRule = createEmptyComposeRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        scenario = ActivityScenario.launch(
            activityIntent()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun viewsPresent() {
           composeRule.onNodeWithText(context.getString(R.string.top_bar_planet,
               context.getString(R.string.test_planet)))
               .assertIsDisplayed()
           composeRule.onNodeWithTag(context.getString(R.string.snackbar)).assertIsDisplayed()

    }

    fun activityIntent(): Intent {
        val intent = Intent(context, PlanetDetailActivity::class.java)
        intent.putExtra(
            context.getString(R.string.get_extra_planet),
            RootPlanetItem(
                context.getString(R.string.test_planet_uid), context.getString(R.string.test_planet),
                context.getString(R.string.test_planet_url))
        )
        return intent
    }

}