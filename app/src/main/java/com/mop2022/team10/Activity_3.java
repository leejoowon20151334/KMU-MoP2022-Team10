package com.mop2022.team10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.widget.Button;

public class Activity_3 extends AppCompatActivity {

    ImageButton backBtn;
    AppCompatButton addBtn;
    AppCompatButton roomTmpBtn;
    AppCompatButton refrigerationBtn;
    AppCompatButton frozenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        backBtn = (ImageButton) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_2.class);
                startActivityForResult(intent, 0);
            }
        });

        addBtn = (AppCompatButton) findViewById(R.id.addBtn);
        roomTmpBtn = (AppCompatButton) findViewById(R.id.roomTmp);
        refrigerationBtn = (AppCompatButton) findViewById(R.id.refrigeration);
        frozenBtn = (AppCompatButton) findViewById(R.id.frozen);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_3_2.class);
                startActivityForResult(intent, 0);
            }
        });

        roomTmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_3_1.class);
                startActivityForResult(intent, 0);
            }
        });

        refrigerationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_3_1.class);
                startActivityForResult(intent, 0);
            }
        });

        frozenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_3_1.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Activity_2.class);
        startActivity(intent);
        finish();
    }
}
