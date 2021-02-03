package com.example.events

import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel : ViewModel() {

    private val service = EventService()
    private val mEvents = MutableLiveData<List<Event>>()


    val titleText: MutableLiveData<String> = MutableLiveData()
    val urlText: MutableLiveData<String> = MutableLiveData()
    val sourceText: MutableLiveData<String> = MutableLiveData()
    val resultImageUrl = ObservableField<String>()
    val events: LiveData<List<Event>>
        get() = mEvents

    fun getEvent(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getEvents()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    mEvents.value = response.body()
                    urlText.postValue(mEvents.value!![id].date.toString())

                }
            }
        }
    }

}