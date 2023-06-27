package com.example.streaming.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.example.streaming.App
import com.example.streaming.MainActivity
import com.example.streaming.ui.fetcher.FetchingIdlingResource
import com.example.streaming.ui.utils.TestConstants.BACK_BTN
import com.example.streaming.ui.utils.TestConstants.BTN_PLAY
import com.example.streaming.ui.utils.TestConstants.BTN_REWIND
import com.example.streaming.ui.utils.TestConstants.BTN_SKIP
import com.example.streaming.ui.utils.TestConstants.NO_RESULTS_TEXT
import com.example.streaming.ui.utils.TestConstants.RESULTS_LAZY_VERTICAL_GRID
import com.example.streaming.ui.utils.TestConstants.RESULT_ITEM_ROW
import com.example.streaming.ui.utils.TestConstants.SEARCH_TEXT_FIELD
import com.example.streaming.ui.utils.TestConstants.SONG_IMAGE
import com.example.streaming.ui.utils.TestConstants.SONG_NAME
import com.example.streaming.ui.utils.TestConstants.SONG_SLIDER
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class HomeScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    val fetchingIdlingResource = FetchingIdlingResource()
    val mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Before
    fun setUp() {
        composeTestRule.activityRule.scenario.onActivity {
            (it.application as App).setFetcherListener(fetchingIdlingResource)
        }
        IdlingRegistry.getInstance().register(fetchingIdlingResource)
        IdlingPolicies.setIdlingResourceTimeout(30, TimeUnit.SECONDS)
    }

    @Test
    fun viewContainsAHeaderWithATextOfHome() {
        composeTestRule.onNode(hasText("Home")).apply {
            assertExists()
            assertIsDisplayed()
        }
    }

    @Test
    fun viewContainsATextFieldToSearchAQuery() {
        composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
            .apply {
                assertExists()
                assertIsDisplayed()
            }
    }

    @Test
    fun viewContainsANoResultText() {
        composeTestRule.onNodeWithTag(NO_RESULTS_TEXT, useUnmergedTree = true)
            .apply {
                assertExists()
                assertIsDisplayed()
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenUserTypesAQueryAndClicksOnSearchIconASearchIsInitiatedAndResultsAreShown() =
        runTest {
            composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
                .apply {
                    performClick()
                    delay(1000)
                    performTextInput("Reggae")
                    performImeAction()
                }
            composeTestRule.onNodeWithTag(
                RESULTS_LAZY_VERTICAL_GRID,
                useUnmergedTree = true
            ).apply {
                assertExists()
                assertIsDisplayed()
            }
        }

    @Test
    fun whenUserClicksASongRowAndNavigateToMusicPlayerSongViewIsDisplayed() =
        runTest {
            searchHomeItems()
            composeTestRule.onAllNodesWithTag(
                RESULT_ITEM_ROW,
                useUnmergedTree = true
            )[0].performClick()
            composeTestRule.onNode(hasText("Media Player")).apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithTag(SONG_IMAGE, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithTag(SONG_SLIDER, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithTag(SONG_NAME, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithTag(BTN_PLAY, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithTag(BTN_REWIND, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithTag(BTN_SKIP, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
        }

    @Test
    fun whenUserClicksArrowBackBtnNavigateToHomeScreen() =
        runTest {
            searchHomeItems()
            composeTestRule.onAllNodesWithTag(
                RESULT_ITEM_ROW,
                useUnmergedTree = true
            )[0].performClick()
            composeTestRule.onNodeWithTag(BACK_BTN, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
                performClick()
            }
            composeTestRule.onNodeWithTag(NO_RESULTS_TEXT, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
            }
        }

    private suspend fun searchHomeItems() {
        composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
            .apply {
                performClick()
                delay(1000)
                performTextInput("Reggae")
                performImeAction()
            }
        composeTestRule.onNodeWithTag(
            RESULTS_LAZY_VERTICAL_GRID,
            useUnmergedTree = true
        ).apply {
            assertExists()
            assertIsDisplayed()
        }
    }
}
