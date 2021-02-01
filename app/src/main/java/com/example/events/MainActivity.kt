package com.example.events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    val eventAdapter = EventAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)
            .get(MainViewModel::class.java)

        event_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }

        viewModel.fetchEvents()

        viewModel.events.observe(this, Observer {countries ->
            countries?.let {
                eventAdapter.update(it)
            }
        })
    }
}