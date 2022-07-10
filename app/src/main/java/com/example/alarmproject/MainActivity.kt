package com.example.alarmproject

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var calendar: Calendar
    private var alarmManager: AlarmManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                ShowTimePicker(context = this@MainActivity)
            }
        }
    }


    @SuppressLint("ShortAlarm", "UnspecifiedImmutableFlag")
    @Composable
    private fun ShowTimePicker(context: Context) {

        val tempCalendar = Calendar.getInstance()
        val hourTime = tempCalendar[Calendar.HOUR_OF_DAY]
        val minuteTime = tempCalendar[Calendar.MINUTE]

        val time = remember {
            mutableStateOf("")
        }
        val timePicker = TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                time.value = "$hour : $minute"
                calendar = Calendar.getInstance()
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.SECOND, 0)
                setAlarm()
            },
            hourTime,
            minuteTime,
            true
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = {
                timePicker.show()
            }) {
                Text(text = "Set time")
            }
            Text(text = "Selected time: ${time.value}")
        }

    }

    private fun setAlarm() {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmClockInfo =
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, getAlarmInfoPendingIntent())
        alarmManager!!.setAlarmClock(alarmClockInfo, getPendingIntent())

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getAlarmInfoPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, AlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}



