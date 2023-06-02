package org.sorakun.soradisplay.weather.weatherapicom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONException
import org.json.JSONObject

class ForecastRecordViewModel : ViewModel() {
    private val uiState: MutableLiveData<ForecastRecord?> = MutableLiveData<ForecastRecord?>()

    init {
        uiState.value = ForecastRecord()
    }

    fun get(): LiveData<ForecastRecord?> {
        return uiState
    }

    @Throws(JSONException::class)
    fun set(record: JSONObject?) {
        uiState.value!!.update(record)
    }
}
