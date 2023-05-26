package org.sorakun.soradisplay.weatherapi

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

class WeeklyForecastAdapter () :
    ListAdapter<ForecastRecord.Forecastday, WeeklyForecastAdapter.ViewHolder>(ForecastDayDiffCallback) {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val date : TextView
        private val icon : ImageView
        private val data1 : TextView
        private val data2 : TextView
        private val data3 : TextView
        //private val data4 : TextView
        //private val data5 : TextView

        init {
            date = view.findViewById(R.id.weather_full_datetime)
            icon = view.findViewById(R.id.weather_full_icon)
            data1 = view.findViewById(R.id.weather_full_value1)
            data2 = view.findViewById(R.id.weather_full_value2)
            data3 = view.findViewById(R.id.weather_full_value3)
            //data4 = view.findViewById(R.id.weather_full_value4)
            //data5 = view.findViewById(R.id.weather_full_value5)
        }

        fun bind(fd : ForecastRecord.Forecastday) {
            val printer = SimpleDateFormat("dd\nEE")
            val parser = SimpleDateFormat("yyyy-MM-dd")
            try {
                date.text = parser.parse(fd.date)?.let { printer.format(it) }
            } catch (e: ParseException) {
            }
            //DownloadImageTask(icon).execute(fd.day.condition.icon)
            Util.callPicasso(fd.day.condition.icon, icon)
            data1.text = Util.printF("%d°", fd.day.maxtempC.toInt())
            data1.setTextColor(Util.getTemperatureColor(fd.day.maxtempC))
            data2.text = Util.printF("%d°", fd.day.mintempC.toInt())
            data2.setTextColor(Util.getTemperatureColor(fd.day.mintempC))
            val rainOrSnow: Double =
                if (fd.day.dailyChanceOfSnow > 0.0) fd.day.dailyChanceOfSnow else fd.day.dailyChanceOfRain
            data3.text = Util.printF("%d%%", rainOrSnow.toInt())
            data3.setTextColor(Util.getChanceOfRainColor(rainOrSnow))
            //data4.text = Util.printF("%d%%", fd.day.avghumidity.toInt())
            //data4.setTextColor(Util.getHumidityColor(fd.day.avghumidity))
            //data5.text = Util.printF("%d", fd.day.maxwindKph.toInt())
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
        val day = getItem(position)
        holder.bind(day)
    }
}

object ForecastDayDiffCallback : DiffUtil.ItemCallback<ForecastRecord.Forecastday>() {
    override fun areItemsTheSame(oldItem: ForecastRecord.Forecastday, newItem: ForecastRecord.Forecastday): Boolean {
        return oldItem.dateEpoch == newItem.dateEpoch
    }

    override fun areContentsTheSame(oldItem: ForecastRecord.Forecastday, newItem: ForecastRecord.Forecastday): Boolean {
        return oldItem.dateEpoch == newItem.dateEpoch
    }
}