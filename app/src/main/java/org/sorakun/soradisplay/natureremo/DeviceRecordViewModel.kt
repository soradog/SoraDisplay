package org.sorakun.soradisplay.natureremo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONException
import org.json.JSONObject

class DeviceRecordViewModel : ViewModel() {
    private val uiState: MutableLiveData<DeviceRecord?> = MutableLiveData<DeviceRecord?>()

    init {
        uiState.value = DeviceRecord()
    }

    fun get(): LiveData<DeviceRecord?> {
        return uiState
    }

    @Throws(JSONException::class)
    fun set(record: JSONObject?) {
        uiState.value!!.update(record)
        uiState.postValue(uiState.value)
    }
}