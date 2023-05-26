package org.sorakun.soradisplay.weatherapi

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sorakun.soradisplay.databinding.FragmentWeatherForecastBinding
import kotlin.Boolean
import kotlin.Suppress

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WeatherForecastFragment : Fragment() {
    private val forecastAdapter = WeeklyForecastAdapter()
    private lateinit var forecastRecord : ForecastRecord

    private var visible: Boolean = false

    private var fullscreenContent: View? = null

    private var _binding: FragmentWeatherForecastBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeatherForecastBinding.inflate(inflater, container, false)
        binding.weeklyRecycler.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        binding.weeklyRecycler.adapter = forecastAdapter

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

        if (this::forecastRecord.isInitialized) {
            forecastRecord.updateFutureViews(binding)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        if (this::forecastRecord.isInitialized) {
            forecastRecord.updateFutureViews(binding)
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

        if (t != null) {
            forecastRecord = t
            val days = forecastRecord.forecast.forecastday
            forecastAdapter.submitList(days.asList() as MutableList<ForecastRecord.Forecastday>)
        }
    }
}