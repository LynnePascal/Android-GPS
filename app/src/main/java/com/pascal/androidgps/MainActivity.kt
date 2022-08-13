package com.pascal.androidgps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), LocationListener {
    private lateinit var txtLat:TextView
    private lateinit var txtLong:TextView
    private lateinit var txtSpeed:TextView
    private lateinit var txtAlt:TextView

    private var locationPermissionCode=880

    private lateinit var locationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtLat =findViewById(R.id.txtLat)
        txtLong =findViewById(R.id.txtLong)
        txtSpeed =findViewById(R.id.txtSpeed)
        txtAlt =findViewById(R.id.txtAlt)
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }

        val gpsStatus= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!gpsStatus){
            val intent=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000, 5f, this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(loc: Location) {
        txtLat.text= "Lat ${loc.latitude}"
        txtLong.text= "Long ${loc.longitude}"
        txtSpeed.text= "${loc.latitude}m/s"
        txtAlt.text= "Alt ${loc.altitude}"
    }

}
