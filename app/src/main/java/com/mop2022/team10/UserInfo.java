package com.mop2022.team10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;

import java.util.ArrayList;

public class UserInfo extends AppCompatActivity {

    private TextView test;
    private ImageView testImg;
    private LinearLayout Lay;
    private LinearLayout Lay1;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        TextView Title = findViewById(R.id.user_info_UserName);
        //프리퍼런스 받기
//        String name = getPreferences("userName")
        String name = "test";
        Title.setText(name);
        Lay = findViewById(R.id.user_info_FirScrollView);
        Lay1 = findViewById(R.id.user_info_SecScrollView);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                userId = user.getUserId(name);
                Recipe recipe = new Recipe();
                ArrayList<RecipeModel> result = recipe.getFavorite(userId);
//                Bitmap img = recipe.getImg(result.img);userSearchLog
                Recipe recipe1 = new Recipe();
                ArrayList<RecipeModel> result1 = recipe.userSearchLog(userId);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        LinearLayout la = new LinearLayout(getBaseContext());
//                        la.setLayoutParams(params);

                        for(int i = 0;i < result.size(); i++) {
                            LinearLayout la = new LinearLayout(getBaseContext());
                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            layout_params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            layout_params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            la.setLayoutParams(layout_params);
                            la.setOrientation(LinearLayout.VERTICAL);
                            Lay.addView(la);

                            Bitmap img_ = recipe.getImg(result.get(i).img);
                            ImageView img = new ImageView(getBaseContext());
                            img.setImageBitmap(img_);
                            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            img.setLayoutParams(new LinearLayout.LayoutParams(
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()),
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics())));
                            la.addView(img);

                            TextView view = new TextView(getBaseContext());
                            view.setText(result.get(i).name);
                            view.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            view.setTextColor(Color.rgb(0,0,0));
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            la.addView(view);
                        }
                        for(int i = 0;i < result1.size(); i++) {
                            LinearLayout la = new LinearLayout(getBaseContext());
                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                            layout_params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            layout_params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,6,getResources().getDisplayMetrics());
                            la.setLayoutParams(layout_params);
                            la.setOrientation(LinearLayout.VERTICAL);
                            Lay1.addView(la);

                            Bitmap img_ = recipe1.getImg(result1.get(i).img);
                            ImageView img = new ImageView(getBaseContext());
                            img.setImageBitmap(img_);
                            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            img.setLayoutParams(new LinearLayout.LayoutParams(
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics()),
                                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics())));
                            la.addView(img);

                            TextView view = new TextView(getBaseContext());
                            view.setText(result1.get(i).name);
                            view.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            view.setTextColor(Color.rgb(0,0,0));
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            la.addView(view);
                        }
                    }
                });
            }
        });
        t.start();
    }
}