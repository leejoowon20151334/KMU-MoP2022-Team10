package com.mop2022.team10;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;
import com.mop2022.team10.Rest.Model.RecipeModel;
import com.mop2022.team10.Rest.Recipe;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Activity_6 extends AppCompatActivity {
    private ArrayList<recommend> dataList6;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_6);
        ImageButton imageButton = (ImageButton) findViewById(R.id.backButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ??으로 이동
//                Intent intent = new Intent(getApplicationContext(), Activity_?.class);
//                startActivity(intent);
                finish();
            }
        });
        this.InitailizeData6();
        Comparator<recommend> sort = new Comparator<recommend>() {
            @Override
            public int compare(recommend recommend1, recommend recommend2) {
                int ret = 0;

                if(recommend1.getRate()<recommend2.getRate()){
                    ret = -1;
                }
                else if(recommend1.getRate()==recommend2.getRate()){
                    ret = 0;
                }
                else{
                    ret = 1;
                }
                return ret;
            }
        };
        Collections.sort(dataList6,sort);

        RecyclerView recyclerView6 = (RecyclerView)findViewById(R.id.recyclerView1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView6.setLayoutManager(manager);
        RecommendAdapter recommendAdapter = new RecommendAdapter(dataList6);
        recyclerView6.setAdapter(recommendAdapter);

        Button difflow = (Button) findViewById(R.id.difficultylow_Btn);
        difflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<recommend> sort = new Comparator<recommend>() {
                    @Override
                    public int compare(recommend recommend1, recommend recommend2) {
                        int ret = 0;

                        if(recommend1.getRate()<recommend2.getRate()){
                            ret = -1;
                        }
                        else if(recommend1.getRate()==recommend2.getRate()){
                            ret = 0;
                        }
                        else{
                            ret = 1;
                        }
                        return ret;
                    }
                };

                Collections.sort(dataList6,sort);
                recommendAdapter.notifyDataSetChanged();
            }
        });
        Button diffhigh = (Button) findViewById(R.id.difficultyhigh_Btn);
        diffhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<recommend> sort = new Comparator<recommend>() {
                    @Override
                    public int compare(recommend recommend1, recommend recommend2) {
                        int ret = 0;

                        if(recommend1.getRate()<recommend2.getRate()){
                            ret = 1;
                        }
                        else if(recommend1.getRate()==recommend2.getRate()){
                            ret = 0;
                        }
                        else{
                            ret = -1;
                        }
                        return ret;
                    }
                };

                Collections.sort(dataList6,sort);
                recommendAdapter.notifyDataSetChanged();
            }
        });
    }

    private void InitailizeData6() {
        dataList6 = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //API호출
                Recipe recipe = new Recipe();
                //해당 사용자의 식자재 목록 반환
                RecipeModel result = recipe.getRecipeDetail(1);
                Bitmap img = recipe.getImg(result.img);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        StringBuilder txt = new StringBuilder(result.description);
//                        txt.append("\n");
//                        for(int i=0;i<result.procedure.size();i++)
//                            txt.append(Integer.toString(i)).append(". ").append("\n").append(result.procedure.get(i));
////                            test.setText(txt.toString());
////                            testImg.setImageBitmap(img);
//
//                            dataList6.add(new recommend())
                        dataList6.add(new recommend(R.drawable.ic_launcher_background,result.name,(float) result.difficulty));
                    }
                });

            }
        });
        dataList6.add(new recommend(R.drawable.ic_launcher_background,"사과", (float) 1.6));
        dataList6.add(new recommend(R.drawable.ic_launcher_background,"바나나",(float) 4.4));
        dataList6.add(new recommend(R.drawable.ic_launcher_background,"당근",(float) 3));
    }

}
