package org.sorakun.soradisplay

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.*
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.sorakun.soradisplay.databinding.ActivityFullscreenBinding
import org.sorakun.soradisplay.natureremo.*
import org.sorakun.soradisplay.weather.*
import java.util.*
import kotlin.ConcurrentModificationException


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private val hideHandler = Handler(Looper.myLooper()!!)

    private val systemBroadcastReceiver = object : SystemBroadcastReceiver() {
        override fun onDockOrBatteryStateChanged(isChargingOrDocked : Boolean) {
            disableScreenSleep (isChargingOrDocked)
        }
    }

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        val fullscreenContent = binding.fullscreenContent
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

    private var currentPage = 0

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
        val fullscreenContent = binding.fullscreenContent
        fullscreenContent.setOnClickListener { toggle() }

        val fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)

        fullscreenContent.orientation = ViewPager2.ORIENTATION_VERTICAL
        fullscreenContent.adapter = fragmentAdapter

        setupNatureRemoRestApiRunnable()
        setupWeatherRestApiRunnable()

        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            registerReceiver(systemBroadcastReceiver, ifilter)
        }
        val status: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
        disableScreenSleep (isCharging)
    }

    private fun setupNatureRemoRestApiRunnable() {
        val viewModel by viewModels<DeviceRecordViewModel>()
        val devicesRunnable = DevicesRequestRunnable(this, viewModel)
        devicesRunnable.firstRun()
    }
    private fun setupWeatherRestApiRunnable() {
        val viewModel by viewModels<ForecastRecordViewModel>()
        val forecastRunnable = ServiceFactory.requestRunnable(this, viewModel)
        forecastRunnable.firstRun()

        // Schedule a timer event to auto flip through the different viewpages
        val handler = Handler()
        val flipPagerRunnable = Runnable {

            if (currentPage == FragmentAdapter.MAX_FRAGMENT) {
                currentPage = 0
            }
            // only auto turn to the weather pages if weather api is running
            if (forecastRunnable.isRunning) {
                binding.fullscreenContent.setCurrentItem(currentPage++, false)
            }
        }

        // repeat the page flipping on a timer
        Timer().schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(flipPagerRunnable)
            }
        }, 20000, 20000)
    }

    private val preferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { pref, tag ->
            if (pref != null) {
                if (tag.contains("natureremo", false)) {
                    setupNatureRemoRestApiRunnable()
                }
                else if (tag.contains("weather", false)) {
                    setupWeatherRestApiRunnable()
                }
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
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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
        val fullscreenContent = binding.fullscreenContent
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

