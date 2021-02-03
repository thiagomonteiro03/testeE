package com.example.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.events.databinding.ActivityEventBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityEventBinding
    private val viewModel: EventViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val toolbar : androidx.appcompat.widget.Toolbar = binding.toolbarLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val position = intent.getIntExtra("position", 1)
        viewModel.getEvent(position)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitude = intent.getDoubleExtra("latitude", 1.0)
        val longitude = intent.getDoubleExtra("longitude", 1.0)
        val eventLocalAPI = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(eventLocalAPI).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocalAPI))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocalAPI, 18F))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}