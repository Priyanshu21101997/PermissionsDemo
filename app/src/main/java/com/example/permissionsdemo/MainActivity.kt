package com.example.permissionsdemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRequestPermission = findViewById<Button>(R.id.btnReqPermissions)

        btnRequestPermission.setOnClickListener{
            requestPermissions()
        }
    }



    private fun hasWriteExternalStoragePermissions() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED


    private fun hasLocationForegroundPermissions() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermissions() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    // Make a function to check which permission are not given tat the moment . Add those permissions
    // in a mutable list and ask them specifically next time
    private fun requestPermissions(){
        var permissionToRequest = mutableListOf<String>()

        if(!hasWriteExternalStoragePermissions()){
            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!hasLocationForegroundPermissions()){
            permissionToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
//        if(!hasLocationBackgroundPermissions()){
//            permissionToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }

        if(permissionToRequest.isNotEmpty()){
            // Asking for permissions
            // Different code is used for different set of permissions asking by us
            ActivityCompat.requestPermissions(this,permissionToRequest.toTypedArray(),0)
        }

        Log.d("Permissions","{${permissionToRequest.size}}")

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if(grantResults[i]== PackageManager.PERMISSION_GRANTED){
                    Log.d("Permissions","${permissions[i]} is granted")
                }
            }
        }
    }

}