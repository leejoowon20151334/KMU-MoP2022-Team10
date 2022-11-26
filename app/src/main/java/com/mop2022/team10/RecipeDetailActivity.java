
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private int userId;
    private TextView titleText;
    private ImageView recipeImg;
    private TextView evaluation;
    private TextView description;
    private TextView recipeTime;
    private TextView difficulty;
    private ImageView favorite;
    private LinearLayout ingredients;
    private LinearLayout procedures;
    private RelativeLayout modal;
    private ArrayList<ImageView> evaluationStars = new ArrayList<>();
    private Button execute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(getIntent().hasExtra("recipeId"))
            recipeId = (int) getIntent().getExtras().get("recipeId");
        else
            recipeId = 1;

        if(getIntent().hasExtra("userId"))
            userId = (int) getIntent().getExtras().get("userId");
        else
            userId = 1;

        titleText = findViewById(R.id.recipeDetail_titleText);
        recipeImg = findViewById(R.id.recipeDetail_recipeImg);
        evaluation = findViewById(R.id.recipeDetail_evaluation);
        description = findViewById(R.id.recipeDetail_description);
        recipeTime = findViewById(R.id.recipeDetail_recipeTime);
        difficulty = findViewById(R.id.recipeDetail_difficulty);
        favorite = findViewById(R.id.recipeDetail_favoriteImg);
        ingredients = findViewById(R.id.recipeDetail_ingredients);
        procedures = findViewById(R.id.recipeDetail_procedures);
        modal = findViewById(R.id.recipeDetail_evaluationModal);
        evaluationStars.add(findViewById(R.id.recipeDetail_evaluationStar_1));
        evaluationStars.add(findViewById(R.id.recipeDetail_evaluationStar_2));
        evaluationStars.add(findViewById(R.id.recipeDetail_evaluationStar_3));
        evaluationStars.add(findViewById(R.id.recipeDetail_evaluationStar_4));
        evaluationStars.add(findViewById(R.id.recipeDetail_evaluationStar_5));
        execute = findViewById(R.id.recipeDetail_execute);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                RecipeModel result = recipe.getRecipeDetail(recipeId);
                if(result.id!=recipeId)
                    finish();
                User user = new User();
                boolean isFavorite = user.isFavorite(userId,recipeId);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.name!=null)
                            titleText.setText(result.name);
                        if(result.evaluation>0)
                            evaluation.setText(Double.toString(result.evaluation));
                        else if(result.evaluation==0)
                            evaluation.setText("-");
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
                        if(isFavorite) {
                            favorite.setTag("on");
                            favorite.setImageResource(R.drawable.ic_star_fill);
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
                        setPastEvaluation();
                        setUserLog();
                    }
                });
            }
        });
        t.start();

        favorite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(favorite.getTag().equals("on")){
                    favoriteOff();
                }else{
                    favoriteOn();
                }
            }
        });

        findViewById(R.id.recipeDetail_evaluationBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                modal.setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.recipeDetail_evaluationModal_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                modal.setVisibility(View.INVISIBLE);
            }
        });

        for(int i=0;i<5;i++){
            int point = i+1;
            evaluationStars.get(i).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    evaluate(point);
                }
            });
        }

        execute.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                useRecipe();
            }
        });
    }

    private void favoriteOn(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                if(user.addFavorite(userId,recipeId)){
                    favorite.setTag("on");
                    favorite.setImageResource(R.drawable.ic_star_fill);
                }
            }
        });
        t.start();
    }

    private void favoriteOff(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                if(user.deleteFavorite(userId,recipeId)){
                    favorite.setTag("off");
                    favorite.setImageResource(R.drawable.ic_star_empty);
                }
            }
        });
        t.start();
    }

    private void setPastEvaluation(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                int val = recipe.getUserEvaluation(userId,recipeId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setStar(val);
                    }
                });
            }
        });
        t.start();
    }

    private void evaluate(int point){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                if(recipe.evaluate(userId,recipeId,point)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setStar(point);
                            setEvaluation();
                        }
                    });
                }
            }
        });
        t.start();
    }

    private void setStar(int count){
        for(int i=0;i<5;i++){
            if(i<count)
                evaluationStars.get(i).setImageResource(R.drawable.ic_star_fill);
            else
                evaluationStars.get(i).setImageResource(R.drawable.ic_star_empty);
        }
    }

    private void setEvaluation(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                double point = recipe.getEvaluation(recipeId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(point>0)
                            evaluation.setText(Double.toString(point));
                        else if(point==0)
                            evaluation.setText("-");
                    }
                });
            }
        });
        t.start();
    }

    private void useRecipe(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                recipe.useRecipe(userId,recipeId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast myToast = Toast.makeText(getApplicationContext(),"조리완료! 식자재에서 자동차감 되었습니다.", Toast.LENGTH_SHORT);
                        myToast.show();
                        finish();
                    }
                });
            }
        });
        t.start();
    }

    private void setUserLog(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.addUserSearchLog(userId,recipeId);
            }
        });
        t.start();
    }
}

