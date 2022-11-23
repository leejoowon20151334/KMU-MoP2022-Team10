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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;

import java.util.ArrayList;

public class UserInfo extends AppCompatActivity {

    private TextView test;
    private ImageView testImg;
    private LinearLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        TextView Title = findViewById(R.id.user_info_UserName);
//        String name = getPreferences("userName")
        String name = "test";
        Title.setText(name);
        lay = findViewById(R.id.user_info_innerLayout);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                ArrayList<RecipeModel> result = recipe.getFavorite(1);
//                Bitmap img = recipe.getImg(result.img);

                Ingredient ingredient = new Ingredient();
                ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(1);
                Bitmap img2 = ingredient.getImg(ingredientList.get(0).img);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        StringBuilder txt = new StringBuilder(result.description);
//                        txt.append("\n");
//                        for(int i=0;i<result.procedure.size();i++)
//                            txt.append(Integer.toString(i)).append(". ").append("\n").append(result.procedure.get(i));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        Matrix m = new Matrix();
                        Bitmap img_ = recipe.getImg(result.get(0).img);
                        ImageView img = new ImageView(getBaseContext());
                        img.setLayoutParams(params);
                        img.setScaleType(ImageView.ScaleType.MATRIX);
                        img.setImageBitmap(img_);
                        img.setImageMatrix(m);
                        lay.addView(img);

                        TextView view = new TextView(getBaseContext());
                        view.setText(result.get(0).name);
                        view.setLayoutParams(params);
                        view.setTextColor(Color.rgb(0,0,0));
                        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                        lay.addView(view);




//                        testImg.setImageBitmap(img);
//                        test.setLayoutParams();
//                        testImg.setLayoutParams(lay.getLayoutParams());
//                        String txt2 = "";
//                        TextView text2 = (TextView) findViewById(R.id.recipeDetail_test2);
//                        ImageView testImg2 = (ImageView) findViewById(R.id.recipeDetail_testImg2);
//                        for(int i=0;i<ingredientList.size();i++)
//                            txt2 += ingredientList.get(i).name + ", ";
//                        text2.setText(txt2);
//                        testImg2.setImageBitmap(img2);

                    }
                });
            }
        });
        t.start();
    }
}