package com.example.events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.events.databinding.ActivityEventBinding
import com.example.events.databinding.ActivityEventBindingImpl
import com.example.events.ui.main.EventFragment
import com.example.events.ui.main.EventViewModel
import com.google.android.gms.maps.GoogleMap

class EventActivity : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private val viewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_event, EventFragment.newInstance())
                .commitNow()
        }

        val binding : ActivityEventBinding = DataBindingUtil.setContentView(
            this,
            R.layout.event_activity2_activity)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getEvent(intent.getIntExtra("position",1))

    }
}