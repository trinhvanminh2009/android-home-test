package com.example.minh.android_home_test.config;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class PermissionRequest {
    private Activity activity;

    public PermissionRequest(Activity activity) {
        this.activity = activity;
    }

    public void checkInternet(){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_NETWORK_STATE)){
                Toast.makeText(activity, "Please grant permission", Toast.LENGTH_LONG).show();
            }else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        Config.PERMISSION_REQUEST_INTERNET);

            }
        }else{
            Log.d("Minh", "Permission granted");
        }

    }


}
