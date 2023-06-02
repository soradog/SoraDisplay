package org.sorakun.soradisplay.weather.weatherapicom

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
import org.sorakun.soradisplay.weather.weatherapicom.ForecastRecord
import java.text.ParseException
import java.text.SimpleDateFormat

class IntraDayForecastAdapter () :
    ListAdapter<ForecastRecord.Hour, IntraDayForecastAdapter.ViewHolder>(HourDiffCallback) {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val datetime : TextView
        private val icon : ImageView
        private val data1 : TextView
        private val data2 : TextView

        init {
            datetime = view.findViewById(R.id.weather_column_date)
            icon = view.findViewById(R.id.weather_column_icon)
            data1 = view.findViewById(R.id.weather_column_valueA)
            data2 = view.findViewById(R.id.weather_column_valueB)
        }

        fun bind(hour : ForecastRecord.Hour) {
            val printer = SimpleDateFormat("HH:mm")
            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm")
            try {
                datetime.text = parser.parse(hour.time)?.let { printer.format(it) }
            } catch (e: ParseException) {
                Log.e("IntraDayForecastAdapter", "Unable to parse date: " + hour.time)
            }
            ForecastRecord.callPicasso(hour.condition.icon, icon)
            data1.text = Util.printF("%d°", hour.tempC.toInt())
            data1.setTextColor(Util.getTemperatureColor(hour.tempC))
            val rainOrSnow: Double =
                if (hour.chanceOfSnow > 0.0) hour.chanceOfSnow else hour.chanceOfRain
            data2.text = Util.printF("%d%%", rainOrSnow.toInt())
            data2.setTextColor(Util.getChanceOfRainColor(rainOrSnow))
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

object HourDiffCallback : DiffUtil.ItemCallback<ForecastRecord.Hour>() {
    override fun areItemsTheSame(oldItem: ForecastRecord.Hour, newItem: ForecastRecord.Hour): Boolean {
        return oldItem.timeEpoch == newItem.timeEpoch
    }

    override fun areContentsTheSame(oldItem: ForecastRecord.Hour, newItem: ForecastRecord.Hour): Boolean {
        return oldItem.timeEpoch == newItem.timeEpoch
    }
}