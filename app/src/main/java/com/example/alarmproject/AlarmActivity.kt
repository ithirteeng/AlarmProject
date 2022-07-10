package com.example.alarmproject

import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmproject.ui.theme.AlarmProjectTheme

class AlarmActivity : ComponentActivity() {

    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmProjectTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ALARM",
                        fontSize = 50.sp
                    )
                    Button(onClick = {
                        if (ringtone != null && ringtone!!.isPlaying) {
                            ringtone!!.stop()
                        }
                        intentToMainActivity()
                    }) {
                        Text(text = "stop ringtone")
                    }
                }
            }
        }


        var notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        setRingtone(notificationUri)

        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            setRingtone(notificationUri)
        }

        if (ringtone != null) {
            ringtone!!.play()
        }


    }

    @Composable
    private fun ShowSnackbar() {
        Snackbar(
            elevation = 6.dp,
            modifier = Modifier.padding(10.dp)
        ) {
            Button(
                onClick = {
                    if (ringtone != null && ringtone!!.isPlaying) {
                        ringtone!!.stop()
                    }
                    intentToMainActivity()
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "STOP")
            }
        }
    }

    private fun setRingtone(notificationUri: Uri) {
        ringtone = RingtoneManager.getRingtone(this, notificationUri)
    }

    override fun onDestroy() {
        if (ringtone != null && ringtone!!.isPlaying) {
            ringtone!!.stop()
        }
        super.onDestroy()
    }

    private fun intentToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }


}

