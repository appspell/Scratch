package com.example.scratch.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scratch.R
import com.example.scratch.list.domain.ListState
import com.example.scratch.list.domain.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    navigationController: NavController,
    viewModel: ListViewModel
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.list_screen_title))
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.addNew()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.list_add_content_description)
                        )
                    }
                })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                state.value.list.forEachIndexed { index, item ->
                    item {
                        ListItem(item = item, onClick = {
                            viewModel.onSelect(index = index)
                        }, onDelete = {
                            viewModel.onDelete(index = index)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(item: ListState.Item, onClick: () -> Unit, onDelete: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable {
                onClick.invoke()
            },
        color = if (item.isSelected) Color.LightGray else MaterialTheme.colorScheme.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp),
                painter = painterResource(
                    id = when (item.type) {
                        ListState.Item.Type.BOAT -> R.drawable.ic_boat
                        ListState.Item.Type.RAFT -> R.drawable.ic_raft
                    }
                ),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = ""
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = item.dateTime,
                    textAlign = TextAlign.Start,
                )
            }


            if (item.isSelected) {
                Button(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = { onDelete.invoke() }
                ) {
                    Text(text = stringResource(id = R.string.delete))
                }
            }
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem(item = ListState.Item(
        id = 1,
        name = "name",
        isSelected = true,
        dateTime = "mm-dd-YYYY",
        type = ListState.Item.Type.BOAT
    ),
        onClick = {},
        onDelete = {})
}