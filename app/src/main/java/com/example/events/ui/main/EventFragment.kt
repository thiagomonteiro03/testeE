package com.example.events.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.example.events.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class EventFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = EventFragment()
    }

    private val viewModel: EventViewModel by viewModels()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, containerEvent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_event, containerEvent, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toolbar: Toolbar = EventFragment.newInstance().requireActivity().findViewById(R.id.toolbar_layout)

        val position = activity?.intent?.getIntExtra("position", 1)
        viewModel.getEvent(position)

        val mapFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(EventFragment.newInstance())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitude = activity?.intent?.getDoubleExtra("latitude",1.0)
        val longitude = activity?.intent?.getDoubleExtra("longitude",1.0)
        var eventLocalAPI = LatLng(1.0,1.0)
        if(latitude!=null&&longitude!=null) eventLocalAPI = LatLng(latitude!!, longitude!!)
        mMap.addMarker(MarkerOptions().position(eventLocalAPI).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocalAPI))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocalAPI, 18F))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}