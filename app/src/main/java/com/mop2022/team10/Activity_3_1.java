package com.mop2022.team10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

        this.InitailizeData();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MyAdapter(dataList));
    }

    private void InitailizeData() {
        dataList = new ArrayList<>();

        dataList.add(new food(R.drawable.ic_launcher_background,"사과", "7일"));
        dataList.add(new food(R.drawable.ic_launcher_background,"바나나", "7일"));
        dataList.add(new food(R.drawable.ic_launcher_background,"당근", "7일"));
        //예제 코드이며 만드는 계획은 due(남은 일자 순으로 정렬을 합니다)로 오름차순으로 정렬 합니다. 정렬 알고리즘으로는 간단한 버블정렬을 채택하겠습니다.
    }
}
