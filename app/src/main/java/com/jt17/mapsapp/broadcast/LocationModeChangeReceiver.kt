package com.jt17.mapsapp.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast

class LocationModeChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == LocationManager.MODE_CHANGED_ACTION) {
            val locationM = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locationMode = Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )

            when (locationMode) {
                Settings.Secure.LOCATION_MODE_OFF -> Toast.makeText(
                    context,
                    "location is off!",
                    Toast.LENGTH_SHORT
                ).show()

                Settings.Secure.LOCATION_MODE_SENSORS_ONLY -> Toast.makeText(
                    context,
                    "location is sensors only!",
                    Toast.LENGTH_SHORT
                ).show()

                Settings.Secure.LOCATION_MODE_BATTERY_SAVING -> Toast.makeText(
                    context,
                    "location in battery save mode!!",
                    Toast.LENGTH_SHORT
                ).show()

                Settings.Secure.LOCATION_MODE_HIGH_ACCURACY -> Toast.makeText(
                    context,
                    "location is high accuracy!!!",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }

    }

}