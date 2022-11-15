package com.mop2022.team10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;
import com.mop2022.team10.Util.RestUtil;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView test;
    private Button testBtn;
    private ImageView testImg;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        test = (TextView) findViewById(R.id.recipeDetail_test);
        testBtn = (Button) findViewById(R.id.recipeDetail_testBtn);
        testImg = (ImageView) findViewById(R.id.recipeDetail_testImg);

        testBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Recipe recipe = new Recipe();
                        RecipeModel result = recipe.getRecipeDetail(1);
                        Bitmap img = recipe.getImg(result.img);

                        Ingredient ingredient = new Ingredient();
                        ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(1);
                        Bitmap img2 = ingredient.getImg(ingredientList.get(0).img);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder txt = new StringBuilder(result.description);
                                txt.append("\n");
                                for(int i=0;i<result.procedure.size();i++)
                                    txt.append(Integer.toString(i)).append(". ").append("\n").append(result.procedure.get(i));
                                test.setText(txt.toString());
                                testImg.setImageBitmap(img);

                                String txt2 = "";
                                TextView text2 = (TextView) findViewById(R.id.recipeDetail_test2);
                                ImageView testImg2 = (ImageView) findViewById(R.id.recipeDetail_testImg2);
                                for(int i=0;i<ingredientList.size();i++)
                                    txt2 += ingredientList.get(i).name + ", ";
                                text2.setText(txt2);
                                testImg2.setImageBitmap(img2);

                            }
                        });
                    }
                });
                t.start();
            }
        });
    }


}