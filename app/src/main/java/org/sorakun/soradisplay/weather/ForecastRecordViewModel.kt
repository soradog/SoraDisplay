package org.sorakun.soradisplay.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONException
import org.json.JSONObject

class ForecastRecordViewModel : ViewModel() {
    private val uiState: MutableLiveData<ForecastRecordBase?> = MutableLiveData<ForecastRecordBase?>()

    init {
        uiState.value = ServiceFactory.initForecastRecord()
    }

    fun get(): LiveData<ForecastRecordBase?> {
        return uiState
    }

    @Throws(JSONException::class)
    fun set(record: JSONObject?) {
        uiState.value = ServiceFactory.forecastRecordFromJSON(record)
    }
}

