package com.agendaprobeauty.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.agendaprobeauty.app.core.navigation.AppNavHost
import com.agendaprobeauty.app.core.ui.theme.AgendaProBeautyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as AgendaProBeautyApplication).container
        setContent {
            AgendaProBeautyTheme {
                AppNavHost(appContainer = appContainer)
            }
        }
    }
}
