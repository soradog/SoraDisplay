package org.sorakun.soradisplay.natureremo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sorakun.soradisplay.FullscreenActivity
import org.sorakun.soradisplay.SystemBroadcastReceiver
import org.sorakun.soradisplay.Util
import org.sorakun.soradisplay.databinding.FragmentClockBinding
import java.util.*

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ClockFragment() : Fragment() {

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

        fullscreenContent = binding.linear
        dateDisplay = binding.textDateDisplay
        tempDisplay = binding.sensorTemperature
        humidDisplay = binding.sensorHumidity
        locationLabel = binding.remoLocation

        val parentActivity : FullscreenActivity = activity as FullscreenActivity
        fullscreenContent?.setOnClickListener { parentActivity.toggle() }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        activity?.registerReceiver(systemBroadcastReceiver,
            SystemBroadcastReceiver.getIntentFilter()
        )
        dateDisplay.text = systemBroadcastReceiver.printDate(Date())
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
/*
    private val hideHandler = Handler(Looper.myLooper()!!)
    @Suppress("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        val flags =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity?.window?.decorView?.systemUiVisibility = flags
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        //fullscreenContentControls?.visibility = View.VISIBLE
    }
    private var visible: Boolean = false
    private val hideRunnable = Runnable { hide() }

    private fun toggle() {
        if (visible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        //fullscreenContentControls?.visibility = View.GONE
        visible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    @Suppress("InlinedApi")
    private fun show() {
        // Show the system bar
        fullscreenContent?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        visible = true

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }*/
}