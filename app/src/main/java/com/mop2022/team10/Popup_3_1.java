package com.mop2022.team10;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Popup_3_1 extends Activity {
    ImageView imageView_popup;
    TextView due_tv;
    TextView count_tv;
    TextView ingrediantname_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_3_1);
        due_tv = (TextView) findViewById(R.id.dialog_due);
        count_tv = (TextView) findViewById(R.id.dialog_count);
        imageView_popup = (ImageView)findViewById(R.id.imageView_popup);
        ingrediantname_tv = (TextView)findViewById(R.id.ingredientname_tv);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Bitmap img = intent.getParcelableExtra("image");
        double count = intent.getDoubleExtra("count",0);
        String unit = intent.getStringExtra("unit");
//        LocalDate experationDate = intent.getParcelableExtra("experationdate");
        int exyear = intent.getIntExtra("year",-1);
        int exmonth  = intent.getIntExtra("month",-1);
        int exday = intent.getIntExtra("day",-1);

        ingrediantname_tv.setText(name);
        due_tv.setText(Integer.toString(exyear)+"-"+Integer.toString(exmonth)+"-"+Integer.toString(exday));
        if(unit.equals("개")){
            count_tv.setText(Integer.toString((int)count)+"개");
        }
        else if(unit.equals("ml")){
            count_tv.setText(Integer.toString((int)count)+"ml");
        }
        else if(unit.equals("g")){
            count_tv.setText(Integer.toString((int)count)+"g");
        }
        else if(unit.equals("대")){
            count_tv.setText(Integer.toString((int)count)+"대");
        }
        else if(unit.equals("봉")){
            count_tv.setText(Integer.toString((int)count)+"봉");
        }
        else if(unit.equals("모")){
            count_tv.setText(Integer.toString((int)count)+"모");
        }

        else{
            count_tv.setText(Double.toString(count));
        }
        imageView_popup.setImageBitmap(img);
    }
    public void mOnClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
