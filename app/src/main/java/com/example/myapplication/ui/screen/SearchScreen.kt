package com.example.myapplication.ui.screen

import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.components.TrackListItem
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.view_model.SearchState
import com.example.myapplication.ui.view_model.SearchViewModel
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit
) {

    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.provideFactory())
    val currentState by viewModel.uiState.collectAsState()

    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.search_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = inputText,
                onValueChange = { 
                    inputText = it
                    viewModel.startSearch(it)
                },
                modifier = Modifier.fillMaxWidth(),

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },

                trailingIcon = {
                    if (inputText.isNotEmpty()) {
                        IconButton(onClick = { 
                            inputText = ""
                            viewModel.startSearch("")
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = null)
                        }
                    }
                },

                placeholder = { Text(stringResource(R.string.search_placeholder)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (currentState) {

                is SearchState.Idle -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.search_empty_hint))
                    }
                }

                is SearchState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is SearchState.DataLoaded -> {
                    val tracksList = (currentState as SearchState.DataLoaded).trackList

                    if (tracksList.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Ничего не найдено")
                        }
                    } else {
                        LazyColumn {
                            items(tracksList) {
                                TrackListItem(it)
                                HorizontalDivider()
                            }
                        }
                    }
                }

                is SearchState.ErrorOccurred -> {
                    val errorText = (currentState as SearchState.ErrorOccurred).errorDescription

                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Ошибка загрузки: $errorText", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    MyApplicationTheme() {
        Surface {
            SearchScreen(
                onBackClick = {}
            )
        }
    }
}