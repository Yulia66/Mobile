package com.example.myapplication.domain.api

import com.example.myapplication.data.network.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}