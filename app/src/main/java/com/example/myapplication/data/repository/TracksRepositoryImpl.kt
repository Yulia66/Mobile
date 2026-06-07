package com.example.myapplication.data.repository

import com.example.myapplication.data.dto.TracksSearchRequest
import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.domain.api.NetworkClient
import com.example.myapplication.domain.api.TracksRepository
import com.example.myapplication.domain.model.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {

        val response = networkClient.doRequest(
            TracksSearchRequest(expression)
        )

        delay(1000)

        return if (response.resultCode == 200) {

            (response as TracksSearchResponse).results.map {

                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60

                val time = "%02d:%02d".format(minutes, remainingSeconds)

                Track(
                    trackName = it.trackName,
                    artistName = it.artistName,
                    trackTime = time
                )
            }

        } else {
            emptyList()
        }
    }
}