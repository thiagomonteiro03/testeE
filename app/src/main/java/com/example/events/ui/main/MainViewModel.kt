package com.example.events.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.events.Event
import com.example.events.EventService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val service = EventService()
    private val mEvents = MutableLiveData<List<Event>>()

    val events: LiveData<List<Event>>
        get() = mEvents

    fun fetchEvents(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getEvents()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    mEvents.value = response.body()
                }
            }
        }
    }
}