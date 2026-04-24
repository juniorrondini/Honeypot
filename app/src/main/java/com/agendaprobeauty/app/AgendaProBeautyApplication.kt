package com.agendaprobeauty.app

import android.app.Application
import com.agendaprobeauty.app.core.di.AppContainer

class AgendaProBeautyApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
