package com.example.events

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventService {

    private val api : EventAPI = Retrofit.Builder()
        .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EventAPI::class.java)

    suspend fun getEvents() : Response<List<Event>> = api.all()

}