package org.sorakun.soradisplay.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sorakun.soradisplay.FullscreenActivity
import org.sorakun.soradisplay.Util
import org.sorakun.soradisplay.databinding.FragmentTodayWeatherBinding

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
open class TodayWeatherFragment() : Fragment() {

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

        val viewModel by activityViewModels<ForecastRecordViewModel>()
        viewModel.get().observe(this.viewLifecycleOwner) {
            if (it?.isReady() == true) {
                updateTodayViews(it)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visible = true

        fullscreenContent = binding.fullscreenContent
        val parentActivity : FullscreenActivity = activity as FullscreenActivity
        fullscreenContent?.setOnClickListener { parentActivity.toggle() }
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

    override fun onDestroy() {
        super.onDestroy()
        fullscreenContent = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateTodayViews(record : ForecastRecordBase) {
        val currentConditions = record.getCurrentConditions()
        binding.dayLocation.text = record.address
        binding.temperature.text = Util.printF("%d°c", currentConditions.temp.toInt())
        binding.temperature.setTextColor(Util.getTemperatureColor(currentConditions.temp))
        binding.condition.text = Util.printF("%s", currentConditions.conditions)
        binding.currentIcon.adjustViewBounds = true
        binding.currentIcon.minimumWidth = 60
        binding.currentIcon.minimumHeight = 60
        //new Util.DownloadImageTask(binding.currentIcon).execute(current.condition.icon);
        ServiceFactory.setIcon(context, currentConditions.icon, binding.currentIcon)
        binding.label1.text = "Feels:"
        binding.value1.text = Util.printF("%d°c", currentConditions.feelslike.toInt())
        binding.value1.setTextColor(Util.getTemperatureColor(currentConditions.feelslike))
        binding.label2.text = "Humidity:"
        binding.value2.text = Util.printF("%d%%", currentConditions.humidity.toInt())
        binding.value2.setTextColor(Util.getHumidityColor(currentConditions.humidity))
        //binding.label3.setText("Rain:");
        //binding.value3.setText(Util.printF("%dmm", current.precipMm.intValue()));
        binding.label3.text = "Wind (km/h):"
        binding.value3.text = Util.printF(
            "%d",
            currentConditions.windspeed.toInt()
        )
    }
}