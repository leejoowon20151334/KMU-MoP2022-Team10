package com.mop2022.team10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;
import com.mop2022.team10.Util.RestUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private Button cameraBtn;

    private int recipeId;
    private TextView titleText;
    private ImageView recipeImg;
    private TextView description;
    private TextView recipeTime;
    private TextView difficulty;
    private LinearLayout ingredients;
    private LinearLayout procedures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(getIntent().hasExtra("recipeId"))
            recipeId = (int) getIntent().getExtras().get("recipeId");
        else
            recipeId = 1;

        titleText = findViewById(R.id.recipeDetail_titleText);
        recipeImg = findViewById(R.id.recipeDetail_recipeImg);
        description = findViewById(R.id.recipeDetail_description);
        recipeTime = findViewById(R.id.recipeDetail_recipeTime);
        difficulty = findViewById(R.id.recipeDetail_difficulty);
        ingredients = findViewById(R.id.recipeDetail_ingredients);
        procedures = findViewById(R.id.recipeDetail_procedures);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                RecipeModel result = recipe.getRecipeDetail(recipeId);
                if(result.id!=recipeId)
                    finish();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.name!=null)
                            titleText.setText(result.name);
                        if(result.img!=null)
                            recipeImg.setImageBitmap(recipe.getImg(result.img));
                        if(result.description!=null)
                            description.setText(result.description);
                        if(result.time > 0)
                            recipeTime.setText(Integer.toString(result.time) + "분");
                        if(result.difficulty > 0){
                            if(result.difficulty < 3)
                                difficulty.setText("쉬워요");
                            else if(result.difficulty < 5)
                                difficulty.setText("약간 쉬워요");
                            else if(result.difficulty < 7)
                                difficulty.setText("할만해요");
                            else if(result.difficulty < 9)
                                difficulty.setText("조금 어려워요");
                            else
                                difficulty.setText("어려워요");
                        }

                        Context context = getApplicationContext();

                        for(IngredientModel ingredient : result.ingredients){

                            LinearLayout ingredientLine = new LinearLayout(context);
                            ingredientLine.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150));

                            //식자재 이미지
                            ImageView ingredientImg = new ImageView(context);
                            LinearLayout.LayoutParams imgParam = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                            imgParam.weight = 1f;
                            ingredientImg.setLayoutParams(imgParam);
                            if(ingredient.img!=null)
                                ingredientImg.setImageBitmap(recipe.getImg(ingredient.img));
                            ingredientLine.addView(ingredientImg);

                            //식자재 이름
                            TextView ingredientName = new TextView(context);
                            LinearLayout.LayoutParams nameParam = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT );
                            nameParam.weight = 2f;
                            ingredientName.setLayoutParams(nameParam);
                            ingredientName.setTextSize(25);
                            ingredientName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            ingredientName.setGravity(Gravity.CENTER);
                            ingredientName.setText(ingredient.name);
                            ingredientLine.addView(ingredientName);

                            //식자재 갯수
                            TextView ingredientCount = new TextView(context);
                            LinearLayout.LayoutParams countParam = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT );
                            countParam.weight = 1f;
                            ingredientCount.setLayoutParams(countParam);
                            ingredientCount.setTextSize(25);
                            ingredientCount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            ingredientCount.setGravity(Gravity.CENTER);
                            ingredientCount.setText(Double.toString(ingredient.count));
                            ingredientLine.addView(ingredientCount);

                            LinearLayout margin = new LinearLayout(context);
                            margin.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50));

                            ingredients.addView(ingredientLine);
                            ingredients.addView(margin);
                        }

                        int i=1;
                        for(String procedureStr : result.procedure){
                            TextView procedure = new TextView(context);
                            procedure.setTextSize(15);
                            procedure.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            procedure.setGravity(Gravity.CENTER);
                            procedure.setText(Integer.toString(i) + ". "+procedureStr);
                            procedures.addView(procedure);

                            LinearLayout margin = new LinearLayout(context);
                            margin.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50));
                            procedures.addView(margin);
                            i++;
                        }


                    }
                });
            }
        });
        t.start();
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //기타 로직



        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //카메라 호출 버튼
        cameraBtn = (Button) findViewById(R.id.recipeDetail_testCamera);

        //촬영한 사진 관련 permission check
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, permissions, 1);

        //카메라 호출 버튼 클릭시 작동
        cameraBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendTakePhotoIntent();
            }
        });
    }

    //기본 카메라앱 실행
    private void sendTakePhotoIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, 101);
        }catch (Exception e){
            Log.d("imageRecognition",e.toString());
        }
    }

    //기본 카메라앱에서 촬영한 사진 받아온 후 동작
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    RestUtil rs = new RestUtil();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
                    byte[] bytes = baos.toByteArray();
                    String imgStr = Base64.encodeToString(bytes, Base64.DEFAULT);
                    JSONObject result = rs.PostImg("/imageRecognition",imgStr);
                    if(result != null) {
                        try {
                            String data = result.getString("data");
                            if(data.equals("success"))
                                Log.d("APItest", "success");
                        }catch (Exception e){
                            Log.d("APItest","fail");
                        }
                    }else
                        Log.d("APItest","fail");
                }
            });
            t.start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

}
