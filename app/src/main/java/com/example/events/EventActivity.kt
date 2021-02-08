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


    private val viewModel: EventViewModel by viewModels()
    private lateinit var binding: ActivityEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_event, EventFragment.newInstance())
                .commitNow()
        }

        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getEvent(intent.getIntExtra("position",1))

    }
}