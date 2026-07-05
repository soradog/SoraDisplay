package org.sorakun.soradisplay.weather

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
    ListAdapter<ForecastRecordBase.DayBase.HourBase, IntraDayForecastAdapter.ViewHolder>(HourDiffCallback) {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val datetime : TextView
        private val icon : ImageView
        private val data1 : TextView
        private val data2 : TextView
        private val data3 : TextView
        private val iconV2 : ImageView
        private val iconV3 : ImageView
        private val context : Context

        init {
            datetime = view.findViewById(R.id.weather_full_datetime)
            icon = view.findViewById(R.id.weather_full_icon)
            data1 = view.findViewById(R.id.weather_full_value1)
            data2 = view.findViewById(R.id.weather_full_value2)
            data3 = view.findViewById(R.id.weather_full_value3)
            iconV2 = view.findViewById(R.id.weather_icon_v2)
            iconV3 = view.findViewById(R.id.weather_icon_v3)
            context = view.context
        }

        companion object {
            private val printer = SimpleDateFormat("HH:mm")
            private val parser = SimpleDateFormat("HH:mm:ss")
        }

        fun bind(hour : ForecastRecordBase.DayBase.HourBase) {
            try {
                datetime.text = parser.parse(hour.datetime)?.let { printer.format(it) }
            } catch (e: ParseException) {
                Log.e("SoraDisplay", "IntraDayForecastAdapter:Unable to parse date: " + hour.datetime)
            }
            ServiceFactory.setIcon(context, hour.icon, icon)
            
            data1.text = Util.printF("%d°", hour.temp.toInt())
            data1.setTextColor(Util.getTemperatureColor(hour.temp))
            
            // For intraday, value 2 is rain probability
            iconV2.setImageResource(R.drawable.baseline_water_drop_24)
            data2.text = Util.printF("%d%%", hour.precipprob.toInt())
            data2.setTextColor(Util.getChanceOfRainColor(hour.precipprob))
            
            // For intraday, value 3 is wind speed
            iconV3.setImageResource(R.drawable.visualcrossing_wind)
            data3.text = Util.printF("%dkm", hour.windspeed.toInt())
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.weather_column_full, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val hour = getItem(position)
        holder.bind(hour)
    }
}

object HourDiffCallback : DiffUtil.ItemCallback<ForecastRecordBase.DayBase.HourBase>() {
    override fun areItemsTheSame(
        oldItem: ForecastRecordBase.DayBase.HourBase,
        newItem: ForecastRecordBase.DayBase.HourBase): Boolean {
        return oldItem.datetimeEpoch == newItem.datetimeEpoch
    }

    override fun areContentsTheSame(
        oldItem: ForecastRecordBase.DayBase.HourBase,
        newItem: ForecastRecordBase.DayBase.HourBase): Boolean {
        return oldItem.datetimeEpoch == newItem.datetimeEpoch
    }
}