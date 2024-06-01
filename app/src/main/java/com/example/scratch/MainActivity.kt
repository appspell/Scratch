package com.example.scratch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scratch.list.ListContent
import com.example.scratch.list.ListViewModel
import com.example.scratch.ui.theme.ScratchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel : ListViewModel by viewModels()
            ScratchTheme {
                NavHost(navController = navController, startDestination = "/list") {
                    composable("/list") {

                        ListContent(
                            viewModel = viewModel,
                            navigationController = navController
                        )
                    }
                }
            }
        }
    }
}
