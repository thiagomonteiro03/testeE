package com.example.events

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_event.view.*
import java.sql.Date
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class EventAdapter(private val items: ArrayList<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private val eventName = view.event_name
        val eventPrice = view.event_price!!
        override fun onClick(view: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                Log.i("clickTest: ", "Position $position as clicked.")
                val intent = Intent(view?.context, EventActivity::class.java)
                intent.putExtra("position", position)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.context?.startActivity(intent)
                } else {
                    //Go to activity API 19.
                }
            }
        }

        val eventDate = view.event_date!!
        val eventImage = view.event_image!!

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(event: Event) {
            eventName.text = event.title
        }

    }

    fun update(events: List<Event>) {
        items.clear()
        items.addAll(events)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        Glide.with(holder.eventImage.context).load(items[position].image).transition(withCrossFade())
            .placeholder(R.drawable.ic_launcher_foreground).apply(RequestOptions.noAnimation())
            .into(holder.eventImage)
        holder.eventPrice.text = getPrice(items[position].price)
        holder.eventDate.text = getDateTime(items[position].date)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fragment_event, parent, false)
    )



    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(1000*s)
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




}

