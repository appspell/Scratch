package com.example.scratch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.scratch.details.domain.DetailsViewModel
import com.example.scratch.details.view.DetailsContentScreen
import com.example.scratch.list.view.ListContentScreen
import com.example.scratch.list.domain.ListViewModel
import com.example.scratch.ui.theme.ScratchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ScratchTheme {
                NavHost(navController = navController, startDestination = "/list") {
                    composable("/list") {
                        val viewModel: ListViewModel by viewModels()
                        ListContentScreen(navigationController = navController, viewModel = viewModel)
                    }

                    composable(
                        "/list/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val elementId = backStackEntry.arguments?.getLong("id")
                            ?: throw Exception("missed ID argument")
                        val viewModel: DetailsViewModel by viewModels()
                        viewModel.init(elementId = elementId)
                        DetailsContentScreen(navigationController = navController, viewModel = viewModel)
                    }
                }
            }
        }

    }
}


