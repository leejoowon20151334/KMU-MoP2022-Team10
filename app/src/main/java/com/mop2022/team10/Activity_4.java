package com.mop2022.team10;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;


public class Activity_4 extends AppCompatActivity {

    int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_4);

        SharedPreferences pref = getSharedPreferences("userId",0);
        userId = pref.getInt("userId", 1);

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int recipe_id = (int) v.getTag();

                Intent intent = new Intent(getApplicationContext(), RecipeDetailActivity.class);
                intent.putExtra("recipeId", recipe_id);
                intent.putExtra("userId", userId);

                startActivity(intent);
            }
        };

        LinearLayout LL4 = (LinearLayout) findViewById(R.id.LL_4);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = new Recipe();
                for (int i = 1; i <= 40; i++) {
                    RecipeModel result = recipe.getRecipeDetail(i);
                    Bitmap img = recipe.getImg(result.img);

                    final int ind = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String txt = result.name;
                            int id = result.id;

                            TextView text = new TextView(Activity_4.this);
                            text.setText(txt.toString());
                            text.setGravity(Gravity.CENTER);

                            RatingBar ratingBar = new RatingBar(Activity_4.this);
                            ratingBar.setIsIndicator(true);
                            ratingBar.setNumStars(5);
                            ratingBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ratingBar.setRating(result.difficulty);


                            ImageView testImg = new ImageView(Activity_4.this);
                            testImg.setImageBitmap(img);
                            testImg.setTag(id);
                            testImg.setOnClickListener(myListener);

                            LL4.addView(text);
                            LL4.addView(testImg);
                            LL4.addView(ratingBar);
                        }
                    });
                }
            }
        });
        t.start();


        Button search_btn = (Button) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_4_1.class);
                startActivity(intent);
            }
        });


//        Button sort_btn = (Button) findViewById(R.id.sort_button);
//        sort_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
//                getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    // 팝업메뉴 클릭시
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        if (menuItem.getItemId() == R.id.action_menu1) {
//                            Toast.makeText(Activity_4.this, "오름차순", Toast.LENGTH_SHORT).show();
//                        } else if (menuItem.getItemId() == R.id.action_menu2) {
//                            Toast.makeText(Activity_4.this, "내림차순", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(Activity_4.this, "별점순", Toast.LENGTH_SHORT).show();
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });


    }
}
