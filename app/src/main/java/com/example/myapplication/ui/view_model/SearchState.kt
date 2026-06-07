package com.example.myapplication.ui.view_model

import com.example.myapplication.domain.model.Track

sealed class SearchState {
    data object Idle : SearchState()
    data object Loading : SearchState()
    data class DataLoaded(val trackList: List<Track>) : SearchState()
    data class ErrorOccurred(val errorDescription: String) : SearchState()
}