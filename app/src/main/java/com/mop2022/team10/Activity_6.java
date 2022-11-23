package com.mop2022.team10;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Activity_6 extends AppCompatActivity {
    private ArrayList<recommend> dataList6;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_6);
        this.InitailizeData6();
        RecyclerView recyclerView6 = (RecyclerView)findViewById(R.id.recyclerView1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView6.setLayoutManager(manager);
        recyclerView6.setAdapter(new RecommendAdapter(dataList6));
    }

    private void InitailizeData6() {
        dataList6 = new ArrayList<>();
        dataList6.add(new recommend(R.drawable.ic_launcher_background,"사과"));
        dataList6.add(new recommend(R.drawable.ic_launcher_background,"바나나"));
        dataList6.add(new recommend(R.drawable.ic_launcher_background,"당근"));
    }

}
