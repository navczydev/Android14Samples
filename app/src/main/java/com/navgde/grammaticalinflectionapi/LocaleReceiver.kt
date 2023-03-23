package com.navgde.grammaticalinflectionapi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_LOCALE_CHANGED
import android.widget.Toast

class LocaleReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        if (intent.action == ACTION_LOCALE_CHANGED) {
            Toast.makeText(context, "LocaleChanged", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Something else changed", Toast.LENGTH_SHORT).show()
        }
    }
}
