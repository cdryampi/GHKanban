package com.ircarren.ghkanban

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.preference.PreferenceManager
import com.google.accompanist.pager.ExperimentalPagerApi

import com.ircarren.ghkanban.screens.GenericTab
import com.ircarren.ghkanban.screens.IssuesScreen
import com.ircarren.ghkanban.screens.ReposScreen
import com.ircarren.ghkanban.screens.mainContainer
import com.ircarren.ghkanban.ui.theme.GHKanbanTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this.application
        @OptIn(ExperimentalPagerApi::class)
        super.onCreate(savedInstanceState)
        setContent {
            GHKanbanTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "repo"
                    ) {
                        composable("repo") { ReposScreen(navController = navController, application = context) }
                        composable("issues") { IssuesScreen(navController = navController, application = context, repoName = "m6") }

                    }
                }
            }
        }
    }
    @Composable
    fun repo(navController: NavController) {
        Button(onClick = { navController.navigate("issues") }) {
            Text("Ir a pantalla 2")
        }
        // Vista de la pantalla 1
    }
    @Composable
    fun issues(navController: NavController) {
        Button(onClick = { navController.navigate("repo") }) {
            Text("Ir a pantalla 1")
        }
        // Vista de la pantalla 2
    }
}