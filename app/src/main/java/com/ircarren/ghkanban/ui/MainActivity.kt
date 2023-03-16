package com.ircarren.ghkanban.ui

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ircarren.ghkanban.ui.screens.IssuesScreen
import com.ircarren.ghkanban.ui.screens.ReposScreen
import com.ircarren.ghkanban.ui.theme.GHKanbanTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("RememberReturnType")
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
                        composable("repo") { ReposScreen(navController = navController) }
                        composable(
                            "issues/{userName}/{repoName}",
                            arguments = listOf(navArgument("repoName") {
                                type = NavType.StringType
                            }, navArgument("userName") {
                                type = NavType.StringType
                            })
                        ) { navBackStackEntry ->
                            val repoName = navBackStackEntry.arguments?.getString("repoName")
                            val userName = navBackStackEntry.arguments?.getString("userName")
                            IssuesScreen(
                                navController = navController,
                                repoName = repoName ?: "LaSagradaFamilia",
                                userName = userName ?: "cdryampi"
                            )

                        }


                    }

                }
            }
        }
    }
}
