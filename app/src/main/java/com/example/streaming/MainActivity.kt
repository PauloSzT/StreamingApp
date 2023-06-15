package com.example.streaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.streaming.ui.navigation.NavigationGraph
import com.example.streaming.ui.theme.StreamingAppTheme
import com.example.streaming.ui.navigation.NavItem
import com.example.streaming.ui.navigation.NavItem.Companion.title
import com.example.streaming.ui.utils.UiConstants.EMPTY_STRING
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            var title by remember { mutableStateOf(EMPTY_STRING) }
            var displayBackIcon by remember { mutableStateOf(true) }
            DisposableEffect(key1 = Unit) {
                val destinationListener =
                    NavController.OnDestinationChangedListener { _, destination, _ ->
                        displayBackIcon = destination.route != NavItem.HomeScreen.route
                        title = destination.route?.title().orEmpty()
                    }
                navHostController.addOnDestinationChangedListener(destinationListener)
                onDispose { navHostController.removeOnDestinationChangedListener(destinationListener) }
            }
            StreamingAppTheme {
                Scaffold(
                    topBar = {
                        Column(modifier = Modifier.padding(8.dp)) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                },
                                navigationIcon = {
                                    if (displayBackIcon) {
                                        IconButton(onClick = { navHostController.navigateUp() }) {
                                            Icon(Icons.Filled.ArrowBack, "Back")
                                        }
                                    }
                                },
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}
