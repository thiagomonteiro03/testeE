package com.example.events

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class EventViewModel : ViewModel(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val service = EventService()
    private val mEvents = MutableLiveData<List<Event>>()
    private val _date = MutableLiveData<String>()
    private val _title = MutableLiveData<String>()
    private val _price = MutableLiveData<String>()
    private val _description = MutableLiveData<String>()
    private val _latitude = MutableLiveData<Double>()
    private val _longitude = MutableLiveData<Double>()
    private val _image = ObservableField<String>()

    val events: LiveData<List<Event>>
        get() = mEvents
    val date: LiveData<String>
        get() = _date
    val title: LiveData<String>
        get() = _title
    val price: LiveData<String>
        get() = _price
    val description: LiveData<String>
        get() = _description
    private val latitude: LiveData<Double>
        get() = _latitude
    private val longitude: LiveData<Double>
        get() = _longitude
    val image: ObservableField<String>
        get() = _image


    fun getEvent(id : Int){
        viewModelScope.launch {
            val response = service.getEvents()

            if (response.isSuccessful) {
                    mEvents.value = response.body()
                val event = mEvents.value!![id]
                _date.value = getDateTime(event.date)
                _title.value = event.title
                _price.value = getPrice(event.price)
                _description.value = event.description
                _longitude.value = event.longitude
                _latitude.value = event.latitude
                image.set(event.image)
            }
        }
    }

    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat(" dd/MM/yyyy  HH:mm")
            val netDate = Date(1000 * s)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun getPrice(p: Double) : String{
        val dec = DecimalFormat("#,###.00")
        var credits = "R$ " + dec.format(p)
        credits = credits.replace(".", ",")

        return credits
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val eventLocalAPI = LatLng(2.0, 2.0)
        mMap.addMarker(MarkerOptions().position(eventLocalAPI).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocalAPI))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocalAPI, 18F))
    }

}