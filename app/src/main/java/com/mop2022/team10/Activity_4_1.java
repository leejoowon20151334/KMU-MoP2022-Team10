package com.mop2022.team10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;

import java.util.ArrayList;

public class Activity_4_1 extends AppCompatActivity {
    int userId;
    ArrayList<RecipeModel> result = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_4_1);

        SharedPreferences pref = getSharedPreferences("userId", 0);
        userId = pref.getInt("userId",1);
        ImageButton backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Recipe recipe = new Recipe();
                        result = recipe.searchRecipe(query);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i = 0;i < result.size();i++)
                                {
                                    LinearLayout ll4_1 = findViewById(R.id.ll_4_1);
                                    Button btn = new Button(Activity_4_1.this);
                                    btn.setText(result.get(i).name);
                                    btn.setTag(i);
                                    btn.setOnClickListener(myListener);
                                    ll4_1.addView(btn);
                                }
                            }
                        });
                    }
                });
                t.start();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int ind = (int) view.getTag();
            String recipe_name = result.get(ind).name;
            int recipe_id = result.get(ind).id;

            Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
            intent.putExtra("recipeId", recipe_id);
            intent.putExtra("userId", userId);

            startActivity(intent);
        }
    };
}
