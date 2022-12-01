package com.mop2022.team10;

import android.content.Context;
import android.content.Intent;
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
                Intent intent = new Intent(getApplicationContext(), Activity_2.class);
                startActivity(intent);
                finish();
            }
        });
        Context context = this;
        dataList6 = new ArrayList<>();
        RecyclerView recyclerView6 = (RecyclerView)findViewById(R.id.recyclerView1);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView6.setLayoutManager(manager);
        RecommendAdapter recommendAdapter = new RecommendAdapter(dataList6);
        recyclerView6.setAdapter(recommendAdapter);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Recipe recipe = new Recipe();
                for(int i=1;i<=10;i++) {
                    RecipeModel result = recipe.getRecipeDetail(i);
                    Bitmap img = recipe.getImg(result.img);
                    final int ind = i;
                    dataList6.add(new recommend(img, result.name, (float) result.difficulty, result.time, result.evaluation,result.id));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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

            }
        });
        t.start();
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
        Button evallow = (Button) findViewById(R.id.evaluationlow_Btn);
        evallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<recommend> sort = new Comparator<recommend>() {
                    @Override
                    public int compare(recommend recommend1, recommend recommend2) {
                        int ret = 0;

                        if(recommend1.getEvaluate()<recommend2.getEvaluate()){
                            ret = -1;
                        }
                        else if(recommend1.getEvaluate()==recommend2.getEvaluate()){
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
        Button evalhigh = (Button) findViewById(R.id.evaluationhigh_Btn);
        evalhigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<recommend> sort = new Comparator<recommend>() {
                    @Override
                    public int compare(recommend recommend1, recommend recommend2) {
                        int ret = 0;

                        if(recommend1.getEvaluate()<recommend2.getEvaluate()){
                            ret = 1;
                        }
                        else if(recommend1.getEvaluate()==recommend2.getEvaluate()){
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
        Button timelow = (Button) findViewById(R.id.timelow_Btn);
        timelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<recommend> sort = new Comparator<recommend>() {
                    @Override
                    public int compare(recommend recommend1, recommend recommend2) {
                        int ret = 0;

                        if(recommend1.getTime()<recommend2.getTime()){
                            ret = -1;
                        }
                        else if(recommend1.getTime()==recommend2.getTime()){
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
        Button timehigh = (Button) findViewById(R.id.timehigh_Btn);
        timehigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comparator<recommend> sort = new Comparator<recommend>() {
                    @Override
                    public int compare(recommend recommend1, recommend recommend2) {
                        int ret = 0;

                        if(recommend1.getTime()<recommend2.getTime()){
                            ret = 1;
                        }
                        else if(recommend1.getTime()==recommend2.getTime()){
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
}
