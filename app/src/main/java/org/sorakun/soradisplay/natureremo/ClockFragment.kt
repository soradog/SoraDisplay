package org.sorakun.soradisplay.natureremo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sorakun.soradisplay.SystemBroadcastReceiver
import org.sorakun.soradisplay.Util
import org.sorakun.soradisplay.databinding.FragmentClockBinding
import java.util.*

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ClockFragment : Fragment() {

    private val systemBroadcastReceiver = object : SystemBroadcastReceiver() {

        override fun onDayChanged(format: String) {
            dateDisplay.text = format
        }
    }

    private var fullscreenContent: View? = null
    private lateinit var dateDisplay: TextView
    private lateinit var tempDisplay: TextView
    private lateinit var humidDisplay: TextView
    private lateinit var locationLabel: TextView

    private var _binding: FragmentClockBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClockBinding.inflate(inflater, container, false)

        // use activityViewModels to access the shared view model object
        // observe its changes and update the views
        val viewModel by activityViewModels<DeviceRecordViewModel>()
        viewModel.get().observe(this.viewLifecycleOwner) {
            updateViews(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullscreenContent = binding.simpleDigitalClock
        dateDisplay = binding.textDateDisplay
        tempDisplay = binding.sensorTemperature
        humidDisplay = binding.sensorHumidity
        locationLabel = binding.remoLocation
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        activity?.registerReceiver(systemBroadcastReceiver,
            SystemBroadcastReceiver.getIntentFilter()
        )
        dateDisplay.text = systemBroadcastReceiver.printDate(Date())
    }

    private fun updateViews(device : DeviceRecord?) {
        if (device != null && device.isReady) {
            // update the views according to device data
            tempDisplay.text = String.format(
                Locale.getDefault(),
                "%d°c", device.temperature.toInt()
            )
            tempDisplay.setTextColor(Util.getTemperatureColor(device.temperature))
            humidDisplay.text = String.format(
                Locale.getDefault(),
                "%d%%", device.humidity.toInt()
            )
            humidDisplay.setTextColor(Util.getHumidityColor(device.humidity))
            locationLabel.text = String.format(
                Locale.getDefault(), "Sensor: %s", device.name
            )
        }
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        activity?.unregisterReceiver(systemBroadcastReceiver)

        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        fullscreenContent = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}