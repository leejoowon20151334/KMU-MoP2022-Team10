package com.mop2022.team10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_3 extends AppCompatActivity {

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        backBtn = (ImageButton) findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_2.class);
                startActivityForResult(intent, 0);
            }
        });

        /*
        Button testBtn = (Button) findViewById(R.id.roomTmp);
        TextView test = (TextView) findViewById(R.id.test);
        ImageView testImg = (ImageView) findViewById(R.id.testImg);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Recipe recipe = new Recipe();
                        RecipeModel result = recipe.getRecipeDetail(3);
                        Bitmap img = recipe.getImg(result.img);

                        Ingredient ingredient = new Ingredient();
                        ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(1);
                        Bitmap img2 = ingredient.getImg(ingredientList.get(0).img);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder txt = new StringBuilder(result.description);
                                txt.append("\n");
                                for(int i=0; i<result.procedure.size(); i++)
                                    txt.append(Integer.toString(i)).append(". ").append("\n").append(result.procedure.get(i));
                                test.setText(txt.toString());
                                testImg.setImageBitmap(img);
                            }
                        });
                    }
                });
                t.start();
            }
        });
         */
    }
}
