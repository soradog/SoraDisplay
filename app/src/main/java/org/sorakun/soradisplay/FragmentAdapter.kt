package org.sorakun.soradisplay

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.sorakun.soradisplay.natureremo.ClockFragment
import org.sorakun.soradisplay.weather.NextFewHoursWeatherFragment
import org.sorakun.soradisplay.weather.TodayWeatherFragment
import org.sorakun.soradisplay.weather.WeatherForecastFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment = ClockFragment()
        when (position) {
            1 -> fragment = TodayWeatherFragment()
            2 -> fragment = NextFewHoursWeatherFragment()
            3 -> fragment = WeatherForecastFragment()
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return MAX_FRAGMENT
    }
    companion object {
        const val MAX_FRAGMENT = 4
    }
}
