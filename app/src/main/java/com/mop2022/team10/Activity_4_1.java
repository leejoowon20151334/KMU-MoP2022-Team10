package com.mop2022.team10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;

import java.util.ArrayList;

public class Activity_4_1 extends AppCompatActivity {
    int userId;
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

                Recipe recipe = new Recipe();
                ArrayList<RecipeModel> result = recipe.searchRecipe(query);

                String recipe_name = "";
                int recipe_id=0;
                for(int i=1;i<=52;i++)
                {
                    if(result.get(i).name == query)
                    {
                        recipe_name = result.get(i).name;
                        recipe_id = result.get(i).id;
                        break;
                    }
                }
                // 레시피 내용으로 이동해주세요. 연결해야할 부분
//                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
//                intent.putExtra("recipeId", recipe_id);
//                intent.putExtra("userId", userId);
//
//                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });



    }
}
