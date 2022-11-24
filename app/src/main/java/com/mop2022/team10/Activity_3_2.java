package com.mop2022.team10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

public class Activity_3_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3_2);

        // 뒤로가기 버튼을 눌렀을 때
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 3-1로 다시 넘어가기 위해 3-2 종료하기
                Intent intent = new Intent(getApplicationContext(), Activity_3_1.class);
                startActivity(intent);
                finish();
            }
        });

        // 검색 기능을 이용할 때
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 프래그먼트매니저를 통해 사용
        TableFragment1 fragment1= new TableFragment1(); // 객체 생성
        transaction.replace(R.id.frameLayout, fragment1); // layout, 교체될 layout
        transaction.commit(); // commit으로 저장 하지 않으면 화면 전환이 되지 않음
    }
}
