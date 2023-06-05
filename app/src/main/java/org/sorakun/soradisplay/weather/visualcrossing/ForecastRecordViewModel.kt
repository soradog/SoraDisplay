package org.sorakun.soradisplay.weather.visualcrossing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
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
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(ForecastRecord::class.java)
        uiState.value = jsonAdapter.fromJson(record.toString())
    }
}

