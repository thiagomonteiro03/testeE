package com.example.events

import retrofit2.Response
import retrofit2.http.GET

interface EventAPI {
    @GET ("events/")
    suspend fun all() : Response<List<Event>>
}