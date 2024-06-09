package com.example.delfineswear.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.delfineswear.presentation.modelo.Schedule
import com.example.delfineswear.presentation.viewmodel.HomeViewModelWear

@Composable
fun HomeScreenWear(homeViewModel: HomeViewModelWear) {
    val scheduleList by homeViewModel.scheduleList.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Horarios de Entrenamiento",
                style = MaterialTheme.typography.title2,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(scheduleList) { schedule ->
                    ScheduleItem(schedule)

                }
            }
        }
    }
}

@Composable
fun ScheduleItem(schedule: Schedule) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Día: ${schedule.day}", style = MaterialTheme.typography.body1)
        Text(text = "Hora: ${schedule.hour}", style = MaterialTheme.typography.body2)
    }
}