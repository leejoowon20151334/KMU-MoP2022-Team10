package com.mop2022.team10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mop2022.team10.Rest.User;

public class Activity_1 extends AppCompatActivity {

    String ssaid;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);

        SharedPreferences pref = getSharedPreferences("userId", 0);
        SharedPreferences.Editor editor = pref.edit();
        String ssaid = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                    }
                });
            }
        });
        t.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Activity_1.this, Activity_2.class);
                startActivity(intent);
            }
        }, 1500);


    }

}