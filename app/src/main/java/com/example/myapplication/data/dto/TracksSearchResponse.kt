package com.example.myapplication.data.dto

import com.example.myapplication.data.network.BaseResponse

class TracksSearchResponse(
    val results: List<TrackDto>
) : BaseResponse()