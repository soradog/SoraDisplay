package org.sorakun.soradisplay.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.sorakun.soradisplay.FullscreenActivity
import org.sorakun.soradisplay.databinding.FragmentNextFewHoursWeatherBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
open class NextFewHoursWeatherFragment() : Fragment() {

    private var visible: Boolean = false

    private var _binding: FragmentNextFewHoursWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNextFewHoursWeatherBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val forecastAdapter = IntraDayForecastAdapter()
        binding.recyclerView.adapter = forecastAdapter

        val viewModel by activityViewModels<ForecastRecordViewModel>()
        viewModel.get().observe(this.viewLifecycleOwner) {
            if (it?.isReady() == true) {
                val hours = it.getForecastedHours()
                forecastAdapter.submitList(hours)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visible = true

        val fullscreenContent = binding.fullscreenContent
        val parentActivity : FullscreenActivity = activity as FullscreenActivity
        fullscreenContent.setOnClickListener { parentActivity.toggle() }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}