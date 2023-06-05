package org.sorakun.soradisplay

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.*
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import org.json.JSONArray
import org.json.JSONObject
import org.sorakun.soradisplay.databinding.ActivityFullscreenBinding
import org.sorakun.soradisplay.natureremo.*
import org.sorakun.soradisplay.weather.*
import org.sorakun.soradisplay.weather.visualcrossing.ForecastRecordViewModel
import org.sorakun.soradisplay.weather.visualcrossing.GetForecastRunnable
import java.util.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    class PageCountViewModel : ViewModel() {
        private val uiState: MutableLiveData<Int?> = MutableLiveData<Int?>()

        init {
            uiState.value = 0
        }

        fun getNextPage(max : Int) : Int {
            val current : Int = uiState.value!!
            val result = if (current >= max) {
                0
            } else {
                current + 1
            }
            uiState.value = result
            return result
        }
    }

    private lateinit var binding: ActivityFullscreenBinding
    private lateinit var fullscreenContent: ViewPager2
    private lateinit var fragmentAdapter: FragmentAdapter
    private val hideHandler = Handler(Looper.myLooper()!!)

    private val systemBroadcastReceiver = object : SystemBroadcastReceiver() {
        override fun onDockOrBatteryStateChanged(isChargingOrDocked : Boolean) {
            disableScreenSleep (isChargingOrDocked)
        }
    }

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            fullscreenContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            fullscreenContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        //fullscreenContentControls.visibility = View.VISIBLE
    }
    private var isFullscreen: Boolean = false
    private val hideRunnable = Runnable { hide() }
    private lateinit var forecastRunnable : GetForecastRunnable
    private lateinit var devicesRunnable : DevicesRequestRunnable

    private val clockFragment = ClockFragment()
    private val todayFragment = TodayWeatherFragment()
    private val forecastFragment = WeatherForecastFragment()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                hide()
                return true
            }
            R.id.setting_button -> {
                val setting = Intent(this@FullscreenActivity, SettingsActivity::class.java)
                startActivity(setting)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isFullscreen = true

        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = binding.fullscreenContent
        fullscreenContent.setOnClickListener { toggle() }

        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        fragmentAdapter.addFragment(clockFragment)
        fragmentAdapter.addFragment(todayFragment)
        fragmentAdapter.addFragment(forecastFragment)

        fullscreenContent.orientation = ViewPager2.ORIENTATION_VERTICAL
        fullscreenContent.adapter = fragmentAdapter

        // Schedule a timer event to auto flip through the different viewpages
        val handler = Handler()
        val flipPagerRunnable = Runnable {

            val viewModel by viewModels<PageCountViewModel>()
            val nextPage = viewModel.getNextPage(fragmentAdapter.itemCount - 1)
            fullscreenContent.setCurrentItem(nextPage, false)
        }

        // repeat the page flipping on a timer
        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(flipPagerRunnable)
            }
        }, 20000, 20000)


        forecastRunnable = GetForecastRunnable(this)
        forecastRunnable.firstRun()
        devicesRunnable = DevicesRequestRunnable(this)
        devicesRunnable.firstRun()

        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            registerReceiver(systemBroadcastReceiver, ifilter)
        }
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
        disableScreenSleep (isCharging)
    }

    private val preferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { pref, tag ->
            if (pref != null &&
                    tag.contains("natureremo", false)) {
                devicesRunnable.firstRun()
            }
        }

    override fun onResume() {
        super.onResume()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.registerOnSharedPreferenceChangeListener(preferenceListener)
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.registerOnSharedPreferenceChangeListener(preferenceListener)
    }

    private fun disableScreenSleep(chargingOrDocking : Boolean) {
        if (chargingOrDocking) {
            // prevent screen going to sleep if device is charging
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            val toast = Toast.makeText(this, "Device is docked/charging. Screen sleep has been disabled.", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            val toast = Toast.makeText(this, "Device is not charging/undocked. Screen sleep has been enabled.", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun onResponseJSONObject(response: JSONObject?) {
        val viewModel by viewModels<ForecastRecordViewModel>()
        viewModel.set(response)
    }

    fun onResponseJSONArray(response: JSONArray?) {
        if (response != null && response.length() > 0) {
            val viewModel by viewModels<DeviceRecordViewModel>()
            viewModel.set(response.getJSONObject(0))
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
        //hide()
    }

    fun toggle() {
        if (isFullscreen) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        //fullscreenContentControls.visibility = View.GONE
        isFullscreen = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= 30) {
            fullscreenContent.windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            fullscreenContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        isFullscreen = true

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
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
    }
}

