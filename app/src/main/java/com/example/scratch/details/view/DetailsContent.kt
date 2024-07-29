package com.example.scratch.details.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.scratch.R
import com.example.scratch.details.domain.DetailsViewModel
import com.example.scratch.details.domain.DetailsViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContentScreen(navigationController: NavHostController, viewModel: DetailsViewModel) {
    val state = viewModel.state.collectAsState()
    DetailsContent(
        state = state.value,
        onNavigationUp = {
            navigationController.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsContent(
    state: DetailsViewState,
    onNavigationUp: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(state.data?.name ?: stringResource(id = R.string.details_screen))
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "" // TODO add description
                        )
                    }
                },
            )
        }
    ) { paddings ->

        Box(modifier = Modifier.padding(paddings)) {

        }
    }
}

@Preview
@Composable
private fun DetailsContentPreivw() {
    DetailsContent(
        state = DetailsViewState(
            isLoading = false,
            isError = false,
            data = DetailsViewState.Data(
                id = 1L,
                name = "name",
                date = "2016-02-11T13:42:14.401Z",
                type = DetailsViewState.Type.BOAT,
                image = "https://github.com/appspell/Scratch/blob/jetpack-compose-mvvm/server-response/6616EC71-2609-48F3-BC82-F4AB5CB3ECF0.JPG?raw=true"
            )
        ),
        onNavigationUp = {}
    )
}