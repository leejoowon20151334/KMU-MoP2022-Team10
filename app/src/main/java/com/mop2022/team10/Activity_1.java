package com.mop2022.team10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mop2022.team10.Rest.User;

public class Activity_1 extends AppCompatActivity {

    String ssaid;
    String userName;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);

        SharedPreferences pref = getSharedPreferences("userId", 0);
        SharedPreferences.Editor editor = pref.edit();
        ssaid = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                if(user.signUp(ssaid) == 0) {
                    userId = 1;
                    userName = "test";
                }
                else{
                    userId = user.getUserId(ssaid);
                    userName = ssaid;
                }
            }
        });
        t.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editor.putInt("userId",userId);
                editor.putString("userName",userName);
                editor.apply();
                Intent intent = new Intent(Activity_1.this, Activity_2.class);
                startActivity(intent);
                finish();
            }
        }, 1500);


    }

}