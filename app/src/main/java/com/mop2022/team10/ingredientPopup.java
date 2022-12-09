package com.mop2022.team10;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ingredientPopup extends Activity {
    ImageView imageView_popup1;
    TextView due_tv1;
    TextView count_tv1;
    TextView ingrediantname_tv1;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences pref = getSharedPreferences("userId",0);
        userId = pref.getInt("userId", 1);
        setContentView(R.layout.popup_ingredient);
        due_tv1 = (TextView) findViewById(R.id.dialog_due1);
        count_tv1 = (TextView) findViewById(R.id.dialog_count1);
        imageView_popup1 = (ImageView)findViewById(R.id.imageView_popup1);
        ingrediantname_tv1 = (TextView)findViewById(R.id.ingredientname_tv1);
        Intent intent = getIntent();
        int pos = intent.getIntExtra("position",-1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //API호출
                Ingredient ingredient = new Ingredient();
                //---------------유저 호출
                ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(userId);
                //---------------------------
                IngredientModel data = ingredientList.get(pos);
                Bitmap img = ingredient.getImg(ingredientList.get(pos).img);



                //해당 사용자의 식자재 목록 반환
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView_popup1.setImageBitmap(img);
                    }
                });

            }
        });
        t.start();

        String name = intent.getStringExtra("name");
//        Bitmap img = (Bitmap) intent.getParcelableExtra("image");
        double count = intent.getDoubleExtra("count",0);
        String unit = intent.getStringExtra("unit");
        LocalDate experationDate = intent.getParcelableExtra("experationdate");
        int exyear = intent.getIntExtra("year",-1);
        int exmonth  = intent.getIntExtra("month",-1);
        int exday = intent.getIntExtra("day",-1);

        ingrediantname_tv1.setText(name);
        due_tv1.setText(Integer.toString(exyear)+"-"+Integer.toString(exmonth)+"-"+Integer.toString(exday));
        if(unit.equals("개")){
            count_tv1.setText(Integer.toString((int)count)+"개");
        }
        else if(unit.equals("ml")){
            count_tv1.setText(Integer.toString((int)count)+"ml");
        }
        else if(unit.equals("g")){
            count_tv1.setText(Integer.toString((int)count)+"g");
        }
        else if(unit.equals("대")){
            count_tv1.setText(Integer.toString((int)count)+"대");
        }
        else if(unit.equals("봉")){
            count_tv1.setText(Integer.toString((int)count)+"봉");
        }
        else if(unit.equals("모")){
            count_tv1.setText(Integer.toString((int)count)+"모");
        }

        else{
            count_tv1.setText(Double.toString(count));
        }
//        imageView_popup1.setImageBitmap(img);
    }
    public void mOnClose(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

}
