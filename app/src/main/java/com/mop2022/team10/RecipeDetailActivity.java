package com.mop2022.team10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;
import com.mop2022.team10.Rest.User;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView test;
    private Button testBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        test = (TextView) findViewById(R.id.recipeDetail_test);
        testBtn = (Button) findViewById(R.id.recipeDetail_testBtn);

        testBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Recipe recipe = new Recipe();
                        ArrayList<RecipeModel> result = recipe.searchRecipe("후라이");
                        if(result.size()>0)
                            test.setText(result.get(0).description);
                    }
                });
                t.start();
            }
        });
    }


}