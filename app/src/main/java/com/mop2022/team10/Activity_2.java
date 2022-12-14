package com.mop2022.team10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class Activity_2 extends AppCompatActivity {

    ImageButton ManagementBtn;

    private final long finishtimeed = 1000;
    private long presstime = 0;
    int userId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        SharedPreferences pref = getSharedPreferences("userId",0);
        userId = pref.getInt("userId",1);
        userName = pref.getString("userName","test");

        ManagementBtn = (ImageButton) findViewById(R.id.gotoManagement);

        ManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_3.class);
                startActivity(intent);
                finish();
            }
        });

        /*
        레시피 추천
        랜덤으로 레시피 중 하나를 선택하여 사진과 이름 표시
         */
        ImageButton recomRecipe = (ImageButton) findViewById(R.id.recommendedImg);
        TextView recomText = (TextView) findViewById(R.id.recommendText);

        /*
        유통기한이 가장 적게 남은 식자재 표시
         */
        ImageButton shortExpirationDateBtn = (ImageButton) findViewById(R.id.shortExpirationDate);
        TextView nameOfSEDFood = (TextView) findViewById(R.id.nameOfSEDFood);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Ingredient ingredient = new Ingredient();
                ArrayList<IngredientModel> userIngredient = ingredient.userIngredient(userId);
                Recipe recipe = new Recipe();
                RecipeModel result;
                Random randomN = new Random();

                //랜덤으로 레시피 아이디를 선정
                int id;
                while(true){
                    id = randomN.nextInt(50);
                    if(recipe.getRecipeDetail(id).name != null)
                        break;
                }
                result = recipe.getRecipeDetail(id);
                Bitmap img = recipe.getImg(result.img);
                String txt = "오늘 메뉴는 " + result.name + " 어때요?";

                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        //기본 식자재를 ArrayList의 0번 인덱스로 설정
                        LocalDate minExpirationDate;
                        if(userIngredient.size() > 0) {
                            minExpirationDate = userIngredient.get(0).expirationDate;
                            nameOfSEDFood.setText(userIngredient.get(0).name);
                            shortExpirationDateBtn.setImageBitmap(ingredient.getImg(userIngredient.get(0).img));
                        }
                        else{
                            minExpirationDate = LocalDate.MIN;
                            nameOfSEDFood.setText("없음");
                        }

                        //남은 유통기한이 가장 짧은 기간의 식자재 구하기
                        for(int i=0; i<userIngredient.size(); i++){
                            if(minExpirationDate.isBefore(userIngredient.get(i).expirationDate)) {
                                minExpirationDate = userIngredient.get(i).expirationDate;
                                nameOfSEDFood.setText(userIngredient.get(i).name);
                                shortExpirationDateBtn.setImageBitmap(ingredient.getImg(userIngredient.get(i).img));
                            }
                        }
                        recomRecipe.setImageBitmap(img);
                        recomText.setText(txt);
                    }
                });
            }
        });
        t.start();


        /*
----------------------------------------------------------------------------------------------------------------------------------------------------
        다른 페이지로 이동되는 버튼들 자바코드
         */


        /*
        4.레시피 검색으로 이동
         */
        ImageButton searchBtn = (ImageButton) findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_4.class);
                startActivity(intent);
                finish();
            }
        });



        /*
        6.레시피 추천 페이지로 이동
         */

        Button gotoRecom = (Button) findViewById(R.id.gotorecom);

        gotoRecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_6.class);
                startActivity(intent);
                finish();
            }
        });


        /*
        7.내 정보로 이동
         */

        ImageButton goToMyInfo = (ImageButton) findViewById(R.id.myInfo);

        goToMyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_2.this, UserInfo.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed(){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - presstime;

        if(0<=intervalTime && finishtimeed >= intervalTime) {
            finish();
        }
        else{
            presstime = tempTime;
            Toast.makeText(getApplicationContext(),"한번 더 누르시면 앱이 종료됩니다",Toast.LENGTH_SHORT).show();
        }
    }
}
