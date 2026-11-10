package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PackageReceiver : BroadcastReceiver() {

    companion object {
        private val _packageEvents = MutableSharedFlow<String>(extraBufferCapacity = 5)
        val packageEvents = _packageEvents.asSharedFlow()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return
        val packageName = intent.data?.schemeSpecificPart ?: ""
        _packageEvents.tryEmit("$action:$packageName")
    }
}
