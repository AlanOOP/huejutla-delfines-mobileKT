package com.example.huejutladelfinesmobile.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huejutladelfinesmobile.model.Schedule
import com.example.huejutladelfinesmobile.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel()
    val scheduleList by homeViewModel.scheduleList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Horarios de Entrenamiento",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(scheduleList) { schedule ->
                ScheduleItem(schedule)
                Divider()
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