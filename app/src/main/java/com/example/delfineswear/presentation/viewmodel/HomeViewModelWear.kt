package com.example.delfineswear.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.delfineswear.presentation.modelo.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.IOException

class HomeViewModelWear(application: Application) : AndroidViewModel(application) {

    private val _scheduleList = MutableStateFlow<List<Schedule>>(emptyList())
    val scheduleList: StateFlow<List<Schedule>> = _scheduleList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val client = OkHttpClient()

    init {
        fetchSchedules()
    }

    private fun fetchSchedules() {
        viewModelScope.launch {
            val request = Request.Builder()
                .url("https://huejutla-delfines-mern-backend.vercel.app/api/schedule/7")
                .build()

            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    if (responseBody != null) {
                        val jsonArray = JSONArray(responseBody)
                        val schedules = mutableListOf<Schedule>()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val schedule = Schedule(
                                _id = jsonObject.getString("_id"),
                                day = jsonObject.getString("day"),
                                hour = jsonObject.getString("hour")
                            )
                            schedules.add(schedule)
                        }
                        _scheduleList.value = schedules
                    }
                } else {
                    // Manejar error de solicitud
                    _errorMessage.value = "Error al obtener horarios ${response.code()}"
                }
            } catch (e: IOException) {
                e.printStackTrace()
                // Manejar error de conexión
                _errorMessage.value = "Error de conexión ${e.message}"
            }
        }
    }
}