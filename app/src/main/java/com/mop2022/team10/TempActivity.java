package com.mop2022.team10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        //메인화면(2)
        Button tempButtom_page2 = findViewById(R.id.tempButtom_page2);
        tempButtom_page2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_1.class);
                startActivity(intent);
            }
        });
        //식자재관리
        Button tempButtom_page3 = findViewById(R.id.tempButtom_page3);
        tempButtom_page3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_3.class);
                startActivity(intent);
            }
        });
        //식자재목록(3-1)
        Button tempButtom_page3_1 = findViewById(R.id.tempButtom_page3_1);
        tempButtom_page3_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_3_1.class);
                startActivity(intent);
            }
        });
        //식자재검색(3-2)
        Button tempButtom_page3_2 = findViewById(R.id.tempButtom_page3_2);
        tempButtom_page3_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_3_2.class);
                startActivity(intent);
            }
        });
        //레시피검색(4)
        Button tempButtom_page4 = findViewById(R.id.tempButtom_page4);
        tempButtom_page4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_4.class);
                startActivity(intent);
            }
        });
        //레시피내용(5)
        Button tempButtom_page5 = findViewById(R.id.tempButtom_page5);
        tempButtom_page5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RecipeDetailActivity.class);
                startActivity(intent);
            }
        });
        //레시피추천(6)
        Button tempButtom_page6 = findViewById(R.id.tempButtom_page6);
        tempButtom_page6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_6.class);
                startActivity(intent);
            }
        });
        //내정보(7)
        Button tempButtom_page7 = findViewById(R.id.tempButtom_page7);
        tempButtom_page7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                startActivity(intent);
            }
        });
    }
}