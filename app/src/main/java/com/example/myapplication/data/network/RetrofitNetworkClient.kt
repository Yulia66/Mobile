package com.example.myapplication.data.network

import com.example.myapplication.creator.MusicStorage
import com.example.myapplication.data.dto.TracksSearchRequest
import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.domain.api.NetworkClient

class RetrofitNetworkClient(
    private val musicStorage: MusicStorage
) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val request = dto as TracksSearchRequest
        val foundItems = musicStorage.findTracksByQuery(request.expression)
        return TracksSearchResponse(foundItems).apply { resultCode = 200 }
    }
}