package org.sorakun.soradisplay.weather

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

class WeeklyForecastAdapter () :
    ListAdapter<ForecastRecordBase.DayBase, WeeklyForecastAdapter.ViewHolder>(ForecastDayDiffCallback) {

    private val printer = SimpleDateFormat("dd (EE)")
    private val parser = SimpleDateFormat("yyyy-MM-dd")

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val date : TextView
        private val icon : ImageView
        private val data1 : TextView
        private val data2 : TextView
        private val data3 : TextView
        private val iconV2 : ImageView
        private val iconV3 : ImageView

        init {
            date = view.findViewById(R.id.weather_full_datetime)
            icon = view.findViewById(R.id.weather_full_icon)
            data1 = view.findViewById(R.id.weather_full_value1)
            data2 = view.findViewById(R.id.weather_full_value2)
            data3 = view.findViewById(R.id.weather_full_value3)
            iconV2 = view.findViewById(R.id.weather_icon_v2)
            iconV3 = view.findViewById(R.id.weather_icon_v3)
        }

        fun bind(fd : ForecastRecordBase.DayBase) {
            try {
                date.text = parser.parse(fd.datetime)?.let { printer.format(it) }
            } catch (e: ParseException) {
                Log.e("SoraDisplay", "WeeklyForecastAdapter: Unable to parse ${fd.datetime}")
            }
            ServiceFactory.setIcon(icon.context, fd.icon, icon)
            
            data1.text = Util.printF("%d°", fd.tempmax.toInt())
            data1.setTextColor(Util.getTemperatureColor(fd.tempmax))
            
            // For weekly, value 2 is min temp
            iconV2.visibility = View.GONE
            data2.text = Util.printF("%d°", fd.tempmin.toInt())
            data2.setTextColor(Util.getTemperatureColor(fd.tempmin))
            data2.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, data2.resources.getDimension(R.dimen.weather_detail_temp_text_size))
            
            // For weekly, value 3 is precipitation
            iconV3.setImageResource(R.drawable.baseline_water_drop_24)
            data3.text = Util.printF("%d%%", fd.precipprob.toInt())
            data3.setTextColor(Util.getChanceOfRainColor(fd.precipprob))
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

object ForecastDayDiffCallback : DiffUtil.ItemCallback<ForecastRecordBase.DayBase>() {
    override fun areItemsTheSame(oldItem: ForecastRecordBase.DayBase, newItem: ForecastRecordBase.DayBase): Boolean {
        return oldItem.datetimeEpoch == newItem.datetimeEpoch
    }

    override fun areContentsTheSame(oldItem: ForecastRecordBase.DayBase, newItem: ForecastRecordBase.DayBase): Boolean {
        return oldItem.datetimeEpoch == newItem.datetimeEpoch
    }
}