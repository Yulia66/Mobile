package com.example.myapplication.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.creator.Creator
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepo: TracksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchState>(SearchState.Initial)
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    private val searchQueryFlow = MutableSharedFlow<String>()

    init {
        collectSearchQueries()
    }

    private fun collectSearchQueries() {
        viewModelScope.launch(Dispatchers.IO) {
            searchQueryFlow
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    fun startSearch(searchText: String) {
        if (searchText.isEmpty()) {
            _uiState.value = SearchState.Initial
            return
        }
        viewModelScope.launch {
            searchQueryFlow.emit(searchText)
        }
    }

    private suspend fun performSearch(queryText: String) {
        try {
            _uiState.value = SearchState.Searching
            val searchResult = tracksRepo.searchTracks(queryText)
            _uiState.value = SearchState.Success(searchResult)
        } catch (ioException: IOException) {
            _uiState.value = SearchState.Fail(ioException.message ?: "Ошибка соединения")
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel(Creator.getTracksRepository()) as T
            }
        }
    }
}