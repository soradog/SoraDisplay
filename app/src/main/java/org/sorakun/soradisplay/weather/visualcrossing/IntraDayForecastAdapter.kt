package org.sorakun.soradisplay.weather.visualcrossing

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sorakun.soradisplay.R
import org.sorakun.soradisplay.Util
import java.text.ParseException
import java.text.SimpleDateFormat

class IntraDayForecastAdapter :
    ListAdapter<ForecastRecord.Day.Hour, IntraDayForecastAdapter.ViewHolder>(HourDiffCallback) {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val datetime : TextView
        private val icon : ImageView
        private val data1 : TextView
        private val data2 : TextView
        private val context : Context

        init {
            datetime = view.findViewById(R.id.weather_column_date)
            icon = view.findViewById(R.id.weather_column_icon)
            data1 = view.findViewById(R.id.weather_column_valueA)
            data2 = view.findViewById(R.id.weather_column_valueB)
            context = view.context
        }

        fun bind(hour : ForecastRecord.Day.Hour) {
            val printer = SimpleDateFormat("HH:mm")
            val parser = SimpleDateFormat("HH:mm:ss")
            try {
                datetime.text = parser.parse(hour.datetime)?.let { printer.format(it) }
            } catch (e: ParseException) {
                Log.e("IntraDayForecastAdapter", "Unable to parse date: " + hour.datetime)
            }
            ForecastRecord.callPicasso(context, hour.icon, icon)
            data1.text = Util.printF("%d°", hour.temp.toInt())
            data1.setTextColor(Util.getTemperatureColor(hour.temp))
            data2.text = Util.printF("%d%%", hour.precipprob.toInt())
            data2.setTextColor(Util.getChanceOfRainColor(hour.precipprob))
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.weather_column, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val hour = getItem(position)
        holder.bind(hour)
    }
}

object HourDiffCallback : DiffUtil.ItemCallback<ForecastRecord.Day.Hour>() {
    override fun areItemsTheSame(oldItem: ForecastRecord.Day.Hour, newItem: ForecastRecord.Day.Hour): Boolean {
        return oldItem.datetimeEpoch == newItem.datetimeEpoch
    }

    override fun areContentsTheSame(oldItem: ForecastRecord.Day.Hour, newItem: ForecastRecord.Day.Hour): Boolean {
        return oldItem.datetimeEpoch == newItem.datetimeEpoch
    }
}