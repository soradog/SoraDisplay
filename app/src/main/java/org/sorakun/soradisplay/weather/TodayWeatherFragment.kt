package org.sorakun.soradisplay.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sorakun.soradisplay.FullscreenActivity
import org.sorakun.soradisplay.databinding.FragmentTodayWeatherBinding
import org.sorakun.soradisplay.weather.visualcrossing.ForecastRecord
import org.sorakun.soradisplay.weather.visualcrossing.ForecastRecordViewModel
import org.sorakun.soradisplay.weather.visualcrossing.IntraDayForecastAdapter

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
open class TodayWeatherFragment(fs : FullscreenActivity) : Fragment() {

    private val activity : FullscreenActivity = fs
    private val forecastAdapter = IntraDayForecastAdapter()
    private lateinit var forecastRecord : ForecastRecord

    private var visible: Boolean = false

    private var fullscreenContent: View? = null

    private var _binding: FragmentTodayWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTodayWeatherBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = forecastAdapter

        val viewModel by activityViewModels<ForecastRecordViewModel>()
        viewModel.get().observe(this.viewLifecycleOwner) {
            onChanged(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visible = true

        fullscreenContent = binding.fullscreenContent
        fullscreenContent?.setOnClickListener { activity.toggle() }

        if (this::forecastRecord.isInitialized) {
            Log.d("TodayWeatherFragment", "onResume: valid forecastRecord proceed with update")
            forecastRecord.updateTodayViews(binding)
        } else {
            Log.e("TodayWeatherFragment", "onResume: forecastRecord not initialized")
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.recyclerView.adapter = forecastAdapter

        if (this::forecastRecord.isInitialized) {
            Log.d("TodayWeatherFragment", "onResume: valid forecastRecord proceed with update")
            forecastRecord.updateTodayViews(binding)
        } else {
            Log.e("TodayWeatherFragment", "onResume: forecastRecord not initialized")
        }
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

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

    fun onChanged(t: ForecastRecord?) {
        if (t != null && t.isReady() == true) {
            Log.d("TodayWeatherFragment", "onChanged: Valid forecastRecord received")
            forecastRecord = t
            val hours = forecastRecord.getForecastedHours()
            forecastAdapter.submitList(hours)
        } else {
            Log.e("TodayWeatherFragment", "onChanged: Invalid forecastRecord received")
        }
    }
}