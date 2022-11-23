package com.mop2022.team10;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mop2022.team10.Rest.Ingredient;
import com.mop2022.team10.Rest.Model.IngredientModel;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Activity_3_1 extends AppCompatActivity {
    private ArrayList<food> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3_1);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3으로 이동
//                Intent intent = new Intent(getApplicationContext(), Activity_3.class);
//                startActivity(intent);
                finish();
            }
        });
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3으로 이동
                Intent intent = new Intent(getApplicationContext(), Activity_3_2.class);
                startActivity(intent);
                finish();
            }
        });


            /*
    사용예시

    Ingredient ingredient = new Ingredient();
    ArrayList<IngredientModel> ingredientList = ingredient.searchIngredient("계란");
    for(int i=0;i<ingredientList.size();i++){
        IngredientModel data = ingredientList.get(i);
        Log.d("식자재_이름 : ",data.name);
    }

    // IngredientModel 내용물은 /Rest/Model/IngredientModel 참조

     */

        this.InitailizeData();
        //아래 코드는 유통기한 순으로 오름차순 정렬하는 것입니다.
        Comparator<food> noAsc = new Comparator<food>() {
            @Override
            public int compare(food food1, food food2) {
                int ret;

                if(food1.getDueDate()<food2.getDueDate()){
                    ret = -1;
                }
                else if(food1.getDueDate()==food2.getDueDate()){
                    ret = 0;
                }
                else{
                    ret = 1;
                }
                return ret;
            }
        };
        Collections.sort(dataList,noAsc);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        MyAdapter myAdapter = new MyAdapter(dataList);
        recyclerView.setAdapter(myAdapter);
    }
    private void InitailizeData() {
        dataList = new ArrayList<>();
        //비동기 처리
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //API호출
                Ingredient ingredient = new Ingredient();
                //해당 사용자의 식자재 목록 반환
                ArrayList<IngredientModel> ingredientList = ingredient.userIngredient(1);
                Bitmap img2 = ingredient.getImg(ingredientList.get(0).img);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<ingredientList.size();i++){
                            IngredientModel data = ingredientList.get(i);
                            LocalDate currentdate = LocalDate.now();
                            LocalDate expirationdate = data.expirationDate;
                            Period period = Period.between(currentdate,expirationdate);
                            int duedateint = period.getDays();
                            dataList.add(new food(R.drawable.ic_launcher_background,data.name
                                    , duedateint));

            /*
    사용예시

    Ingredient ingredient = new Ingredient();
    ArrayList<IngredientModel> ingredientList = ingredient.searchIngredient("계란");
    for(int i=0;i<ingredientList.size();i++){
        IngredientModel data = ingredientList.get(i);
        Log.d("식자재_이름 : ",data.name);
    }

    // IngredientModel 내용물은 /Rest/Model/IngredientModel 참조

     */
                        }
                    }
                });

            }
        });
        dataList.add(new food(R.drawable.ic_launcher_background,"사과", 9));
        dataList.add(new food(R.drawable.ic_launcher_background,"바나나", 5));
        dataList.add(new food(R.drawable.ic_launcher_background,"당근", 3));
    }
}
