package com.example.events

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

class EventViewModel : ViewModel(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val service = EventService()
    private val mEvents = MutableLiveData<List<Event>>()


    val events: LiveData<List<Event>>
        get() = mEvents

    fun getEvent(id : Int) : Event{
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getEvents()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    mEvents.value = response.body()
                }
            }
        }
        return mEvents.value!![id]
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}